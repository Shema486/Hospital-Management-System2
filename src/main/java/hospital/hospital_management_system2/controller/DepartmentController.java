package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.Department;
import hospital.hospital_management_system2.service.DepartmentService;
import hospital.hospital_management_system2.service.DoctorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class DepartmentController {

    @FXML TableView<Department> departmentTable;
    @FXML TableColumn<Department, Long> colId;
    @FXML TableColumn<Department,String> colName;
    @FXML TableColumn<Department,Integer> colFloor;

    @FXML private TextField textName;
    @FXML private TextField textFloor;

    private final DepartmentService departmentService = new DepartmentService();
    private final DoctorService doctorService = new DoctorService();
    private final ObservableList<Department> departmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getDeptId()).asObject());
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDeptName()));
        colFloor.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getLocationFloor()).asObject());

        loadDepartments();

        departmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                textName.setText(selected.getDeptName());
                textFloor.setText(String.valueOf(selected.getLocationFloor()));
            }
        });
    }
    @FXML
    private void deleteDepartment() {
        Department selected = departmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a department to delete.");
            return;
        }

        try {
            departmentService.deleteDepartment(selected.getDeptId());
            loadDepartments();
            clearFields();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("foreign key constraint")) {
                showWarning("Cannot Delete Department",
                        "This department cannot be deleted because it is referenced by other records.");
            } else {
                showWarning("Error", "Failed to delete department: " + (errorMessage != null ? errorMessage : "Unknown error"));
            }
            e.printStackTrace();
        }
    }
    @FXML
    private void updateDepartment() {
        Department selected = departmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a department to update.");
            return;
        }

        // Validate department name
        String name = textName.getText().trim();
        if (name == null || name.isEmpty()) {
            showWarning("Validation Error", "Department name is required. Please enter a department name.");
            return;
        }

        // Validate floor
        String floorText = textFloor.getText().trim();
        if (floorText == null || floorText.isEmpty()) {
            showWarning("Validation Error", "Floor number is required. Please enter the floor number.");
            return;
        }

        int floor;
        try {
            floor = Integer.parseInt(floorText);
        } catch (NumberFormatException e) {
            showWarning("Validation Error", "Invalid floor format. Please enter a valid number.");
            return;
        }

        if (floor < 0) {
            showWarning("Validation Error", "Floor number cannot be negative. Please enter a value greater than or equal to 0.");
            return;
        }

        selected.setDeptName(name);
        selected.setLocationFloor(floor);
        departmentService.updateDepartment(selected);
        loadDepartments();
        clearFields();
    }
    @FXML
    private void addDepartment() {
        // Validate department name
        String name = textName.getText().trim();
        if (name == null || name.isEmpty()) {
            showWarning("Validation Error", "Department name is required. Please enter a department name.");
            return;
        }

        // Validate floor
        String floorText = textFloor.getText().trim();
        if (floorText == null || floorText.isEmpty()) {
            showWarning("Validation Error", "Floor number is required. Please enter the floor number.");
            return;
        }

        int floor;
        try {
            floor = Integer.parseInt(floorText);
        } catch (NumberFormatException e) {
            showWarning("Validation Error", "Invalid floor format. Please enter a valid number.");
            return;
        }

        if (floor < 0) {
            showWarning("Validation Error", "Floor number cannot be negative. Please enter a value greater than or equal to 0.");
            return;
        }

        Department dept = new Department(floor,name);
        departmentService.addDepartment(dept);
        loadDepartments();
        clearFields();
    }

    public void loadDepartments(){
        List<Department> departments =departmentService.getAllDepartment();
        departmentList.setAll(departments);
        departmentTable.setItems(departmentList);
    }
    @FXML
    private void clearFields() {
        textName.clear();
        textFloor.clear();
    }
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
