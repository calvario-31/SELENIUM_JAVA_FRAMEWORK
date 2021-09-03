package pageobjects.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class OverviewCheckoutPage extends Page {
    private final By labelPrice = By.className("summary_subtotal_label");
    private final By buttonFinish = By.id("finish");
    private final By title = By.className("title");

    public OverviewCheckoutPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Getting the total price from the UI")
    public double getTotalPrice() {
        waitToLoad();
        Log.info("Getting the total price from the UI");
        String text = find(labelPrice).getText();
        Log.debug("Total price: " + text.substring(13));
        return Double.parseDouble(text.substring(13));
    }

    @Step("Clicking on finish checkout")
    public void finishCheckout() {
        Log.info("Clicking on finish checkout");
        find(buttonFinish).click();
    }

    @Override
    public void waitToLoad() {
        waitVisibilityOf(title);
    }
}
