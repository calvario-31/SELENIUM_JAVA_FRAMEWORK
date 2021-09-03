package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({utilities.listeners.TestListeners.class, utilities.listeners.SuiteListeners.class})
public abstract class Base {
    protected WebDriver driver;

    protected void setup() {
        Log.info("Setting up the driver");
        driver = new DriverManager().buildDriver();
    }

    protected void teardown() {
        Log.info("Tearing down the driver");
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod(description = "Setting up the driver")
    public abstract void setUp();
    @AfterMethod(description = "Tearing down the driver")
    public abstract void tearDown();
}
