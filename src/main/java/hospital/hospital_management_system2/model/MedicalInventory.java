package hospital.hospital_management_system2.model;

public class MedicalInventory {
 private int itemId;
 private String itemName;
 private int StockQuantity;
 private int unitPrice;

    public MedicalInventory(int itemId, String itemName, int stockQuantity, int unitPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        StockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
    }

    public MedicalInventory(String itemName, int stockQuantity, int unitPrice) {
        this.itemName = itemName;
        this.StockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        StockQuantity = stockQuantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "MedicalInventory{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", StockQuantity=" + StockQuantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
