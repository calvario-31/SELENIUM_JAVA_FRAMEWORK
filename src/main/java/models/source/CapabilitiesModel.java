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
    /*    desiredCapabilities.setCapability("browser", browserModel.getBrowser());
        desiredCapabilities.setCapability("browser_version", browserModel.getVersion());
        desiredCapabilities.setCapability("os", osModel.getOs());
        desiredCapabilities.setCapability("os_version", osModel.getVersion());*/
        desiredCapabilities.setCapability("device", "Samsung Galaxy S20");
        desiredCapabilities.setCapability("os_version", "10.0");
        desiredCapabilities.setCapability("browserName", "Android");
        //
        desiredCapabilities.setCapability("browserstack.local", browserstackLocal);
        desiredCapabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
        desiredCapabilities.setCapability("build", buildName);
        desiredCapabilities.setCapability("browserstack.debug", "true");  // for enabling visual logs
        desiredCapabilities.setCapability("browserstack.console", "info");  // to enable console logs at the info level. You can also use other log levels here
        desiredCapabilities.setCapability("browserstack.networkLogs", "true");  // to enable network logs to be logged

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
