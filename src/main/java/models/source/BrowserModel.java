package models.source;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("browserCap")
public class BrowserModel {
    @ExcelCellName("browser")
    private String browser;
    @ExcelCellName("version")
    private String version;

    public BrowserModel() {
    }

    public BrowserModel(String browser, String version) {
        this.browser = browser;
        this.version = version;
    }

    public String getBrowser() {
        return browser;
    }

    public String getVersion() {
        return version;
    }
}
