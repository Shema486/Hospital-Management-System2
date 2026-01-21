package hospital.hospital_management_system2.model;

public class PrescriptionItem {

    private Long prescriptionId;
    private Long itemId;
    private String dosageInstruction;
    private int quantityDispensed;

    public PrescriptionItem() {}

    public PrescriptionItem(Long prescriptionId,
                             Long itemId,
                             String dosageInstruction,
                             int quantityDispensed) {
        this.prescriptionId = prescriptionId;
        this.itemId = itemId;
        this.dosageInstruction = dosageInstruction;
        this.quantityDispensed = quantityDispensed;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
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
        return "PrescriptionItems{" +
                "prescriptionId=" + prescriptionId +
                ", itemId=" + itemId +
                ", dosageInstruction='" + dosageInstruction + '\'' +
                ", quantityDispensed=" + quantityDispensed +
                '}';
    }
}
