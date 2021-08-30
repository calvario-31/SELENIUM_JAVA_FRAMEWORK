package pageobjects.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class InformationCheckoutPage extends Page {
    private final By inputFirstname = By.id("first-name");
    private final By inputLastname = By.id("last-name");
    private final By inputZipcode = By.id("postal-code");
    private final By buttonContinue = By.id("continue");

    public InformationCheckoutPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Filling the form with {0}, {1}, {2}")
    public void fillForm(String firstname, String lastname, String zipcode) {
        Log.info("Filling the username");
        Log.debug("Firstname: " + firstname);
        $$(inputFirstname).sendKeys(firstname);
        Log.info("Filling the lastname");
        Log.debug("Lastname: " + lastname);
        $(inputLastname).sendKeys(lastname);
        Log.info("Filling the zipcode");
        Log.debug("Zipcode: " + zipcode);
        $(inputZipcode).sendKeys(zipcode);
        Log.info("Clicking on continue");
        $(buttonContinue).click();
    }
}
