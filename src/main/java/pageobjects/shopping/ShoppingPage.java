package pageobjects.shopping;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.Page;
import utilities.Log;

public class ShoppingPage extends Page {
    private final By title = By.className("title");
    private final By menuBurger = By.id("react-burger-menu-btn");
    private final By buttonLogout = By.id("logout_sidebar_link");
    private final By buttonAbout = By.id("about_sidebar_link");
    private final By itemCount = By.className("shopping_cart_badge");
    private final By buttonCheckout = By.id("shopping_cart_container");

    public ShoppingPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Verifying the title is displayed")
    public boolean titleIsDisplayed() {
        Log.info("Verifying the title is displayed");
        return isDisplayed(title);
    }

    @Step("Getting the href from about button and verifying is enabled")
    public String getHrefFromAbout() {
        Log.info("Clicking on the menu burger");
        $(menuBurger).click();
        WebElement AboutElement = $$(buttonAbout);
        Log.info("Verifying the button is enabled");
        if (AboutElement.isEnabled()) {
            Log.info("The button is enabled, getting the href");
            return AboutElement.getAttribute("href");
        } else {
            Log.error("The button is not enabled");
            return null;
        }
    }

    @Step("Clicking on logout")
    public void logout() {
        Log.info("Clicking on the menu burger");
        $(menuBurger).click();
        Log.info("Clicking on the button logout");
        $$(buttonLogout).click();
    }

    @Step("Going to item details of {0}")
    public void goToDetail(String productName) {
        String xpathGeneric = "//div[text()='PRODUCT_NAME']";
        String xpathItemName = xpathGeneric.replace("PRODUCT_NAME", productName);
        Log.debug("Xpath of the item name: " + xpathItemName);
        By itemName = By.xpath(xpathItemName);
        Log.info("Clicking on the name to go to the item detail");
        $$(itemName).click();
    }

    @Step("Getting the item count from the UI")
    public int getItemCount() {
        Log.info("Getting item count text");
        String text = $$(itemCount).getText();
        Log.debug("Item count test: " + text);
        return Integer.parseInt(text);
    }

    @Step("Clicking on checkout")
    public void goToCheckout() {
        Log.info("Clicking on the checkout button");
        $(buttonCheckout).click();
    }
}
