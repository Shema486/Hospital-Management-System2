package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.AppointmentDao;
import hospital.hospital_management_system2.model.Appointment;

import java.util.List;

public class AppointmentService {
    private final AppointmentDao appointmentDao = new AppointmentDao();

    public void addAppointment(Appointment appointment) {
        appointmentDao.addAppointment(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDao.findAll();
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentDao.deleteAppointment(appointmentId);
    }

    public void updateStatus(Long appointmentId, String status) {
        appointmentDao.updateStatus(appointmentId, status);
    }
}

