package hospital.hospital_management_system2.model;

import java.time.LocalDateTime;

public class Prescription {
    private int prescriptionId;
    private int appointmentId;
    private LocalDateTime prescriptionDate;
    private String notes;

    public Prescription(int prescriptionId, int appointmentId, LocalDateTime prescriptionDate, String notes) {
        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.prescriptionDate = prescriptionDate;
        this.notes = notes;
    }

    public Prescription(int appointmentId, LocalDateTime prescriptionDate, String notes) {
        this.appointmentId = appointmentId;
        this.prescriptionDate = prescriptionDate;
        this.notes = notes;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateTime getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateTime prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId=" + prescriptionId +
                ", appointmentId=" + appointmentId +
                ", prescriptionDate=" + prescriptionDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
