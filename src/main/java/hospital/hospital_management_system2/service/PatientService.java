package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.PatientDao;
import hospital.hospital_management_system2.model.Patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientService {

    private final PatientDao patientDAO = new PatientDao();
    private final Map<Long, Patient> patientCache = new HashMap<>();

    public List<Patient> searchPatientByLastName(String lastName) {
        List<Patient> patients = patientDAO.searchPatientByLastName(lastName);
        for (Patient p : patients) {
            patientCache.put(p.getPatientId(), p);
        }
        return patients;
    }



    public void addPatient(Patient patient) {
        patientDAO.addPatient(patient);
        patientCache.put(patient.getPatientId(),patient);
    }

    public void updatePatient(Patient patient) {
        patientDAO.updatePatient(patient);
        patientCache.put(patient.getPatientId(), patient);
    }

    public void deletePatient(long patientId) {
        patientDAO.deletePatient(patientId);
        patientCache.remove(patientId);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = patientDAO.getAllPatients();
        for (Patient p : patients) {
            patientCache.put(p.getPatientId(), p);
        }
        return patients;
    }


    public List<Patient> getPatientsPaginated(int limit, int offset) {
        List<Patient> patients = patientDAO.getPatientsPaginated(limit, offset);
        for (Patient p : patients) {
            patientCache.put(p.getPatientId(), p);
        }
        return patients;
    }
    public boolean isContactNumberUnique(String contactNumber, long excludePatientId) {
        if (patientDAO.contactExistsInPatients(contactNumber, excludePatientId)) {
            return false;
        }
        return true;
    }
}
