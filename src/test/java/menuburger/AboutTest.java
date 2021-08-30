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
import utilities.Base;
import utilities.DataReader;

public class AboutTest extends Base {
    private LoginPage loginPage;
    private ShoppingPage shoppingPage;

    @BeforeMethod(alwaysRun = true, description = "setting up the driver")
    public void setUp() {
        setup();
    }

    @Test(dataProvider = "test data", groups = {"regression", "smoke"})
    @Description("Test to verify the about button redirects to sauce labs")
    @Severity(SeverityLevel.NORMAL)
    @Parameters({"credentials", "sauce labs url"})
    @TmsLink("moriaEyr")
    public void aboutTest(CredentialsModel credentialsModel, String sauceLabsUrl) {
        loginPage = new LoginPage(driver);
        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        shoppingPage = new ShoppingPage(driver);
        Assert.assertEquals(shoppingPage.getHrefFromAbout(), sauceLabsUrl, "Href does not equals to " + sauceLabsUrl);
    }

    @AfterMethod(alwaysRun = true, description = "tearing down the driver")
    public void tearDown() {
        teardown();
    }

    @DataProvider(name = "test data")
    public Object[][] dataProvider() {
        DataReader dataReader = new DataReader();

        return new Object[][]{
                {dataReader.getValidCredentials(), dataReader.getSauceLabsUrl()}
        };
    }
}
