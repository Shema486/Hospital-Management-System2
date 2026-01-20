package hospital.hospital_management_system2.model;

import java.time.LocalDate;

public class Appointment {
    private int appointmentId;
    private int patient;
    private int doctor;
    private LocalDate appointmentDate;
    private String status;
    private String reason;

    public Appointment(int appointmentId, int patient, int doctor, LocalDate appointmentDate, String status, String reason) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public Appointment(int patient, int doctor, LocalDate appointmentDate, String status, String reason) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public int getPatient() {
        return patient;}
    public void setPatient(int patient) {
        this.patient = patient;
    }
    public int getDoctor() {
        return doctor;
    }
    public void setDoctor(int doctor) {
        this.doctor = doctor;}
    public LocalDate getAppointmentDate() {
        return appointmentDate;}
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;}
    public String getStatus() {
        return status;}
    public void setStatus(String status) {
        this.status = status;}
    public String getReason() {
        return reason;}
    public void setReason(String reason) {
        this.reason = reason;}

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
