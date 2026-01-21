package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.PrescriptionDao;
import hospital.hospital_management_system2.model.Prescription;

import java.util.*;

public class PrescriptionService {

    private final PrescriptionDao prescriptionDAO = new PrescriptionDao();
    private final Map<Long, Prescription> prescriptionCache = new HashMap<>();

    // CREATE
    public void addPrescription(Prescription prescription) {
        prescriptionDAO.addPrescription(prescription);
        clearCache(); // Clear cache after adding new data
    }

    // READ all prescriptions
    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionDAO.findAll();
        // Add to cache
        for (Prescription p : prescriptions) {
            prescriptionCache.put(p.getPrescriptionId(), p);
        }
        return prescriptions;
    }

    // UPDATE
    public void updatePrescriptionNotes(Long id, String notes) {
        prescriptionDAO.updatePrescriptionNotes(id, notes);
        prescriptionCache.remove(id); // Remove from cache to force refresh
    }

    // DELETE
    public void deletePrescription(Long id) {
        prescriptionDAO.deletePrescription(id);
        prescriptionCache.remove(id);
    }

    public boolean hasPrescriptionForAppointment(Long appointmentId) {
        if (appointmentId == null) {
            return false;
        }
        return !prescriptionDAO.findByAppointmentId(appointmentId).isEmpty();
    }

    // Clear cache
    public void clearCache() {
        prescriptionCache.clear();
    }


}
