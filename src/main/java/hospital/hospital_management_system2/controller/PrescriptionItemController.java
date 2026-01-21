package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.*;
import hospital.hospital_management_system2.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionItemController {

    @FXML private TableView<PrescriptionItem> itemTable;
    @FXML private TableColumn<PrescriptionItem, String> colMedicine;
    @FXML private TableColumn<PrescriptionItem, String> colDosage;
    @FXML private TableColumn<PrescriptionItem, Integer> colQuantity;

    @FXML private ComboBox<Prescription> cbPrescription;
    @FXML private ComboBox<MedicalInventory> cbMedicine;
    @FXML private TextField txtDosage;
    @FXML private Spinner<Integer> spinQuantity;

    private final PrescriptionItemService itemService = new PrescriptionItemService();
    private final PrescriptionService prescriptionService = new PrescriptionService();
    private final MedicalInventoryService inventoryService = new MedicalInventoryService();
    private final AppointmentService appointmentService = new AppointmentService();

    private final ObservableList<PrescriptionItem> itemList = FXCollections.observableArrayList();

    // Lookup maps (Doctor / Feedback style)
    private final Map<Long, MedicalInventory> medicineById = new HashMap<>();
    private final Map<Long, Appointment> appointmentById = new HashMap<>();

    @FXML
    public void initialize() {

        // ===== TABLE COLUMNS =====
        colMedicine.setCellValueFactory(data -> {
            MedicalInventory med = medicineById.get(data.getValue().getItemId());
            return new javafx.beans.property.SimpleStringProperty(
                    med != null ? med.getItemName() : "Unknown"
            );
        });

        colDosage.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDosageInstruction()
                )
        );

        colQuantity.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getQuantityDispensed()
                ).asObject()
        );

        spinQuantity.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );

        loadPrescriptions();
        loadMedicines();

        cbPrescription.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                loadItems();
            }
        });

        itemTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                cbMedicine.setValue(medicineById.get(selected.getItemId()));
                txtDosage.setText(selected.getDosageInstruction());
                spinQuantity.getValueFactory().setValue(selected.getQuantityDispensed());
            }
        });
    }

    // ===== LOAD PRESCRIPTIONS =====
    private void loadPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();

        appointmentById.clear();
        for (Appointment app : appointmentService.getAllAppointments()) {
            appointmentById.put(app.getAppointmentId(), app);
        }

        cbPrescription.setItems(FXCollections.observableArrayList(prescriptions));

        cbPrescription.setConverter(new StringConverter<>() {
            @Override
            public String toString(Prescription p) {
                if (p == null) return "";
                return buildPrescriptionLabel(p);
            }

            @Override
            public Prescription fromString(String string) {
                return null;
            }
        });

        cbPrescription.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Prescription item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : buildPrescriptionLabel(item));
            }
        });
    }

    // ===== LOAD MEDICINES =====
    private void loadMedicines() {
        List<MedicalInventory> medicines = inventoryService.getAllInventoryItems();
        medicineById.clear();

        for (MedicalInventory m : medicines) {
            medicineById.put(m.getItemId(), m);
        }

        cbMedicine.setItems(FXCollections.observableArrayList(medicines));

        cbMedicine.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(MedicalInventory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null
                        ? null
                        : item.getItemName() + " (Stock: " + item.getStockQuantity() + ")");
            }
        });

        cbMedicine.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(MedicalInventory item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getItemName());
            }
        });
    }

    // ===== LOAD ITEMS =====
    private void loadItems() {
        itemList.setAll(
                itemService.getItemsByPrescription(
                        cbPrescription.getValue().getPrescriptionId()
                )
        );
        itemTable.setItems(itemList);
    }

    // ===== ADD ITEM =====
    @FXML
    private void addItem() {
        if (!isFormValid()) return;

        MedicalInventory medicine = cbMedicine.getValue();
        int qty = spinQuantity.getValue();

        if (medicine.getStockQuantity() < qty) {
            showWarning("Stock Error", "Insufficient stock available.");
            return;
        }

        PrescriptionItem item = new PrescriptionItem(
                cbPrescription.getValue().getPrescriptionId(),
                medicine.getItemId(),
                txtDosage.getText(),
                qty
        );

        itemService.addPrescriptionItem(item);

        medicine.setStockQuantity(medicine.getStockQuantity() - qty);
        inventoryService.updateInventoryItem(medicine);

        loadItems();
        loadMedicines();
        clearFields();
    }

    // ===== DELETE ITEM =====
    @FXML
    private void deleteItem() {
        PrescriptionItem selected = itemTable.getSelectionModel().getSelectedItem();
        if (selected != null) {

            MedicalInventory medicine = medicineById.get(selected.getItemId());
            medicine.setStockQuantity(
                    medicine.getStockQuantity() + selected.getQuantityDispensed()
            );
            inventoryService.updateInventoryItem(medicine);

            itemService.deleteItem(
                    selected.getPrescriptionId(),
                    selected.getItemId()
            );

            loadItems();
            loadMedicines();
            clearFields();
        }
    }

    // ===== UTIL =====
    @FXML
    private void clearFields() {
        cbMedicine.setValue(null);
        txtDosage.clear();
        spinQuantity.getValueFactory().setValue(1);
    }

    private boolean isFormValid() {
        if (cbPrescription.getValue() == null) {
            showWarning("Validation Error", "Select a prescription");
            return false;
        }
        if (cbMedicine.getValue() == null) {
            showWarning("Validation Error", "Select a medicine");
            return false;
        }
        if (txtDosage.getText().isBlank()) {
            showWarning("Validation Error", "Enter dosage instructions");
            return false;
        }
        return true;
    }

    private String buildPrescriptionLabel(Prescription p) {
        Appointment app = appointmentById.get(p.getAppointmentId());
        if (app == null) return "Prescription " + p.getPrescriptionId();

        // Use patientName and doctorName from Appointment
        String patientName = app.getPatientName() != null ? app.getPatientName() : "Unknown Patient";
        String doctorName = app.getDoctorName() != null ? "Dr. " + app.getDoctorName() : "Unknown Doctor";

        return patientName + " - " + doctorName;
    }


    private void showWarning(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
