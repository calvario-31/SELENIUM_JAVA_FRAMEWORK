package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({utilities.Listeners.class})
public abstract class Base {
    protected WebDriver driver;
    private final boolean runOnServer = System.getenv("JOB_NAME") != null;

    protected void setup() {
        Log.info("Setting up the driver");
        DriverManager driverManager = new DriverManager();
        if (runOnServer) {
            Log.info("Building remote driver");
            driver = driverManager.buildRemoteDriver();
        } else {
            Log.info("Building local driver");
            driver = driverManager.buildLocalDriver();
        }
    }

    protected void teardown() {
        Log.info("Tearing down the driver");
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod(description = "Setting up the driver")
    public abstract void setUp();
    @AfterMethod(description = "Tearing down the driver")
    public abstract void tearDown();
}
