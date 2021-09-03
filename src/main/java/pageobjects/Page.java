package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final static String mainUrl = "https://www.saucedemo.com/";

    protected Page(WebDriver driver, int timeOut) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, timeOut);
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected WebElement waitVisibilityOf(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean isDisplayed(By locator) {
        try {
            waitVisibilityOf(locator);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void goToIndex() {
        driver.get(mainUrl);
    }

    public static String getMainUrl() {
        return mainUrl;
    }

    public abstract void waitToLoad();
}
