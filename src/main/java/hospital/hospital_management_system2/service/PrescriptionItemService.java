package hospital.hospital_management_system2.service;


import hospital.hospital_management_system2.dao.PrescriptionItemDao;
import hospital.hospital_management_system2.model.PrescriptionItem;

import java.util.*;

public class PrescriptionItemService {

    private final PrescriptionItemDao dao = new PrescriptionItemDao();
    private final Map<Long, List<PrescriptionItem>> cache = new HashMap<>();

    public void addPrescriptionItem(PrescriptionItem item) {
        dao.addPrescriptionItem(item);
        cache.remove(item.getPrescriptionId());
    }

    public List<PrescriptionItem> getItemsByPrescription(Long prescriptionId) {
        if (cache.containsKey(prescriptionId)) {
            return cache.get(prescriptionId);
        }
        List<PrescriptionItem> items = dao.findByPrescription(prescriptionId);
        cache.put(prescriptionId, items);
        return items;
    }

    public void deleteItem(Long prescriptionId, Long itemId) {
        dao.deleteItem(prescriptionId, itemId);
        cache.remove(prescriptionId);
    }

    public void clearCache() {
        cache.clear();
    }


    public boolean isItemUsedInPrescriptions(Long itemId) {
        return dao.isItemUsedInPrescriptions(itemId);
    }
}
