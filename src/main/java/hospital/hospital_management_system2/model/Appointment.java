package hospital.hospital_management_system2.model;

import java.time.LocalDateTime;

public class Appointment {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String patientName;  // New
    private String doctorName;   // New
    private LocalDateTime appointmentDate;
    private String status;
    private String reason;

    public Appointment(Long appointmentId, Long patientId, Long doctorId, LocalDateTime appointmentDate, String status, String reason) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public Appointment(Long appointmentId, Long patientId, String patientName, Long doctorId, String doctorName, LocalDateTime appointmentDate, String status, String reason) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public Appointment(Long patientId, Long doctorId, LocalDateTime appointmentDate, String status, String reason) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.reason = reason;
    }

    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
