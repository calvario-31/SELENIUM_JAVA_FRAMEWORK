package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Log.startTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.endTest("PASSED", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.endTest("FAILED", result.getName());
        Object currentClass = result.getInstance();
        WebDriver driver = ((Base) currentClass).driver;
        new DriverManager().getScreenshot(driver);
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
