package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.Patient;
import hospital.hospital_management_system2.model.PatientFeedback;
import hospital.hospital_management_system2.service.FeedbackService;
import hospital.hospital_management_system2.service.PatientService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackController {

    // ================= TABLE =================
    @FXML private TableView<PatientFeedback> feedbackTable;
    @FXML private TableColumn<PatientFeedback, Long> colId;
    @FXML private TableColumn<PatientFeedback, String> colPatientName;
    @FXML private TableColumn<PatientFeedback, Integer> colRating;
    @FXML private TableColumn<PatientFeedback, String> colComments;
    @FXML private TableColumn<PatientFeedback, LocalDate> colDate;

    // ================= FORM =================
    @FXML private ComboBox<Patient> cbPatient;
    @FXML private ComboBox<Integer> cbRating;
    @FXML private TextArea txtComments;

    // ================= SERVICES =================
    private final FeedbackService feedbackService = new FeedbackService();
    private final PatientService patientService = new PatientService();

    // ================= DATA =================
    private final ObservableList<PatientFeedback> feedbackList = FXCollections.observableArrayList();
    private final Map<Long, String> patientNameMap = new HashMap<>();

    // ================= INITIALIZE =================
    @FXML
    public void initialize() {

        // ----- Table Columns -----
        colId.setCellValueFactory(data ->
                new SimpleLongProperty(data.getValue().getFeedbackId()).asObject());

        colPatientName.setCellValueFactory(data -> {
            String name = patientNameMap.get(data.getValue().getPatientId());
            return new SimpleStringProperty(name != null ? name : "Unknown");
        });

        colRating.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getRating()).asObject());

        colComments.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getComments()));

        colDate.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getFeedbackDate()));

        // ----- Load Patients -----
        loadPatients();

        // ----- Rating ComboBox -----
        cbRating.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        // ----- Load Feedback -----
        loadFeedback();

        // ----- Table Selection -----
        feedbackTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, selected) -> {
                    if (selected != null) {
                        Patient patient = cbPatient.getItems().stream()
                                .filter(p -> p.getPatientId().equals(selected.getPatientId()))
                                .findFirst()
                                .orElse(null);

                        cbPatient.setValue(patient);
                        cbRating.setValue(selected.getRating());
                        txtComments.setText(selected.getComments());
                    }
                }
        );
    }

    // ================= LOAD PATIENTS =================
    private void loadPatients() {
        List<Patient> patients = patientService.getAllPatients();
        cbPatient.setItems(FXCollections.observableArrayList(patients));

        // Cache patient names
        patientNameMap.clear();
        for (Patient p : patients) {
            patientNameMap.put(
                    p.getPatientId(),
                    p.getFirstName() + " " + p.getLastName()
            );
        }

        cbPatient.setConverter(new StringConverter<>() {
            @Override
            public String toString(Patient patient) {
                return patient == null ? "" :
                        patient.getFirstName() + " " + patient.getLastName();
            }

            @Override
            public Patient fromString(String string) {
                return null;
            }
        });
    }

    // ================= LOAD FEEDBACK =================
    private void loadFeedback() {
        feedbackList.setAll(feedbackService.getAllFeedback());
        feedbackTable.setItems(feedbackList);
    }

    // ================= ADD =================
    @FXML
    private void addFeedback() {
        if (cbPatient.getValue() == null) {
            showWarning("Validation Error", "Please select a patient");
            return;
        }

        if (cbRating.getValue() == null) {
            showWarning("Validation Error", "Please select a rating");
            return;
        }

        if (txtComments.getText().trim().isEmpty()) {
            showWarning("Validation Error", "Please enter comments");
            return;
        }

        PatientFeedback feedback = new PatientFeedback(
                cbPatient.getValue().getPatientId(), // ✅ extract ID
                cbRating.getValue(),
                txtComments.getText()
        );



        feedbackService.addFeedback(feedback);
        loadFeedback();
        clearFields();
    }

    // ================= DELETE =================
    @FXML
    private void deleteFeedback() {
        PatientFeedback selected = feedbackTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select feedback to delete");
            return;
        }

        feedbackService.deleteFeedback(selected.getFeedbackId());
        loadFeedback();
        clearFields();
    }

    // ================= CLEAR =================
    @FXML
    private void clearFields() {
        cbPatient.setValue(null);
        cbRating.setValue(null);
        txtComments.clear();
        feedbackTable.getSelectionModel().clearSelection();
    }

    // ================= ALERT =================
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
