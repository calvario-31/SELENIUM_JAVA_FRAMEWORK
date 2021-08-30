package utilities.datareader.src;

import com.poiji.bind.Poiji;
import models.source.BrowserModel;
import models.source.CapabilitiesModel;
import models.source.OsModel;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class CapabilitiesDataReader {
    private static final String EXCEL_PATH_CAP = "src/main/resources/browserCapabilities.xlsx";
    private static final HashMap<String, OsModel> osCapabilities= new HashMap<>();
    private static final HashMap<String, BrowserModel> browserCapabilities= new HashMap<>();

    public static void buildHashCapabilities(){
        List<BrowserModel> browserList = Poiji.fromExcel(new File(EXCEL_PATH_CAP), BrowserModel.class);
        List<OsModel> osList = Poiji.fromExcel(new File(EXCEL_PATH_CAP), OsModel.class);

        browserCapabilities.put("CHROME", browserList.get(0));
        browserCapabilities.put("FIREFOX", browserList.get(1));
        browserCapabilities.put("EDGE", browserList.get(2));
        browserCapabilities.put("SAFARI", browserList.get(3));

        osCapabilities.put("WINDOWS", osList.get(0));
        osCapabilities.put("MAC", osList.get(1));
    }

    public static CapabilitiesModel getRemoteCapabilities(){
        buildHashCapabilities();
        String browser = System.getProperty("browser");
        String os = System.getProperty("os");
        return new CapabilitiesModel(browserCapabilities.get(browser), osCapabilities.get(os));
    }

    public static CapabilitiesModel getLocalCapabilities(WebDriver driver){
        return new CapabilitiesModel(driver);
    }
}
