package hospital.hospital_management_system2.model;

import java.time.LocalDate;

public class PatientFeedback {
    private int feedback;
    private int patientId;
    private int rating;
    private String comments;
    private LocalDate feedbackDate;

    public PatientFeedback(int feedback, int patientId, int rating, String comments, LocalDate feedbackDate) {
        this.feedback = feedback;
        this.patientId = patientId;
        this.rating = rating;
        this.comments = comments;
        this.feedbackDate = feedbackDate;
    }

    public PatientFeedback(int patientId, int rating, String comments, LocalDate feedbackDate) {
        this.patientId = patientId;
        this.rating = rating;
        this.comments = comments;
        this.feedbackDate = feedbackDate;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(LocalDate feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    @Override
    public String toString() {
        return "PatientFeedback{" +
                "feedback=" + feedback +
                ", patientId=" + patientId +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", feedbackDate=" + feedbackDate +
                '}';
    }
}
