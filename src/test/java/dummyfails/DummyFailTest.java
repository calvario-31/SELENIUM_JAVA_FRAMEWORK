package dummyfails;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import models.CredentialsModel;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.account.LoginPage;
import pageobjects.shopping.ShoppingPage;
import utilities.Base;
import utilities.DataReader;

public class DummyFailTest extends Base {
    private LoginPage loginPage;
    private ShoppingPage shoppingPage;

    @BeforeMethod(alwaysRun = true, description = "setting up the driver")
    public void setUp() {
        setup();
    }

    @Test(dataProvider = "locked out user data", groups = "failed")
    @Description("Test that always fails to test the screenshot 1")
    @Severity(SeverityLevel.TRIVIAL)
    @Parameters({"credentials"})
    @Issue("8dvc3IEV")
    public void alwaysFailsTest(CredentialsModel credentialsModel) {
        loginPage = new LoginPage(driver);
        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        shoppingPage = new ShoppingPage(driver);
        Assert.assertTrue(shoppingPage.titleIsDisplayed(), "Shopping title was not displayed");
    }

    @Test(dataProvider = "valid user data", groups = "failed")
    @Description("Test that always fails to test the screenshot 2")
    @Severity(SeverityLevel.TRIVIAL)
    @Parameters({"credentials"})
    @Issue("8dvc3IEV")
    public void alwaysFailsTest2(CredentialsModel credentialsModel) {
        loginPage = new LoginPage(driver);
        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        Assert.assertTrue(loginPage.buttonImageIsDisplayed(), "Bot image was not displayed");
    }

    @AfterMethod(alwaysRun = true, description = "tearing down the driver")
    public void tearDown() {
        teardown();
    }

    @DataProvider(name = "valid user data")
    public Object[][] dataProviderValid() {
        DataReader dataReader = new DataReader();

        return new Object[][]{
                {dataReader.getValidCredentials()}
        };
    }

    @DataProvider(name = "locked out user data")
    public Object[][] dataProviderLockedOut() {
        DataReader dataReader = new DataReader();

        return new Object[][]{
                {dataReader.getLockedCredentials()}
        };
    }
}
