package credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import models.CredentialsModel;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.account.LoginPage;
import utilities.Base;
import utilities.datareader.TestDataReader;

public class BadCredentialsTest extends Base {
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true, description = "setting up the driver")
    public void setUp() {
        setup();
    }

    @Test(dataProvider = "test data", groups = {"regression", "smoke"})
    @Description("Test to verify the error message when bad credentials are used")
    @Severity(SeverityLevel.NORMAL)
    @Parameters({"credentials"})
    @TmsLink("2QtPrEKU")
    public void badCredentialsTest(CredentialsModel credentialsModel) {
        loginPage = new LoginPage(driver);
        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        Assert.assertTrue(loginPage.errorMessageIsDisplayed(), "Error message was not displayed");
    }

    @AfterMethod(alwaysRun = true, description = "tearing down the driver")
    public void tearDown() {
        teardown();
    }

    @DataProvider(name = "test data")
    public Object[][] dataProvider() {
        TestDataReader testDataReader = new TestDataReader();
        testDataReader.getLockedCredentials();

        return new Object[][]{
                {testDataReader.getLockedCredentials()}
        };
    }
}
