package utilities;

import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pageobjects.Page;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class DriverManager {
    private WebDriver driver;
    private static Capabilities capabilities;

    public WebDriver buildLocalDriver() {
        String browser = System.getProperty("browser");
        if (browser == null) {
            Log.info("Setting default local browser to CHROME");
            browser = "CHROME";
        }

        switch (browser) {
            case "CHROME":
                Log.info("Building local chrome driver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                Log.info("Building local firefox driver");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "EDGE":
                Log.info("Building local edge driver");
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                break;
        }

        Log.info("Maximizing the window");
        driver.manage().window().maximize();
        Log.info("Deleting all the cookies");
        driver.manage().deleteAllCookies();
        Log.info("Getting the capabilities");
        capabilities = ((RemoteWebDriver) driver).getCapabilities();

        return driver;
    }

    public WebDriver buildRemoteDriver() {
        //capabilities = ((RemoteWebDriver) driver).getCapabilities();

        return null;
    }

    public void writeEnvVariables() {
        Log.info("Writing environmental variables to the report");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", capabilities.getBrowserName())
                        .put("Browser Version", capabilities.getVersion())
                        .put("Platform", capabilities.getPlatform().toString())
                        .put("URL", Page.getMainUrl())
                        .build());
    }

    @Attachment(value = "Screenshot failure", type = "image/png")
    public byte[] getScreenshot(WebDriver driver) {
        Log.info("Taking screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
