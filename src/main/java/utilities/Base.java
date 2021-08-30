package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({utilities.Listeners.class})
public abstract class Base {
    protected WebDriver driver;
    public static boolean runOnServer;

    protected void setup() {
        Log.info("Setting up the driver");
        if (runOnServer) {
            Log.info("Building remote driver");
            driver = new DriverManager().buildRemoteDriver();
        } else {
            Log.info("Building local driver");
            driver = new DriverManager().buildLocalDriver();
        }
        Log.info("Maximizing the window");
        driver.manage().window().maximize();
        Log.info("Deleting all the cookies");
        driver.manage().deleteAllCookies();
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
