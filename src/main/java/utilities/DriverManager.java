package utilities;

import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.qameta.allure.Attachment;
import models.source.CapabilitiesModel;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import pageobjects.Page;

import java.net.URL;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class DriverManager {
    private WebDriver driver;
    public static CapabilitiesModel capabilitiesModel;
    public static String browser;

    public WebDriver buildLocalDriver() {
        try {
            DriverManagerType driverManagerType = DriverManagerType.valueOf(browser);
            WebDriverManager.getInstance(driverManagerType).setup();
            Class<?> driverClass = Class.forName(driverManagerType.browserClass());
            driver = (WebDriver) driverClass.newInstance();

            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Failed building local driver");
            return null;
        }
    }

    public WebDriver buildRemoteDriver() {
        try {
            driver = new RemoteWebDriver(new URL(capabilitiesModel.getBrowserstackUrl()),
                    capabilitiesModel.getDesiredCapabilities());
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Failed building remote driver");
            return null;
        }
    }

    public void writeEnvVariables() {
        Log.info("Writing environmental variables to the report");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", capabilitiesModel.getBrowserModel().getBrowser())
                        .put("Browser Version", capabilitiesModel.getBrowserModel().getVersion())
                        .put("OS", capabilitiesModel.getOsModel().getOs())
                        .put("OS Version", capabilitiesModel.getOsModel().getVersion())
                        .put("URL", Page.getMainUrl())
                        .build());
    }

    @Attachment(value = "Screenshot failure", type = "image/png")
    public byte[] getScreenshot(WebDriver driver) {
        Log.info("Taking screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
