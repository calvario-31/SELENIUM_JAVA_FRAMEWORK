package models.test;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("itemData")
public class ShoppingItemModel {
    @ExcelCellName("itemName")
    private String itemName;
    @ExcelCellName("itemId")
    private String itemId;
    @ExcelCellName("price")
    private double price;

    public String getItemName() {
        return itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{" + itemName + " " + itemId + " " + price + "}";
    }
}
