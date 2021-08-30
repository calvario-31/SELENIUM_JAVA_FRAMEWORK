package utilities;

import org.openqa.selenium.JavascriptExecutor;
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
            Log.info("Getting the test name for browserstack");
            Object currentClass = result.getInstance();
            WebDriver driver = ((Base) currentClass).driver;
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\"" + result.getName() + " \" }}");

            if(assignedCapabilities){
                Log.info("Assigning local capabilities");
                DriverManager.capabilitiesModel = CapabilitiesDataReader.getLocalCapabilities(driver);
                assignedCapabilities = true;
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.endTest("PASSED", result.getName());
        if(runOnServer){
            new BrowserStackScripts().writeSuccess(result);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.endTest("FAILED", result.getName());

        Object currentClass = result.getInstance();
        WebDriver driver = ((Base) currentClass).driver;
        new DriverManager().getScreenshot(driver);

        if(runOnServer){
            new BrowserStackScripts().writeFailure(result);
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
            new BrowserStackScripts().writeSkipped(result);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        Log.info("Beginning: " + context.getSuite().getName());

        DriverManager.browser = System.getProperty("browser");

        if(runOnServer){
            Log.info("Assigning remote capabilities");
            DriverManager.capabilitiesModel = CapabilitiesDataReader.getRemoteCapabilities();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        Log.info("Ending: " + context.getSuite().getName());
        new DriverManager().writeEnvVariables();
    }
}
