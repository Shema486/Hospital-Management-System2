package hospital.hospital_management_system2    .dao;

import hospital.hospital_management_system2.model.MedicalInventory;
import hospital.hospital_management_system2.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalInventoryDao {

    // Create
    public void addInventoryItem(MedicalInventory item) {
        String sql = "INSERT INTO medical_inventory (item_name, stock_quantity, unit_price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getStockQuantity());
            ps.setDouble(3, item.getUnitPrice());
            ps.executeUpdate();
            System.out.println("Inventory item added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update
    public void updateItem(MedicalInventory item) {
        String sql = "UPDATE medical_inventory SET item_name = ?, stock_quantity = ?, unit_price = ? WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getStockQuantity());
            ps.setDouble(3, item.getUnitPrice());
            ps.setLong(4, item.getItemId());

            int updated = ps.executeUpdate();
            if (updated > 0) {
                System.out.println("Inventory item updated successfully.");
            } else {
                System.out.println("No item found with ID: " + item.getItemId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read all
    public List<MedicalInventory> findAll() {
        List<MedicalInventory> items = new ArrayList<>();
        String sql = "SELECT * FROM medical_inventory";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(mapRowToInventory(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Delete
    public void deleteInventoryItem(Long itemId) {
        String sql = "DELETE FROM medical_inventory WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, itemId);
            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                System.out.println("Inventory item deleted successfully.");
            } else {
                System.out.println("No item found with ID: " + itemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean itemNameExists(String itemName, long excludeItemId) {
        String sql = "SELECT COUNT(*) FROM medical_inventory WHERE LOWER(item_name) = LOWER(?) AND item_id != ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemName);
            ps.setLong(2, excludeItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Map row
    private MedicalInventory mapRowToInventory(ResultSet rs) throws SQLException {
        return new MedicalInventory(
                rs.getLong("item_id"),
                rs.getString("item_name"),
                rs.getInt("stock_quantity"),
                rs.getDouble("unit_price")
        );
    }
}
