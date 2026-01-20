package hospital.hospital_management_system2.model;

import java.time.LocalDate;

public class Doctor {
    private int doctorId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String specialization;
    private String email;
    private String phone;
    private Department department;

    public Doctor(int doctorId, String firstName, String lastName, LocalDate dob, String specialization, String email, String phone, Department department) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.specialization = specialization;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    public Doctor(String firstName, String lastName, LocalDate dob, String specialization, String email, String phone, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.specialization = specialization;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    public Doctor(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;}

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;}

    public String getFirstName() {
        return firstName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;}

    public String getLastName() {
        return lastName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;}

    public LocalDate getDob() {
        return dob;}

    public void setDob(LocalDate dob) {
        this.dob = dob;}

    public String getSpecialization() {
        return specialization;}

    public void setSpecialization(String specialization) {
        this.specialization = specialization;}

    public String getEmail() {
        return email;}

    public void setEmail(String email) {
        this.email = email;}

    public String getPhone() {
        return phone;}

    public void setPhone(String phone) {
        this.phone = phone;}

    public Department getDepartment() {
        return department;}

    public void setDepartment(Department department) {
        this.department = department;}

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", specialization='" + specialization + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", department=" + department +
                '}';
    }
}
