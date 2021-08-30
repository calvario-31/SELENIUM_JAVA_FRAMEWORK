package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

public class BrowserStackScripts {

    private WebDriver getDriverFromResult(ITestResult result){
        Object currentClass = result.getInstance();
        return ((Base) currentClass).driver;
    }

    public void writeSuccess(ITestResult result){
        Log.info("Writing test success for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) getDriverFromResult(result);
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test OK\"}}");
    }

    public void writeFailure(ITestResult result){
        Log.info("Writing test failure for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) getDriverFromResult(result);
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"\"}}");
    }

    public void writeSkipped(ITestResult result) {
        Log.info("Writing test skipped for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) getDriverFromResult(result);
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"skipped\", \"reason\": \"\"}}");
    }
}
