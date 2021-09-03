package utilities;

import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pageobjects.Page;

import java.net.URL;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class DriverManager {
    private WebDriver driver;
    public static String browser;
    public static String browserVersion;
    public static String os;
    public static String osVersion;
    public static boolean runOnServer;

    public WebDriver buildDriver() {
        if (runOnServer) {
            Log.info("Building remote driver");
            driver = buildRemoteDriver();
        } else {
            Log.info("Building local driver");
            driver = buildLocalDriver();
        }
        Log.info("Maximizing the window");
        driver.manage().window().maximize();
        Log.info("Deleting all the cookies");
        driver.manage().deleteAllCookies();
        return driver;
    }

    public WebDriver buildLocalDriver() {
        try {
            DriverManagerType driverManagerType = DriverManagerType.valueOf(browser);
            WebDriverManager.getInstance(driverManagerType).setup();
            Class<?> driverClass = Class.forName(driverManagerType.browserClass());
            driver = (WebDriver) driverClass.newInstance();

            if (DriverManager.browserVersion == null) {
                DriverManager.browserVersion = ((RemoteWebDriver) driver).getCapabilities().getVersion();
            }

            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Failed building local driver");
            return null;
        }
    }

    public WebDriver buildRemoteDriver() {
        try {
            String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
            String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
            String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
            String username = System.getenv("BROWSERSTACK_USERNAME");
            String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            String browserStackUrl = "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("browser", browser);
            desiredCapabilities.setCapability("browser_version", browserVersion);
            desiredCapabilities.setCapability("os", os);
            desiredCapabilities.setCapability("os_version", osVersion);
            desiredCapabilities.setCapability("browserstack.local", browserstackLocal);
            desiredCapabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
            desiredCapabilities.setCapability("build", buildName);
            desiredCapabilities.setCapability("browserstack.debug", "true");  // for enabling visual logs
            desiredCapabilities.setCapability("browserstack.console", "info");  // to enable console logs at the info level. You can also use other log levels here
            desiredCapabilities.setCapability("browserstack.networkLogs", "true");  // to enable network logs to be logged

            driver = new RemoteWebDriver(new URL(browserStackUrl), desiredCapabilities);
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Failed building remote driver");
            return null;
        }
    }

    public static void assignDriverParameters() {
        DriverManager.runOnServer = System.getenv("JOB_NAME") != null;

        if (DriverManager.runOnServer) {
            DriverManager.browser = System.getProperty("browser");
            DriverManager.browserVersion = System.getProperty("browserVersion");
            DriverManager.os = System.getProperty("os");
            DriverManager.osVersion = System.getProperty("osVersion");
        } else {
            String browser = System.getProperty("browser");
            if (browser == null) {
                Log.info("Setting default local browser to CHROME");
                browser = "CHROME";
            }
            DriverManager.browser = browser;
            DriverManager.os = System.getProperty("os.name");
            DriverManager.osVersion = System.getProperty("os.version");
        }
    }

    public static void writeEnvVariables() {
        Log.info("Writing environmental variables to the report");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", browser)
                        .put("Browser Version", browserVersion)
                        .put("OS", os)
                        .put("OS Version", osVersion)
                        .put("URL", Page.getMainUrl())
                        .build());
    }

    @Attachment(value = "Screenshot failure", type = "image/png")
    public byte[] getScreenshot(WebDriver driver) {
        Log.info("Taking screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
