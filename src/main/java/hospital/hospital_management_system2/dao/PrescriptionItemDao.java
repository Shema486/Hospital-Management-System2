package hospital.hospital_management_system2.dao;


import hospital.hospital_management_system2.model.PrescriptionItem;
import hospital.hospital_management_system2.util.DBConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionItemDao {
    public void addPrescriptionItem(PrescriptionItem item) {
        String sql = """
            INSERT INTO prescription_items
            (prescription_id, item_id, dosage_instruction, quantity_dispensed)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, item.getPrescriptionId());
            ps.setLong(2, item.getItemId());
            ps.setString(3, item.getDosageInstruction());
            ps.setInt(4, item.getQuantityDispensed());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PrescriptionItem> findByPrescription(Long prescriptionId) {
        List<PrescriptionItem> items = new ArrayList<>();

        String sql = """
            SELECT prescription_id, item_id, dosage_instruction, quantity_dispensed
            FROM prescription_items
            WHERE prescription_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, prescriptionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void deleteItem(Long prescriptionId, Long itemId) {
        String sql = """
            DELETE FROM prescription_items
            WHERE prescription_id = ? AND item_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, prescriptionId);
            ps.setLong(2, itemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isItemUsedInPrescriptions(Long itemId) {
        String sql = "SELECT COUNT(*) FROM prescription_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, itemId);

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

    private PrescriptionItem mapRow(ResultSet rs) throws SQLException {
        return new PrescriptionItem(
                rs.getLong("prescription_id"),
                rs.getLong("item_id"),
                rs.getString("dosage_instruction"),
                rs.getInt("quantity_dispensed")
        );
    }
}
