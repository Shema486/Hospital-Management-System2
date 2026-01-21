package hospital.hospital_management_system2.dao;

import hospital.hospital_management_system2.model.Doctor;
import hospital.hospital_management_system2.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDao {

    public void addDoctor(Doctor doctor){
        String sql = "INSERT INTO doctors (first_name, last_name, email, specialization, phone, dept_id) VALUES(?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,doctor.getFirstName());
            ps.setString(2,doctor.getLastName());
            ps.setString(3,doctor.getEmail());
            ps.setString(4, doctor.getSpecialization());
            ps.setString(5, doctor.getPhone());
            ps.setLong(6, doctor.getDepartment());

            ps.executeUpdate();
            System.out.println("Doctor added successfully: ");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Doctor> getAllDoctors(){
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                doctors.add(mapRowToDoctor(rs));
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }
        return doctors;
    }
    public void updateDoctor(Doctor doctor){
        String sql = "UPDATE doctors SET first_name = ?, last_name = ?, email = ?, specialization = ?, phone = ?, dept_id = ? WHERE doctor_id = ? ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, doctor.getFirstName());
            ps.setString(2, doctor.getLastName());
            ps.setString(3, doctor.getEmail());
            ps.setString(4, doctor.getSpecialization());
            ps.setString(5, doctor.getPhone());
            ps.setLong(7,doctor.getDoctorId());

            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteDoctor(Long doctorId){
        String sql = "DELETE FROM doctors WHERE  doctor_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setLong(1,doctorId);
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<Doctor> findDoctorsBySpecialization(String specialization){
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE specialization ILIKE ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,"%" + specialization + "%");
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    doctors.add(mapRowToDoctor(rs));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return doctors;
    }
    public List<Doctor> getDoctorsPaginated(int limit, int offset){
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors ORDER BY doctor_id LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctors.add(mapRowToDoctor(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }
    private Doctor mapRowToDoctor(ResultSet rs) throws SQLException {
        return new Doctor(
                rs.getLong("doctor_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("specialization"),
                rs.getString("phone"),
                rs.getLong("dept_id")
        );
    }

}
