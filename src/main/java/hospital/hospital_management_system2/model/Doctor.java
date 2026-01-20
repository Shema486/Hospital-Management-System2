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
    private int department;

    public Doctor(int doctorId, String firstName, String lastName, LocalDate dob, String specialization, String email, String phone, int department) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.specialization = specialization;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    public Doctor(String firstName, String lastName, LocalDate dob, String specialization, String email, String phone, int department) {
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

    public int getDepartment() {
        return department;}

    public void setDepartment(int department) {
        this.department = department;}


}
