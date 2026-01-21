package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.Appointment;
import hospital.hospital_management_system2.model.Patient;
import hospital.hospital_management_system2.model.Doctor;
import hospital.hospital_management_system2.service.AppointmentService;
import hospital.hospital_management_system2.service.PatientService;
import hospital.hospital_management_system2.service.DoctorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentController {

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, String> colPatient;
    @FXML
    private TableColumn<Appointment, String> colDoctor;
    @FXML
    private TableColumn<Appointment, String> colDate;
    @FXML
    private TableColumn<Appointment, String> colStatus;
    @FXML
    private TableColumn<Appointment, String> colReason;

    @FXML
    private ComboBox<Patient> cbPatient;
    @FXML
    private ComboBox<Doctor> cbDoctor;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField tfTime; // format HH:mm
    @FXML
    private ComboBox<String> cbStatus;
    @FXML
    private TextField tfReason;

    private final AppointmentService appointmentService = new AppointmentService();
    private final PatientService patientService = new PatientService();
    private final DoctorService doctorService = new DoctorService();

    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // Table columns
        colPatient.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPatientName()));
        colDoctor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDoctorName()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getAppointmentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        ));
        colStatus.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        colReason.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getReason()));

        // Status options
        cbStatus.getItems().addAll("Scheduled", "Completed", "Cancelled");

        // Load patients and doctors in combo boxes
        loadPatientsAndDoctors();

        // Load appointments
        loadAppointments();

        // Select appointment to fill form
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
            if (selected != null) {
                fillForm(selected);
            }
        });
    }

    private void loadPatientsAndDoctors() {
        List<Patient> patients = patientService.getAllPatients();
        cbPatient.setItems(FXCollections.observableArrayList(patients));
        cbPatient.setConverter(new StringConverter<>() {
            @Override
            public String toString(Patient patient) {
                return patient != null ? patient.getFirstName() + " " + patient.getLastName() : "";
            }

            @Override
            public Patient fromString(String string) {
                return null;
            }
        });

        List<Doctor> doctors = doctorService.getAllDoctor();
        cbDoctor.setItems(FXCollections.observableArrayList(doctors));
        cbDoctor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Doctor doctor) {
                return doctor != null ? doctor.getFirstName() + " " + doctor.getLastName() : "";
            }

            @Override
            public Doctor fromString(String string) {
                return null;
            }
        });
    }

    private void loadAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        appointmentList.setAll(appointments);
        appointmentTable.setItems(appointmentList);
    }

    private void fillForm(Appointment appointment) {
        cbPatient.getSelectionModel().select(
                patientService.getAllPatients().stream()
                        .filter(p -> p.getPatientId().equals(appointment.getPatientId()))
                        .findFirst().orElse(null)
        );

        cbDoctor.getSelectionModel().select(
                doctorService.getAllDoctor().stream()
                        .filter(d -> d.getDoctorId().equals(appointment.getDoctorId()))
                        .findFirst().orElse(null)
        );

        dpDate.setValue(appointment.getAppointmentDate().toLocalDate());
        tfTime.setText(appointment.getAppointmentDate().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        cbStatus.setValue(appointment.getStatus());
        tfReason.setText(appointment.getReason());
    }

    @FXML
    private void addAppointment() {
        Patient selectedPatient = cbPatient.getSelectionModel().getSelectedItem();
        Doctor selectedDoctor = cbDoctor.getSelectionModel().getSelectedItem();

        if (selectedPatient == null || selectedDoctor == null || dpDate.getValue() == null || tfTime.getText().isEmpty() || cbStatus.getValue() == null) {
            showAlert("Validation Error", "All fields must be filled.");
            return;
        }

        LocalTime time;
        try {
            time = LocalTime.parse(tfTime.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            showAlert("Validation Error", "Invalid time format. Use HH:mm.");
            return;
        }

        LocalDateTime appointmentDateTime = LocalDateTime.of(dpDate.getValue(), time);

        Appointment appointment = new Appointment(
                selectedPatient.getPatientId(),
                selectedDoctor.getDoctorId(),
                appointmentDateTime,
                cbStatus.getValue(),
                tfReason.getText()
        );

        appointmentService.addAppointment(appointment);
        loadAppointments();
        clearForm();
    }

    @FXML
    private void updateAppointmentStatus() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null || cbStatus.getValue() == null) {
            showAlert("Selection Error", "Select an appointment and status to update.");
            return;
        }

        appointmentService.updateStatus(selected.getAppointmentId(), cbStatus.getValue());
        loadAppointments();
    }

    @FXML
    private void deleteAppointment() {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Selection Error", "Select an appointment to delete.");
            return;
        }

        appointmentService.deleteAppointment(selected.getAppointmentId());
        loadAppointments();
        clearForm();
    }

    @FXML
    private void clearForm() {
        cbPatient.getSelectionModel().clearSelection();
        cbDoctor.getSelectionModel().clearSelection();
        dpDate.setValue(null);
        tfTime.clear();
        cbStatus.setValue(null);
        tfReason.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
