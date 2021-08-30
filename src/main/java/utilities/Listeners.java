package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.datareader.src.CapabilitiesDataReader;

import static utilities.Base.runOnServer;

public class Listeners implements ITestListener {
    public static boolean assignedCapabilities = false;

    @Override
    public void onTestStart(ITestResult result) {
        Log.startTest(result.getName());
        if(runOnServer){
            new BrowserStackScripts().writeInit(getDriverFromResult(result), result.getName());
        } else {
            if(!assignedCapabilities){
                Log.info("Assigning local capabilities");
                DriverManager.capabilitiesModel =
                        CapabilitiesDataReader.getLocalCapabilities(getDriverFromResult(result));
                assignedCapabilities = true;
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.endTest("PASSED", result.getName());
        if(runOnServer){
            new BrowserStackScripts().writeSuccess(getDriverFromResult(result));
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.endTest("FAILED", result.getName());
        WebDriver driver = getDriverFromResult(result);
        new DriverManager().getScreenshot(driver);

        if(runOnServer){
            new BrowserStackScripts().writeFailure(driver);
        }
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.endTest("SKIPPED", result.getName());

        if(runOnServer){
            new BrowserStackScripts().writeSkipped(getDriverFromResult(result));
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        Log.info("Beginning: " + context.getSuite().getName());
        runOnServer = System.getenv("JOB_NAME") != null;
        if(runOnServer){
            Log.info("Assigning remote capabilities");
            DriverManager.capabilitiesModel = CapabilitiesDataReader.getRemoteCapabilities();
        } else {
            String browser = System.getProperty("browser");
            if (browser == null) {
                Log.info("Setting default local browser to CHROME");
                browser = "CHROME";
            }
            DriverManager.browser = browser;
        }
    }


    @Override
    public void onFinish(ITestContext context) {
        Log.info("Ending: " + context.getSuite().getName());
        new DriverManager().writeEnvVariables();
    }

    private WebDriver getDriverFromResult(ITestResult result){
        Object currentClass = result.getInstance();
        return ((Base) currentClass).driver;
    }
}
