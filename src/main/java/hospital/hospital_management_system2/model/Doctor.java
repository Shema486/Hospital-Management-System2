        package hospital.hospital_management_system2.model;

        public class Doctor {
            private Long doctorId;
            private String firstName;
            private String lastName;
            private String specialization;
            private String email;
            private String phone;
            private  long department;

            public Doctor(Long doctorId, String firstName, String lastName, String specialization, String email, String phone, Long department) {
                this.doctorId = doctorId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.specialization = specialization;
                this.email = email;
                this.phone = phone;
                this.department = department;
            }

            public Doctor(String firstName, String lastName,  String specialization, String email, String phone, Long department) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.specialization = specialization;
                this.email = email;
                this.phone = phone;
                this.department = department;
            }

            public Doctor(Long doctorId) {
                this.doctorId = doctorId;
            }



            public Long getDoctorId() {
                return doctorId;}

            public void setDoctorId(Long doctorId) {
                this.doctorId = doctorId;}

            public String getFirstName() {
                return firstName;}

            public void setFirstName(String firstName) {
                this.firstName = firstName;}

            public String getLastName() {
                return lastName;}

            public void setLastName(String lastName) {
                this.lastName = lastName;}

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

            public long getDepartment() {
                return department;}

            public void setDepartment(Long department) {
                this.department = department;}
        }
