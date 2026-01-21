package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.MedicalInventory;
import hospital.hospital_management_system2.service.MedicalInventoryService;
import hospital.hospital_management_system2.service.PrescriptionItemService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MedicalInventoryController {

    @FXML private TableView<MedicalInventory> inventoryTable;
    @FXML private TableColumn<MedicalInventory, Long> colId;
    @FXML private TableColumn<MedicalInventory, String> colName;
    @FXML private TableColumn<MedicalInventory, Integer> colQuantity;
    @FXML private TableColumn<MedicalInventory, Double> colPrice;

    @FXML private TextField txtName;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtPrice;

    private final MedicalInventoryService inventoryService = new MedicalInventoryService();
    private final PrescriptionItemService prescriptionItemService = new PrescriptionItemService();
    private final ObservableList<MedicalInventory> inventoryList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getItemId()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getItemName()));
        colQuantity.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getStockQuantity()).asObject());
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getUnitPrice()).asObject());

        loadInventory();

        inventoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                txtName.setText(selected.getItemName());
                txtQuantity.setText(String.valueOf(selected.getStockQuantity()));
                txtPrice.setText(String.valueOf(selected.getUnitPrice()));
            }
        });
    }

    private void loadInventory() {
        List<MedicalInventory> items = inventoryService.getAllInventoryItems();
        inventoryList.setAll(items);
        inventoryTable.setItems(inventoryList);
    }

    @FXML
    private void addItem() {
        String itemName = txtName.getText().trim();
        if (itemName.isEmpty()) {
            showWarning("Validation Error", "Medicine name is required.");
            return;
        }

        if (!inventoryService.isItemNameUnique(itemName, 0)) {
            showWarning("Validation Error", "This medicine name already exists.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(txtQuantity.getText().trim());
        } catch (NumberFormatException e) {
            showWarning("Validation Error", "Invalid quantity format.");
            return;
        }

        if (quantity < 0) {
            showWarning("Validation Error", "Stock quantity cannot be negative.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException e) {
            showWarning("Validation Error", "Invalid price format.");
            return;
        }

        if (price < 0) {
            showWarning("Validation Error", "Unit price cannot be negative.");
            return;
        }

        MedicalInventory item = new MedicalInventory(itemName, quantity, price);
        inventoryService.addInventoryItem(item);
        loadInventory();
        clearFields();
    }

    @FXML
    private void updateItem() {
        MedicalInventory selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select an item to update.");
            return;
        }

        String itemName = txtName.getText().trim();
        if (itemName.isEmpty()) {
            showWarning("Validation Error", "Medicine name is required.");
            return;
        }

        if (!inventoryService.isItemNameUnique(itemName, selected.getItemId())) {
            showWarning("Validation Error", "This medicine name already exists.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(txtQuantity.getText().trim());
        } catch (NumberFormatException e) {
            showWarning("Validation Error", "Invalid quantity format.");
            return;
        }

        if (quantity < 0) {
            showWarning("Validation Error", "Stock quantity cannot be negative.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException e) {
            showWarning("Validation Error", "Invalid price format.");
            return;
        }

        if (price < 0) {
            showWarning("Validation Error", "Unit price cannot be negative.");
            return;
        }

        selected.setItemName(itemName);
        selected.setStockQuantity(quantity);
        selected.setUnitPrice(price);
        inventoryService.updateInventoryItem(selected);
        loadInventory();
        clearFields();
    }

    @FXML
    private void deleteItem() {
        MedicalInventory selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select an item to delete.");
            return;
        }

        try {
            inventoryService.deleteInventoryItem(selected.getItemId());
            loadInventory();
            clearFields();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("foreign key constraint")) {
                showWarning("Cannot Delete Medicine",
                        "This medicine is used in prescription items. Remove it from prescriptions first.");
            } else {
                showWarning("Error", "Failed to delete medicine: " + (errorMessage != null ? errorMessage : "Unknown error"));
            }
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        txtName.clear();
        txtQuantity.clear();
        txtPrice.clear();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
