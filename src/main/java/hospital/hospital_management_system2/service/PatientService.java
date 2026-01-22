package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.PatientDao;
import hospital.hospital_management_system2.model.Patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PatientService {

    private final PatientDao patientDAO = new PatientDao();
    private final Map<Long, Patient> patientCache = new HashMap<>();


    public Patient getPatientById(long patientId) {
        if (patientCache.containsKey(patientId)) {
            return patientCache.get(patientId); // cache hit
        }
        Patient patient = patientDAO.searchPatientById(patientId); // DB hit
        if (patient != null) {
            patientCache.put(patientId, patient); // store in cache
        }
        return patient;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = patientDAO.getAllPatients();
        // populate cache
        for (Patient p : patients) {
            patientCache.put(p.getPatientId(), p);
        }
        return patients;
    }

    public void addPatient(Patient patient) {
        patientDAO.addPatient(patient);
        patientCache.put(patient.getPatientId(), patient);
    }

    public void updatePatient(Patient patient) {
        patientDAO.updatePatient(patient);
        patientCache.put(patient.getPatientId(), patient);
    }

    public void deletePatient(long patientId) {
        patientDAO.deletePatient(patientId);
        patientCache.remove(patientId);
    }

    public List<Patient> searchPatientByLastName(String lastName) {
        List<Patient> patients = patientDAO.searchPatientByLastName(lastName);
        for (Patient p : patients) {
            patientCache.put(p.getPatientId(), p);
        }
        return patients;
    }
    public  boolean isContactNumberUnique(String contactNumber, long excludePatientId){
        return !patientDAO.contactExistsInPatients(contactNumber,excludePatientId);
    }

    public void clearCache() {
        patientCache.clear();
    }

    // Optional: sort patients by last name
    public List<Patient> sortPatientsByLastName(List<Patient> patients) {
        return patients.stream()
                .sorted((p1, p2) -> p1.getLastName().compareToIgnoreCase(p2.getLastName()))
                .collect(Collectors.toList());
    }
}
