package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utilities.Base.runOnServer;

public class Listeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Log.startTest(result.getName());
        if(runOnServer){
            Log.info("Getting the test name for browserstack");
            Object currentClass = result.getInstance();
            WebDriver driver = ((Base) currentClass).driver;
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\"" + result.getName() + " \" }}");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.endTest("PASSED", result.getName());
        if(runOnServer){
            Log.info("Writing test success for browserstack");
            Object currentClass = result.getInstance();
            WebDriver driver = ((Base) currentClass).driver;
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test OK\"}}");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.endTest("FAILED", result.getName());
        Object currentClass = result.getInstance();
        WebDriver driver = ((Base) currentClass).driver;
        new DriverManager().getScreenshot(driver);

        if(runOnServer){
            Log.info("Writing test failure for browserstack");
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"\"}}");
        }
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.endTest("SKIPPED", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        Log.info("Beginning: " + context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        Log.info("Ending: " + context.getSuite().getName());
        new DriverManager().writeEnvVariables();
    }
}
