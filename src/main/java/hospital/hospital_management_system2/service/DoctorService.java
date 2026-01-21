package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.DoctorDao;
import hospital.hospital_management_system2.model.Doctor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorService {
    private final DoctorDao doctorDao = new DoctorDao();
    private final Map<Long, Doctor> doctorCache = new HashMap<>();

    public void addPatient(Doctor doctor){
        doctorDao.addDoctor(doctor);
        if(doctor.getDoctorId() != null){
            doctorCache.put(doctor.getDoctorId(),doctor);
        }
    }
    public List<Doctor> getAllDoctor(){
        List<Doctor> doctors  = doctorDao.getAllDoctors();
        for (Doctor dct: doctors){
            doctorCache.put(dct.getDoctorId(),dct);
        }
        return doctors;
    }
    public void updateDoctor(Doctor doctor){
       doctorDao.updateDoctor(doctor);
       doctorCache.put(doctor.getDoctorId(),doctor);
    }

    public void deleteDoctor(Long doctorId){
        doctorDao.deleteDoctor(doctorId);
        doctorCache.remove(doctorId);

    }
    public List<Doctor> findDoctorsBySpecialization(String specialization){
        List<Doctor> doctors = doctorDao.findDoctorsBySpecialization(specialization);
        for(Doctor d: doctors){
            doctorCache.put(d.getDoctorId(),d);
        }
        return  doctors;
    }
    public List<Doctor> getDoctorsPaginated(int limit, int offset) {
        List<Doctor> doctors = doctorDao.getDoctorsPaginated(limit, offset);
        for (Doctor d : doctors) {
            doctorCache.put(d.getDoctorId(), d);
        }
        return doctors;
    }
}
