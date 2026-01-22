package hospital.hospital_management_system2.model;

import java.time.LocalDateTime;

public class Appointment {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private String status;
    private String reason;
    private String departmentName;

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

    public String getDepartment(){return departmentName;}
    public Long getAppointmentId() { return appointmentId; }
    public Long getPatientId() { return patientId; }
    public Long getDoctorId() { return doctorId; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public String getStatus() { return status; }
    public String getReason() { return reason; }


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
