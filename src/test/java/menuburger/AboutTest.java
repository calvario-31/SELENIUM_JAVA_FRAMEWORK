package menuburger;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import models.CredentialsModel;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.account.LoginPage;
import pageobjects.topmenu.TopMenuPage;
import utilities.Base;
import utilities.datareader.TestDataReader;

public class AboutTest extends Base {
    private LoginPage loginPage;
    private TopMenuPage topMenuPage;

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

        topMenuPage = new TopMenuPage(driver);
        Assert.assertEquals(topMenuPage.getHrefFromAbout(), sauceLabsUrl, "Href does not equals to " + sauceLabsUrl);
    }

    @AfterMethod(alwaysRun = true, description = "tearing down the driver")
    public void tearDown() {
        teardown();
    }

    @DataProvider(name = "test data")
    public Object[][] dataProvider() {
        TestDataReader testDataReader = new TestDataReader();

        return new Object[][]{
                {testDataReader.getValidCredentials(), testDataReader.getSauceLabsUrl()}
        };
    }
}
