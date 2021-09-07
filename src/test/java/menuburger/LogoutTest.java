package menuburger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import models.CredentialsModel;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.account.LoginPage;
import pageobjects.shopping.ShoppingPage;
import pageobjects.topmenu.TopMenuPage;
import utilities.Base;
import utilities.datareader.TestDataReader;

public class LogoutTest extends Base {
    private LoginPage loginPage;
    private ShoppingPage shoppingPage;
    private TopMenuPage topMenuPage;

    @BeforeMethod(alwaysRun = true, description = "setting up the driver")
    public void setUp() {
        setup();
        initPages();
    }

    @Test(dataProvider = "test data", groups = {"regression", "smoke"})
    @Description("Test to verify the logout functionality")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"credentials"})
    @TmsLink("ksBWkBLx")
    public void logoutTest(CredentialsModel credentialsModel) {
        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        Assert.assertTrue(shoppingPage.titleIsDisplayed());

        topMenuPage.logout();

        Assert.assertTrue(loginPage.buttonImageIsDisplayed(), "Bot image is not displayed");
    }

    @AfterMethod(alwaysRun = true, description = "tearing down the driver")
    public void tearDown() {
        teardown();
    }

    @DataProvider(name = "test data")
    public Object[][] dataProvider() {
        TestDataReader testDataReader = new TestDataReader();

        return new Object[][]{
                {testDataReader.getValidCredentials()}
        };
    }

    @Override
    public void initPages() {
        loginPage = new LoginPage(driver);
        shoppingPage = new ShoppingPage(driver);
        topMenuPage = new TopMenuPage(driver);
    }
}
