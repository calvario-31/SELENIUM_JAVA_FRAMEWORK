package models;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("credentials")
public class CredentialsModel {
    @ExcelCellName("username")
    private String username;
    @ExcelCellName("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "{" + username + " " + password + "}";
    }
}
