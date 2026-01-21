package hospital.hospital_management_system2.dao;

import hospital.hospital_management_system2.model.Patient;
import hospital.hospital_management_system2.model.PatientFeedback;
import hospital.hospital_management_system2.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao {

    // CREATE
    public void addFeedback(PatientFeedback feedback) {
        String sql = """
            INSERT INTO patient_feedback (patient_id, rating, comments, feedback_date)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, feedback.getPatientId());
            ps.setInt(2, feedback.getRating());
            ps.setString(3, feedback.getComments());
            ps.setDate(4, Date.valueOf(feedback.getFeedbackDate()));

            ps.executeUpdate();
            System.out.println("Feedback added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ by ID
    public PatientFeedback findById(Long feedbackId) {
        String sql = "SELECT * FROM patient_feedback WHERE feedback_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, feedbackId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // READ ALL
    public List<PatientFeedback> findAll() {
        List<PatientFeedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM patient_feedback";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                feedbackList.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    // DELETE
    public void deleteFeedback(Long feedbackId) {
        String sql = "DELETE FROM patient_feedback WHERE feedback_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, feedbackId);
            ps.executeUpdate();
            System.out.println("Feedback deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mapper
    private PatientFeedback mapRow(ResultSet rs) throws SQLException {
        return new PatientFeedback(
                rs.getLong("feedback_id"),
                rs.getLong("patient_id"),
                rs.getInt("rating"),
                rs.getString("comments"),
                rs.getDate("feedback_date").toLocalDate()
        );
    }
}
