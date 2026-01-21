package hospital.hospital_management_system2.dao;

import hospital.hospital_management_system2.model.Appointment;
import hospital.hospital_management_system2.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, status, reason) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, appointment.getPatientId());
            ps.setLong(2, appointment.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            ps.setString(4, appointment.getStatus());
            ps.setString(5, appointment.getReason());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = """
            SELECT a.appointment_id,
                   a.patient_id, p.first_name || ' ' || p.last_name AS patient_name,
                   a.doctor_id, d.first_name || ' ' || d.last_name AS doctor_name,
                   a.appointment_date,
                   a.status,
                   a.reason
            FROM appointments a
            JOIN patients p ON a.patient_id = p.patient_id
            JOIN doctors d ON a.doctor_id = d.doctor_id
            ORDER BY a.appointment_date
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getLong("appointment_id"),
                        rs.getLong("patient_id"),
                        rs.getString("patient_name"),
                        rs.getLong("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getTimestamp("appointment_date").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getString("reason")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public void deleteAppointment(Long appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(Long appointmentId, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setLong(2, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
