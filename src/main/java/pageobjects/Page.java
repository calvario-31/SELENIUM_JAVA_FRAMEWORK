package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
    protected final static String mainUrl = "https://www.saucedemo.com/";
    protected final WebDriver driver;
    protected final int defaultTimeOut = 5;

    protected Page(WebDriver driver) {
        this.driver = driver;
    }

    public static String getMainUrl() {
        return mainUrl;
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected WebElement waitVisibility(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean elementIsDisplayed(By locator, int timeOut) {
        try {
            waitVisibility(locator, timeOut);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void goToIndex() {
        driver.get(mainUrl);
    }

    public abstract void waitToLoad();
}
