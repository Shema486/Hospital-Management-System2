package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.Doctor;
import hospital.hospital_management_system2.model.Department;
import hospital.hospital_management_system2.service.DoctorService;
import hospital.hospital_management_system2.service.DepartmentService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class DoctorController {

    @FXML private TableView<Doctor> doctorTable;
    @FXML private TableColumn<Doctor, Long> colId;
    @FXML private TableColumn<Doctor, String> colFirstName;
    @FXML private TableColumn<Doctor, String> colLastName;
    @FXML private TableColumn<Doctor, String> colEmail;
    @FXML private TableColumn<Doctor, String> colSpecialization;
    @FXML private TableColumn<Doctor, String> colPhone;
;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtSpecialization;
    @FXML private TextField txtPhone;
    @FXML private ComboBox<Department> cbDepartment;
    @FXML private TextField txtSearch;

    private final DoctorService doctorService = new DoctorService();
    private final DepartmentService departmentService = new DepartmentService();
    private final ObservableList<Doctor> doctorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getDoctorId()).asObject());
        colFirstName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));
        colLastName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        colSpecialization.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSpecialization()));
        colPhone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));

        loadDepartments();
        loadDoctors();

        // Populate fields when a doctor is selected
        doctorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                txtFirstName.setText(selected.getFirstName());
                txtLastName.setText(selected.getLastName());
                txtEmail.setText(selected.getEmail());
                txtSpecialization.setText(selected.getSpecialization());
                txtPhone.setText(selected.getPhone());

                // Get the Department object using the stored departmentId
                Department dept = departmentService.getDepartmentById(selected.getDepartment());
                cbDepartment.setValue(dept);
            }
        });

    }

    private void loadDepartments() {
        List<Department> departments = departmentService.getAllDepartment();
        cbDepartment.setItems(FXCollections.observableArrayList(departments));

        // Show name in ComboBox
        cbDepartment.setConverter(new StringConverter<>() {
            @Override
            public String toString(Department dept) {
                return dept == null ? "" : dept.getDeptName();
            }
            @Override
            public Department fromString(String string) {
                return null;
            }
        });
    }

    private void loadDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctor();
        doctorList.setAll(doctors);
        doctorTable.setItems(doctorList);
    }

    @FXML
    private void addDoctor() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();
        String specialization = txtSpecialization.getText().trim();
        String phone = txtPhone.getText().trim();
        Department selectedDept = cbDepartment.getValue();

        // Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                specialization.isEmpty() || phone.isEmpty() || selectedDept == null) {
            showWarning("Validation Error", "All fields are required. Please fill in all details.");
            return;
        }

        if (!doctorService.isEmailUnique(email, 0)) {
            showWarning("Validation Error", "This email already exists.");
            return;
        }

        if (!doctorService.isContactNumberUnique(phone, 0)) {
            showWarning("Validation Error", "This contact number already exists.");
            return;
        }

        Doctor doctor = new Doctor(firstName, lastName,  specialization ,email, phone, selectedDept.getDeptId());
        doctorService.addDoctor(doctor);
        showInfo("Success", "Doctor added successfully: " + firstName + " " + lastName);
        loadDoctors();
        clearFields();
    }

    @FXML
    private void updateDoctor() {
        Doctor selected = doctorTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a doctor to update.");
            return;
        }

        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();
        String specialization = txtSpecialization.getText().trim();
        String phone = txtPhone.getText().trim();
        Department selectedDept = cbDepartment.getValue();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                specialization.isEmpty() || phone.isEmpty() || selectedDept == null) {
            showWarning("Validation Error", "All fields are required.");
            return;
        }

        if (!doctorService.isEmailUnique(email, selected.getDoctorId())) {
            showWarning("Validation Error", "This email already exists.");
            return;
        }

        if (!doctorService.isContactNumberUnique(phone, selected.getDoctorId())) {
            showWarning("Validation Error", "This contact number already exists.");
            return;
        }

        selected.setFirstName(firstName);
        selected.setLastName(lastName);
        selected.setEmail(email);
        selected.setSpecialization(specialization);
        selected.setPhone(phone);
        selected.setDepartment(selectedDept.getDeptId());

        doctorService.updateDoctor(selected);
        showInfo("Success", "Doctor updated successfully: " + firstName + " " + lastName);
        loadDoctors();
        clearFields();
    }

    @FXML
    private void deleteDoctor() {
        Doctor selected = doctorTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a doctor to delete.");
            return;
        }

        doctorService.deleteDoctor(selected.getDoctorId());
        showInfo("Success", "Doctor deleted successfully: " + selected.getFirstName() + " " + selected.getLastName());
        loadDoctors();
        clearFields();
    }

    @FXML
    private void searchDoctor() {
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            loadDoctors();
        } else {
            List<Doctor> doctors = doctorService.findDoctorsBySpecialization(searchTerm);
            doctorList.setAll(doctors);
            doctorTable.setItems(doctorList);
        }
    }

    @FXML
    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtSpecialization.clear();
        txtPhone.clear();
        cbDepartment.setValue(null);
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
