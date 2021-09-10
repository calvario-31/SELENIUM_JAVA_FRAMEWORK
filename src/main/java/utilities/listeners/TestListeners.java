package utilities.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.Base;
import utilities.BrowserStackScripts;
import utilities.DriverManager;
import utilities.Log;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Log.startTest(result.getName());
        if (DriverManager.runOnServer) {
            BrowserStackScripts.writeInit(getDriverFromResult(result), result.getName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Log.endTest("PASSED", result.getName());
        if (DriverManager.runOnServer) {
            BrowserStackScripts.writeSuccess(getDriverFromResult(result));
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Log.endTest("FAILED", result.getName());

        WebDriver driver = getDriverFromResult(result);
        new DriverManager().getScreenshot(driver);

        if (DriverManager.runOnServer) {
            BrowserStackScripts.writeFailure(getDriverFromResult(result));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.endTest("SKIPPED", result.getName());
        if (DriverManager.runOnServer) {
            BrowserStackScripts.writeSkipped(getDriverFromResult(result));
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
    }

    private WebDriver getDriverFromResult(ITestResult result) {
        Object currentClass = result.getInstance();
        return ((Base) currentClass).getDriver();
    }
}
