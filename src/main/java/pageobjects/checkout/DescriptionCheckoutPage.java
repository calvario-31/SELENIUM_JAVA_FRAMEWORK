package pageobjects.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class DescriptionCheckoutPage extends Page {
    private final By buttonCheckout = By.id("checkout");

    public DescriptionCheckoutPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Clicking on continue checkout")
    public void continueCheckout() {
        Log.info("Clicking on the checkout button");
        $$(buttonCheckout).click();
    }
}
