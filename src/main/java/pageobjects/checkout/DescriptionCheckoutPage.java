package pageobjects.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class DescriptionCheckoutPage extends Page {
    private final By buttonCheckout = By.id("checkout");
    private final By labelDescription = By.className("cart_desc_label");

    public DescriptionCheckoutPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Clicking on continue checkout")
    public void continueCheckout() {
        waitToLoad();
        Log.info("Clicking on the checkout button");
        find(buttonCheckout).click();
    }

    @Override
    public void waitToLoad() {
        waitVisibilityOf(labelDescription);
    }
}
