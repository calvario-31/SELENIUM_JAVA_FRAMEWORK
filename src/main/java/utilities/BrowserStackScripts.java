package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BrowserStackScripts {

    public static void writeInit(WebDriver driver, String testName) {
        Log.info("Writing the test name for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionName\", \"arguments\": {\"name\":\"" + testName + " \" }}");
    }

    public static void writeSuccess(WebDriver driver) {
        Log.info("Writing test success for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test OK\"}}");
    }

    public static void writeFailure(WebDriver driver) {
        Log.info("Writing test failure for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"\"}}");
    }

    public static void writeSkipped(WebDriver driver) {
        Log.info("Writing test skipped for browserstack");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"skipped\", \"reason\": \"\"}}");
    }
}
