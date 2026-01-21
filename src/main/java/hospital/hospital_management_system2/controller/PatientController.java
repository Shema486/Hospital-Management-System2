package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.Patient;
import hospital.hospital_management_system2.service.PatientService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class PatientController {

    @FXML
    private TableView<Patient> patientTable;

    @FXML private TableColumn<Patient, Long> colId;
    @FXML private TableColumn<Patient, String> colFirstName;
    @FXML private TableColumn<Patient, String> colLastName;
    @FXML private TableColumn<Patient, LocalDate> colDob;
    @FXML private TableColumn<Patient, String> colGender;
    @FXML private TableColumn<Patient, String> colContact;
    @FXML private TableColumn<Patient, String> colAddress;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private DatePicker dpDob;
    @FXML private ComboBox<String> cbGender;
    @FXML private TextField txtContact;
    @FXML private TextField txtAddress;
    @FXML private TextField txtSearch;

    private final PatientService patientService = new PatientService();
    //private final AppointmentService appointmentService = new AppointmentService();
    //private final PrescriptionService prescriptionService = new PrescriptionService();

    private final ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // Table column bindings
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getPatientId()).asObject());
        colFirstName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));
        colLastName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));
        colDob.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDob()));
        colGender.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGender()));
        colContact.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContactNumber()));
        colAddress.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAddress()));

        // Gender options
        cbGender.getItems().addAll("Male", "Female");

        // Disable future dates in DOB picker
        dpDob.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now()));
            }
        });

        loadPatients();

        // Fill form when row selected
        patientTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, selected) -> {
                    if (selected != null) {
                        txtFirstName.setText(selected.getFirstName());
                        txtLastName.setText(selected.getLastName());
                        dpDob.setValue(selected.getDob());
                        cbGender.setValue(selected.getGender());
                        txtContact.setText(selected.getContactNumber());
                        txtAddress.setText(selected.getAddress());
                    }
                }
        );
    }

    private void loadPatients() {
        List<Patient> patients = patientService.getAllPatients();
        patientList.setAll(patients);
        patientTable.setItems(patientList);
    }

    @FXML
    private void addPatient() {

        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        LocalDate dob = dpDob.getValue();
        String gender = cbGender.getValue();
        String contact = txtContact.getText().trim();
        String address = txtAddress.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dob == null ||
                gender == null || contact.isEmpty()) {
            showWarning("Validation Error", "All required fields must be filled.");
            return;
        }

        if (dob.isAfter(LocalDate.now())) {
            showWarning("Validation Error", "Date of birth cannot be in the future.");
            return;
        }

        if (!patientService.isContactNumberUnique(contact, 0)) {
            showWarning("Validation Error", "Contact number already exists.");
            return;
        }

        Patient patient = new Patient(firstName, lastName, dob, gender, contact, address);
        patientService.addPatient(patient);

        loadPatients();
        clearFields();
    }

    @FXML
    private void updatePatient() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a patient to update.");
            return;
        }

        String contact = txtContact.getText().trim();

        if (!patientService.isContactNumberUnique(contact, selected.getPatientId())) {
            showWarning("Validation Error", "Contact number already exists.");
            return;
        }

        selected.setFirstName(txtFirstName.getText().trim());
        selected.setLastName(txtLastName.getText().trim());
        selected.setDob(dpDob.getValue());
        selected.setGender(cbGender.getValue());
        selected.setContactNumber(contact);
        selected.setAddress(txtAddress.getText().trim());

        patientService.updatePatient(selected);
        loadPatients();
        clearFields();
    }

    @FXML
    private void deletePatient() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a patient to delete.");
            return;
        }

        try {
            patientService.deletePatient(selected.getPatientId());
            loadPatients();
            clearFields();

        } catch (Exception e) {
            showWarning("Error", "Failed to delete patient.");
            e.printStackTrace();
        }
    }

    @FXML
    private void searchPatient() {
        String lastName = txtSearch.getText().trim();
        if (lastName.isEmpty()) {
            loadPatients();
        } else {
            List<Patient> patients = patientService.searchPatientByLastName(lastName);
            patientList.setAll(patients);
            patientTable.setItems(patientList);
        }
    }

    @FXML
    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        dpDob.setValue(null);
        cbGender.setValue(null);
        txtContact.clear();
        txtAddress.clear();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
