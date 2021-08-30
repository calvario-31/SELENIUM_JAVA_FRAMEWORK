package models.source;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("osCap")
public class OsModel {
    @ExcelCellName("OS")
    private final String os;
    @ExcelCellName("Version")
    private final String version;

    public OsModel(String os, String version) {
        this.os = os;
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public String getVersion() {
        return version;
    }
}
