package hospital.hospital_management_system2.model;

import java.time.LocalDate;

public class PatientFeedback {

    private Long feedbackId;
    private long patient;
    private int rating;
    private String comments;
    private LocalDate feedbackDate;

    // Empty constructor (required)
    public PatientFeedback() {}

    // Full constructor
    public PatientFeedback(Long feedbackId, Long patient, int rating,
                           String comments, LocalDate feedbackDate) {
        this.feedbackId = feedbackId;
        this.patient = patient;
        this.rating = rating;
        this.comments = comments;
        this.feedbackDate = feedbackDate;
    }

    // Constructor without ID (for INSERT)
    public PatientFeedback(Long patient, int rating, String comments) {
        this.patient = patient;
        this.rating = rating;
        this.comments = comments;
        this.feedbackDate = LocalDate.now();
    }

    // Getters
    public Long getFeedbackId() { return feedbackId; }
    public Long getPatientId() {return patient ;}
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public LocalDate getFeedbackDate() { return feedbackDate; }



    @Override
    public String toString() {
        return "PatientFeedback{" +
                "feedbackId=" + feedbackId +
                ", patientId=" + getPatientId() +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", feedbackDate=" + feedbackDate +
                '}';
    }
}

