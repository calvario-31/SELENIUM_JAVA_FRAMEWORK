package models.source;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CapabilitiesModel {
    private BrowserModel browserModel;
    private OsModel osModel;
    private String browserstackUrl;
    private DesiredCapabilities desiredCapabilities;

    public CapabilitiesModel(BrowserModel browserModel, OsModel osModel) {
        this.browserModel = browserModel;
        this.osModel = osModel;

        browserstackUrl = buildRemoteUrl();
        desiredCapabilities = buildRemoteCapabilities();
    }

    public CapabilitiesModel(WebDriver driver){
        buildLocalCapabilities(driver);
    }

    private void buildLocalCapabilities(WebDriver driver){
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        browserModel = new BrowserModel(capabilities.getBrowserName(), capabilities.getVersion());
        osModel = new OsModel(System.getProperty("os.name"), System.getProperty("os.version"));
    }

    private DesiredCapabilities buildRemoteCapabilities(){
        String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
        String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
        String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("browser", browserModel.getBrowser());
        desiredCapabilities.setCapability("browser_version", browserModel.getVersion());
        desiredCapabilities.setCapability("os", osModel.getOs());
        desiredCapabilities.setCapability("os_version", osModel.getVersion());
        desiredCapabilities.setCapability("browserstack.local", browserstackLocal);
        desiredCapabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
        desiredCapabilities.setCapability("build", buildName);

        return desiredCapabilities;
    }

    private String buildRemoteUrl(){
        String username = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

        return "https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
    }

    public String getBrowserstackUrl() {
        return browserstackUrl;
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    public BrowserModel getBrowserModel() {
        return browserModel;
    }

    public OsModel getOsModel() {
        return osModel;
    }
}
