package hospital.hospital_management_system2.dao;

import hospital.hospital_management_system2.model.Department;
import hospital.hospital_management_system2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    public void addDepartment(Department department) {
        String sql = "INSERT INTO departments (dept_name, location_floor) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getDeptName());
            ps.setInt(2, department.getLocationFloor());
            ps.executeUpdate();
            System.out.println("Department added successfully: ");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public List<Department> getAllDepartment(){
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    list.add(mapToDepartment(rs));
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public void updateDepartment(Department department) {
        String sql = "UPDATE departments SET dept_name = ?, location_floor = ? WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, department.getDeptName());
            ps.setInt(2, department.getLocationFloor());
            ps.setLong(3, department.getDeptId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDepartment(Long deptId) {
        String sql = "DELETE FROM departments WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, deptId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Department mapToDepartment(ResultSet rs)throws SQLException{
        return new Department(
                rs.getLong("dept_id"),
                rs.getInt("location_floor"),
                rs.getString("dept_name")
        );
    }

}
