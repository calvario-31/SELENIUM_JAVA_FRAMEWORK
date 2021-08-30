package models.source;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("osCap")
public class OsModel {
    @ExcelCellName("os")
    private String os;
    @ExcelCellName("version")
    private String version;

    public OsModel() {
    }

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
