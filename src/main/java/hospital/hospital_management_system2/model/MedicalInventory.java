package hospital.hospital_management_system2.model;

public class MedicalInventory {
 private Long itemId;
 private String itemName;
 private int StockQuantity;
 private double unitPrice;

    public MedicalInventory(Long itemId, String itemName, int stockQuantity, double unitPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        StockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
    }

    public MedicalInventory(String itemName, int stockQuantity, double unitPrice) {
        this.itemName = itemName;
        this.StockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
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
