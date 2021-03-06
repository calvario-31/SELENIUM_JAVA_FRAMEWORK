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
import pageobjects.topmenu.TopMenuPage;
import utilities.Base;
import utilities.datareader.TestDataReader;

import java.util.List;

public class ShoppingTest extends Base {
    private LoginPage loginPage;
    private ShoppingPage shoppingPage;
    private DetailItemPage detailItemPage;
    private DescriptionCheckoutPage descriptionCheckoutPage;
    private InformationCheckoutPage informationCheckoutPage;
    private OverviewCheckoutPage overviewCheckoutPage;
    private SuccessShoppingPage successShoppingPage;
    private TopMenuPage topMenuPage;

    @BeforeMethod(alwaysRun = true, description = "setting up the driver")
    public void setUp() {
        setup();
        initPages();
    }

    @Test(dataProvider = "test data", groups = {"regression"})
    @Description("Test to the end to end shopping functionality")
    @Severity(SeverityLevel.BLOCKER)
    @Parameters({"credentials", "shopping list", "user data"})
    @TmsLink("2E5cHwYs")
    public void shoppingEndToEndTest(CredentialsModel credentialsModel,
                                     List<ShoppingItemModel> shoppingItemModelList,
                                     UserDataModel userDataModel) {

        loginPage.login(credentialsModel.getUsername(), credentialsModel.getPassword());

        double sum = 0;
        double currentPrice;
        for (ShoppingItemModel item : shoppingItemModelList) {
            shoppingPage.goToDetail(item.getItemName());
            currentPrice = detailItemPage.addToCart(item.getItemId());
            Assert.assertEquals(currentPrice, item.getPrice(), "Price were not equals");
            sum += currentPrice;
        }

        Assert.assertEquals(topMenuPage.getItemCount(), shoppingItemModelList.size(), "Item count were not equal");
        topMenuPage.goToCheckout();

        descriptionCheckoutPage.continueCheckout();

        informationCheckoutPage.fillForm(userDataModel.getFirstname(), userDataModel.getLastname(),
                userDataModel.getZipcode());

        Assert.assertEquals(overviewCheckoutPage.getTotalPrice(), sum, "Sum were not equal");
        overviewCheckoutPage.finishCheckout();

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
        TestDataReader testDataReader = new TestDataReader();

        return new Object[][]{
                {testDataReader.getValidCredentials(), testDataReader.getItemList(), testDataReader.getUserData()}
        };
    }

    @Override
    public void initPages() {
        loginPage = new LoginPage(driver);
        shoppingPage = new ShoppingPage(driver);
        detailItemPage = new DetailItemPage(driver);
        topMenuPage = new TopMenuPage(driver);
        descriptionCheckoutPage = new DescriptionCheckoutPage(driver);
        informationCheckoutPage = new InformationCheckoutPage(driver);
        overviewCheckoutPage = new OverviewCheckoutPage(driver);
        successShoppingPage = new SuccessShoppingPage(driver);
    }
}
