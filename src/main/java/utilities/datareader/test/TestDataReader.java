package utilities.datareader.test;

import com.poiji.bind.Poiji;
import models.test.CredentialsModel;
import models.test.ShoppingItemModel;
import models.test.UserDataModel;

import java.io.File;
import java.util.List;

public class TestDataReader {
    private final String EXCEL_PATH = "src/test/resources/data/testData.xlsx";
    private final String SAUCE_LABS_URL = "https://saucelabs.com/";

    public CredentialsModel getValidCredentials() {
        return Poiji.fromExcel(new File(EXCEL_PATH), CredentialsModel.class).get(0);
    }

    public CredentialsModel getLockedCredentials() {
        return Poiji.fromExcel(new File(EXCEL_PATH), CredentialsModel.class).get(1);
    }

    public List<ShoppingItemModel> getItemList() {
        return Poiji.fromExcel(new File(EXCEL_PATH), ShoppingItemModel.class);
    }

    public UserDataModel getUserData() {
        return new UserDataModel();
    }

    public String getSauceLabsUrl() {
        return SAUCE_LABS_URL;
    }
}
