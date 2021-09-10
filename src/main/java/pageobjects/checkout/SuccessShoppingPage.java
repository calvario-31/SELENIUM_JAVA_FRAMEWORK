package pageobjects.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class SuccessShoppingPage extends Page {
    private final By successTitle = By.className("complete-header");
    private final By buttonBackToHome = By.id("back-to-products");

    public SuccessShoppingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Verifying the title is displayed")
    public boolean titleIsDisplayed() {
        Log.info("Verifying the title is displayed");
        return elementIsDisplayed(successTitle, defaultTimeOut);
    }

    @Step("Clicking on back to home")
    public void backToHome() {
        Log.info("Clicking on back to home");
        find(buttonBackToHome).click();
    }

    @Override
    public void waitToLoad() {

    }
}
