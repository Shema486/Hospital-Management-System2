package hospital.hospital_management_system2.dao;

import hospital.hospital_management_system2.model.Patient;
import hospital.hospital_management_system2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDao {
    public void addPatient(Patient patient)  {
        if (patient.getGender() == null ||
                !(patient.getGender().equalsIgnoreCase("male") || patient.getGender().equalsIgnoreCase("female"))) {
            System.out.println("Error: Invalid gender");
            return;
        }
        String sql = "INSERT INTO patients " +
                "(first_name, last_name, dob, gender, contact_number, address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setDate(3, java.sql.Date.valueOf(patient.getDob()));
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getContactNumber());
            ps.setString(6, patient.getAddress());

            ps.executeUpdate();
            System.out.println("Insertion successful");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Patient> searchPatientByLastName(String last_name){
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE last_name ILIKE ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" +last_name + "%");

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    patients.add(mapRowToPatient(rs));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return patients;
    }
    public List<Patient> getAllPatients(){
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                patients.add(mapRowToPatient(rs));

            }

        }catch (SQLException e ){
            e.printStackTrace();
        }
        return patients;
    }
    public void deletePatient( long patientId){
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps= conn.prepareStatement(sql)){
            ps.setLong(1,patientId);
            ps.executeUpdate();
            System.out.println("Patient(s) deleted");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updatePatient(Patient patient) {
        String sql = "UPDATE patients SET " +
                "first_name = ?, last_name = ?, dob = ?, gender = ?, contact_number = ?, address = ? " +
                "WHERE patient_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setDate(3, java.sql.Date.valueOf(patient.getDob()));
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getContactNumber());
            ps.setString(6, patient.getAddress());
            ps.setLong(7, patient.getPatientId());

            int rows = ps.executeUpdate();
            System.out.println(rows + " patient(s) updated");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Patient mapRowToPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getLong("patient_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("dob").toLocalDate(),
                rs.getString("gender"),
                rs.getString("contact_number"),
                rs.getString("address")
        );
    }
    public Patient searchPatientById(long patientId) {

        String sql = "SELECT * FROM patients WHERE patient_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPatient(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
