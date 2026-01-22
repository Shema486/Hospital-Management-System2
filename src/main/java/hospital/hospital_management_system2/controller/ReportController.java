package hospital.hospital_management_system2.controller;

import hospital.hospital_management_system2.model.*;
import hospital.hospital_management_system2.service.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportController {

    @FXML private ComboBox<Patient> cbPatient;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Long> colAppId;
    @FXML private TableColumn<Appointment, String> colDoctor;
    @FXML private TableColumn<Appointment, String> colDepartment;
    @FXML private TableColumn<Appointment, String> colDate;
    @FXML private TableColumn<Appointment, String> colStatus;

    @FXML private Label lblPatientInfo;
    @FXML private Label lblTotalAppointments;
    private final DoctorService doctorService = new DoctorService();
    private final PatientService patientService = new PatientService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final DepartmentService departmentService = new DepartmentService();
    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    public void initialize() {
        // ===== Table column setup =====
        colAppId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleLongProperty(data.getValue().getAppointmentId()).asObject()
        );

        colDoctor.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(getDoctorDisplay(data.getValue()))
        );

        colDepartment.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(getDepartmentDisplay(data.getValue()))
        );

        colDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getAppointmentDate() != null ?
                                dateFormatter.format(data.getValue().getAppointmentDate()) : "N/A"
                )
        );

        colStatus.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getStatus() != null ? data.getValue().getStatus() : "N/A"
                )
        );

        // Load patients into ComboBox
        loadPatients();

        // Show appointments when a patient is selected
        cbPatient.setOnAction(e -> showPatientAppointments());}

    private void loadPatients() {
        List<Patient> patients = patientService.getAllPatients();
        cbPatient.setItems(FXCollections.observableArrayList(patients));
        cbPatient.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getFirstName() + " " + item.getLastName() + " (ID: " + item.getPatientId() + ")");
                }
            }
        });

        cbPatient.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Patient item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getFirstName() + " " + item.getLastName());
                }
            }
        });
    }

    // ===== Show selected patient's appointments =====
    private void showPatientAppointments() {
        Patient selected = cbPatient.getValue();
        if (selected == null) return;

        // Display patient info
        lblPatientInfo.setText(
                "Patient: " + selected.getFirstName() + " " + selected.getLastName() +
                        " | DOB: " + selected.getDob() +
                        " | Contact: " + selected.getContactNumber()
        );

        // Filter appointments by patientId
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        List<Appointment> patientAppointments = allAppointments.stream()
                .filter(app -> app.getPatientId().equals(selected.getPatientId()))
                .toList();

        // Update TableView
        appointmentList.setAll(patientAppointments);
        appointmentTable.setItems(appointmentList);

        lblTotalAppointments.setText("Total Appointments: " + patientAppointments.size());
    }

    // ===== Utilities =====
    private String getDoctorDisplay(Appointment appointment) {
        if (appointment == null) return "N/A";
        String doctorName = appointment.getDoctorName() != null ? appointment.getDoctorName() : "Unknown Doctor";
        return "Dr. " + doctorName;
    }

    private String getDepartmentDisplay(Appointment appointment) {
        if (appointment == null || appointment.getDoctorId() <= 0) {
            return "N/A";
        }

        Doctor doctor = doctorService.getDoctorById(appointment.getDoctorId());
        if (doctor == null || doctor.getDepartment() <= 0) {
            return "N/A";
        }

        return departmentService.getDepartmentNameById(doctor.getDepartment());
    }





}
