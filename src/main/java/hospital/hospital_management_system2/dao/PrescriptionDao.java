package hospital.hospital_management_system2.dao;

import hospital.hospital_management_system2.model.Prescription;
import hospital.hospital_management_system2.util.DBConnection;
import hospital.hospital_management_system2.model.Prescription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PrescriptionDao {


    public boolean addPrescription(Prescription prescription) {

        String sql = """
            INSERT INTO prescriptions (appointment_id, date_issued, notes)
            VALUES (?, ?, ?)
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, prescription.getAppointmentId());
            ps.setTimestamp(2, Timestamp.valueOf(prescription.getPrescriptionDate()));
            ps.setString(3, prescription.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ BY APPOINTMENT
    public List<Prescription> findByAppointmentId(Long appointmentId) {

        List<Prescription> prescriptions = new ArrayList<>();

        String sql = "SELECT * FROM prescriptions WHERE appointment_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, appointmentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prescriptions.add(mapRowToPrescription(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    // READ ALL
    public List<Prescription> findAll() {

        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions ORDER BY date_issued DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                prescriptions.add(mapRowToPrescription(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }


    // UPDATE (NOTES)
    public boolean updatePrescriptionNotes(Long prescriptionId, String notes) {

        String sql = """
            UPDATE prescriptions
            SET notes = ?
            WHERE prescription_id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, notes);
            ps.setLong(2, prescriptionId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean deletePrescription(Long prescriptionId) {

        String sql = "DELETE FROM prescriptions WHERE prescription_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, prescriptionId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Mapper
    private Prescription mapRowToPrescription(ResultSet rs) throws SQLException {

        return new Prescription(
                rs.getLong("prescription_id"),
                rs.getLong("appointment_id"),
                rs.getTimestamp("date_issued").toLocalDateTime(),
                rs.getString("notes")
        );
    }
}
