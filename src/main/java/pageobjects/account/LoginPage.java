package pageobjects.account;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Page;
import utilities.Log;

public class LoginPage extends Page {
    private final By inputUsername = By.id("user-name");
    private final By inputPassword = By.id("password");
    private final By buttonLogin = By.id("login-button");
    private final By imageBot = By.className("bot_column");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver, 5);
    }

    @Step("Login into the app with {0} and {1}")
    public void login(String username, String password) {
        Log.info("Going to the main page");
        goToIndex();
        waitToLoad();
        Log.info("Filling the username");
        Log.debug("Username: " + username);
        find(inputUsername).sendKeys(username);
        Log.info("Filling the password");
        Log.debug("Password: " + password);
        find(inputPassword).sendKeys(password);
        Log.info("Clicking on login");
        find(buttonLogin).click();
    }

    @Step("Verifying the bot image is displayed")
    public boolean buttonImageIsDisplayed() {
        Log.info("Verifying the bot image is displayed");
        return isDisplayed(imageBot);
    }

    @Step("Verifying the error message is displayed")
    public boolean errorMessageIsDisplayed() {
        Log.info("Verifying the error message is displayed");
        return isDisplayed(errorMessage);
    }

    @Override
    public void waitToLoad() {
        waitVisibilityOf(imageBot);
    }
}
