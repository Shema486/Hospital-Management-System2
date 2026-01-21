package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.Appointment;
import hospital.hospital_management_system2.model.Prescription;
import hospital.hospital_management_system2.service.AppointmentService;
import hospital.hospital_management_system2.service.PrescriptionService;
import hospital.hospital_management_system2.model.Prescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrescriptionController {

    @FXML private TableView<Prescription> prescriptionTable;
    @FXML private TableColumn<Prescription, Long> colId;
    @FXML private TableColumn<Prescription, String> colAppointment;
    @FXML private TableColumn<Prescription, LocalDateTime> colDate;
    @FXML private TableColumn<Prescription, String> colNotes;

    @FXML private ComboBox<Appointment> cbAppointment;
    @FXML private TextArea txtNotes;

    private final PrescriptionService prescriptionService = new PrescriptionService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final ObservableList<Prescription> prescriptionList = FXCollections.observableArrayList();
    // Simple lookup to avoid repeated DB calls in cell renderers.
    private final Map<Long, Appointment> appointmentById = new HashMap<>();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleLongProperty(data.getValue().getPrescriptionId()).asObject());
        colAppointment.setCellValueFactory(data -> {
            Long appId = data.getValue().getAppointmentId();
            Appointment app = appointmentById.get(appId);
            return new javafx.beans.property.SimpleStringProperty(getAppointmentDisplay(appId, app));
        });
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrescriptionDate()));
        colNotes.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNotes()));

        loadAppointments();
        loadPrescriptions();

        prescriptionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                txtNotes.setText(selected.getNotes());
            }
        });
    }

    private void loadAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointmentById.clear();
        for (Appointment appointment : appointments) {
            appointmentById.put(appointment.getAppointmentId(), appointment);
        }
        cbAppointment.setItems(FXCollections.observableArrayList(appointments));
        configureAppointmentComboBox();
    }

    private void loadPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        prescriptionList.setAll(prescriptions);
        prescriptionTable.setItems(prescriptionList);
    }

    @FXML
    private void addPrescription() {
        if (cbAppointment.getValue() == null) {
            showWarning("Validation Error", "Appointment is required. Please select an appointment.");
            return;
        }

        Prescription prescription = new Prescription(
                cbAppointment.getValue().getAppointmentId(),
                LocalDateTime.now(),
                txtNotes.getText()
        );
        prescriptionService.addPrescription(prescription);
        loadPrescriptions();
        clearFields();
    }

    @FXML
    private void updatePrescription() {
        Prescription selected = prescriptionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a prescription to update.");
            return;
        }
        prescriptionService.updatePrescriptionNotes(selected.getPrescriptionId(), txtNotes.getText());
        loadPrescriptions();
        clearFields();
    }

    @FXML
    private void deletePrescription() {
        Prescription selected = prescriptionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selection Error", "Please select a prescription to delete.");
            return;
        }
        prescriptionService.deletePrescription(selected.getPrescriptionId());
        loadPrescriptions();
        clearFields();
    }

    @FXML
    private void clearFields() {
        cbAppointment.setValue(null);
        txtNotes.clear();
    }

    private void configureAppointmentComboBox() {
        cbAppointment.setCellFactory(lv -> new ListCell<Appointment>() {
            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(getAppointmentDisplay(item.getAppointmentId(), item));
                }
            }
        });
        cbAppointment.setButtonCell(new ListCell<Appointment>() {
            @Override
            protected void updateItem(Appointment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(getAppointmentDisplay(item.getAppointmentId(), item));
                }
            }
        });
    }

    private String getAppointmentDisplay(Long appointmentId, Appointment appointment) {
        if (appointment == null) {
            return "Appointment " + appointmentId;
        }
        String patientName = "Unknown Patient";
        if (appointment.getPatientName() != null) {
            patientName = appointment.getPatientName();
        }
        String doctorName = "Unknown Doctor";
        if (appointment.getDoctorName() != null) {
            doctorName = "Dr. " + appointment.getDoctorName();
        }
        return "" + appointmentId + " - " + patientName + " - " + doctorName;
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
