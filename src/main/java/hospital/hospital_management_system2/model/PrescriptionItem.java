package hospital.hospital_management_system2.model;

public class PrescriptionItem {
    private int prescriptionId;
    private int itemId;
    private String dosageInstruction;
    private int quantityDispensed;

    public PrescriptionItem(int prescriptionId, int itemId, String dosageInstruction, int quantityDispensed) {
        this.prescriptionId = prescriptionId;
        this.itemId = itemId;
        this.dosageInstruction = dosageInstruction;
        this.quantityDispensed = quantityDispensed;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDosageInstruction() {
        return dosageInstruction;
    }

    public void setDosageInstruction(String dosageInstruction) {
        this.dosageInstruction = dosageInstruction;
    }

    public int getQuantityDispensed() {
        return quantityDispensed;
    }

    public void setQuantityDispensed(int quantityDispensed) {
        this.quantityDispensed = quantityDispensed;
    }

    @Override
    public String toString() {
        return "PrescriptionItem{" +
                "prescriptionId=" + prescriptionId +
                ", itemId=" + itemId +
                ", dosageInstruction='" + dosageInstruction + '\'' +
                ", quantityDispensed=" + quantityDispensed +
                '}';
    }
}
