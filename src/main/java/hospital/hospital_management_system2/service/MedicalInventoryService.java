package hospital.hospital_management_system2.service;



import hospital.hospital_management_system2.dao.MedicalInventoryDao;
import hospital.hospital_management_system2.model.MedicalInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicalInventoryService {
    private final MedicalInventoryDao inventoryDAO = new MedicalInventoryDao();
    private final Map<Long, MedicalInventory> inventoryCache = new HashMap<>();

    public void addInventoryItem(MedicalInventory item) {
        inventoryDAO.addInventoryItem(item);
        if (item.getItemId() != null) {
            inventoryCache.put(item.getItemId(), item);
        }
    }

    public void updateInventoryItem(MedicalInventory item) {
        inventoryDAO.updateItem(item);
        inventoryCache.put(item.getItemId(), item);
    }

    public void deleteInventoryItem(Long itemId) {
        inventoryDAO.deleteInventoryItem(itemId);
        inventoryCache.remove(itemId);
    }

    public List<MedicalInventory> getAllInventoryItems() {
        List<MedicalInventory> items = inventoryDAO.findAll();
        for (MedicalInventory item : items) {
            inventoryCache.put(item.getItemId(), item);
        }
        return items;
    }

    public void clearCache() {
        inventoryCache.clear();
    }

    public boolean isItemNameUnique(String itemName, long excludeItemId) {
        return !inventoryDAO.itemNameExists(itemName, excludeItemId);
    }
}
