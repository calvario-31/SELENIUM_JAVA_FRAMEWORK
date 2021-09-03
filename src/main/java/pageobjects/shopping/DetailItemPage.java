package pageobjects.shopping;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class DetailItemPage extends Page {
    private final By itemPrice = By.className("inventory_details_price");
    private final By backToProducts = By.id("back-to-products");

    public DetailItemPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Adding item to cart with id {idButton}")
    public double addToCart(String idButton) {
        waitToLoad();
        Log.info("Getting the price text from UI");
        String text = find(itemPrice).getText();

        double price = Double.parseDouble(text.substring(1));
        Log.debug("Price: " + price);
        By buttonAddToCart = By.id(idButton);
        Log.info("Clicking on add to cart");
        find(buttonAddToCart).click();
        Log.info("Clicking on back to products");
        find(backToProducts).click();

        return price;
    }

    @Override
    public void waitToLoad() {
        waitVisibilityOf(backToProducts);
    }
}
