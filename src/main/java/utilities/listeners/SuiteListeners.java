package utilities.listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import utilities.DriverManager;
import utilities.Log;

public class SuiteListeners implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        Log.info("Beginning: " + suite.getName());
        assignDriverParameters();
    }

    @Override
    public void onFinish(ISuite suite) {
        Log.info("Ending: " + suite.getName());
        new DriverManager().writeEnvVariables();
    }

    private void assignDriverParameters(){
        DriverManager.runOnServer = System.getenv("JOB_NAME") != null;

        if(DriverManager.runOnServer){
            DriverManager.browser = System.getProperty("browser");;
            DriverManager.browserVersion = System.getProperty("browserVersion");;
            DriverManager.os = System.getProperty("OS");;
            DriverManager.osVersion = System.getProperty("osVersion");;
        } else {
            String browser = System.getProperty("browser");
            if(browser == null){
                Log.info("Setting default local browser to CHROME");
                browser = "CHROME";
            }
            DriverManager.browser = browser;
/*            String browserVersion;
            switch (browser){
                case "CHROME":
                    browserVersion = WebDriverManager.globalConfig().getBrowserVersionDetectionCommand();
                    break;
                case "FIREFOX":
                    browserVersion = WebDriverManager.globalConfig().getFirefoxVersion();
                    break;
                case "EDGE":
                    browserVersion = WebDriverManager.globalConfig().getEdgeVersion();
                    break;
            }*/
            DriverManager.browserVersion = WebDriverManager.getInstance(browser).browserVersion("latest").toString();
            DriverManager.os = System.getProperty("os.name");
            DriverManager.osVersion = System.getProperty("os.version");
        }
    }
}
