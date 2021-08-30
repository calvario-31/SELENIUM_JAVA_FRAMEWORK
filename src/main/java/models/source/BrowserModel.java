package models.source;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("browserCap")
public class BrowserModel {
    @ExcelCellName("browser")
    private final String browser;
    @ExcelCellName("version")
    private final String version;

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
