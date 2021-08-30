package shopping;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import models.CredentialsModel;
import models.ShoppingItemModel;
import models.UserDataModel;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.account.LoginPage;
import pageobjects.checkout.DescriptionCheckoutPage;
import pageobjects.checkout.InformationCheckoutPage;
import pageobjects.checkout.OverviewCheckoutPage;
import pageobjects.checkout.SuccessShoppingPage;
import pageobjects.shopping.DetailItemPage;
import pageobjects.shopping.ShoppingPage;
import utilities.Base;
import utilities.DataReader;

import java.util.List;

public class ShoppingTest extends Base {
    private LoginPage loginPage;
    private ShoppingPage shoppingPage;
    private DetailItemPage detailItemPage;
    private DescriptionCheckoutPage descriptionCheckoutPage;
    private InformationCheckoutPage informationCheckoutPage;
    private OverviewCheckoutPage overviewCheckoutPage;
    private SuccessShoppingPage successShoppingPage;

    @BeforeMethod(alwaysRun = true, description = "setting up the driver")
    public void setUp() {
        setup();
    }

    @Test(dataProvider = "test data", groups = {"regression"})
    @Description("Test to the end to end shopping functionality")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"credentials", "shopping list", "user data"})
    @TmsLink("2E5cHwYs")
    public void shoppingEndToEndTest(CredentialsModel credentialsModel,
                                     List<ShoppingItemModel> shoppingItemModelList,
                                     UserDataModel userDataModel) {
        loginPage = new LoginPage(driver);
        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        shoppingPage = new ShoppingPage(driver);
        detailItemPage = new DetailItemPage(driver);

        double sum = 0;
        double currentPrice;
        for (ShoppingItemModel item : shoppingItemModelList) {
            shoppingPage.goToDetail(item.getItemName());
            currentPrice = detailItemPage.addToCart(item.getItemId());
            Assert.assertEquals(currentPrice, item.getPrice(), "Price were not equals");
            sum += currentPrice;
        }

        Assert.assertEquals(shoppingPage.getItemCount(), shoppingItemModelList.size(), "Item count were not equal");
        shoppingPage.goToCheckout();

        descriptionCheckoutPage = new DescriptionCheckoutPage(driver);
        descriptionCheckoutPage.continueCheckout();

        informationCheckoutPage = new InformationCheckoutPage(driver);
        informationCheckoutPage.fillForm(userDataModel.getFirstname(), userDataModel.getLastname(),
                userDataModel.getZipcode());

        overviewCheckoutPage = new OverviewCheckoutPage(driver);
        Assert.assertEquals(overviewCheckoutPage.getTotalPrice(), sum, "Sum were not equal");
        overviewCheckoutPage.finishCheckout();

        successShoppingPage = new SuccessShoppingPage(driver);
        Assert.assertTrue(successShoppingPage.titleIsDisplayed(), "Success title was not displayed");
        successShoppingPage.backToHome();

        Assert.assertTrue(shoppingPage.titleIsDisplayed(), "Shopping title was not displayed");
    }

    @AfterMethod(alwaysRun = true, description = "tearing down the driver")
    public void tearDown() {
        teardown();
    }

    @DataProvider(name = "test data")
    public Object[][] dataProvider() {
        DataReader dataReader = new DataReader();

        return new Object[][]{
                {dataReader.getValidCredentials(), dataReader.getItemList(), dataReader.getUserData()}
        };
    }
}
