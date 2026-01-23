
-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS prescription_items CASCADE;
DROP TABLE IF EXISTS prescriptions CASCADE;
DROP TABLE IF EXISTS patient_feedback CASCADE;
DROP TABLE IF EXISTS appointments CASCADE;
DROP TABLE IF EXISTS medical_inventory CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
DROP TABLE IF EXISTS doctors CASCADE;
DROP TABLE IF EXISTS departments CASCADE;

-- TABLE: departments
-- Purpose: Store hospital department information
CREATE TABLE departments (
    dept_id SERIAL PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE,
    location_floor INT CHECK (location_floor >= 0)
);

-- TABLE: doctors
-- Purpose: Store doctor information and their department
CREATE TABLE doctors (
    doctor_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    dept_id INT,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    CONSTRAINT fk_doctor_dept FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- TABLE: patients
-- Purpose: Store patient information
CREATE TABLE patients (
    patient_id  SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('Male', 'Female', 'Other')),
    contact_number VARCHAR(15) NOT NULL,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABLE: appointments
-- Purpose: Store appointment records linking patients and doctors
CREATE TABLE appointments (
    appointment_id  SERIAL PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(15) DEFAULT 'Scheduled' CHECK (status IN ('Scheduled', 'Completed', 'Cancelled')),
    reason TEXT,
    CONSTRAINT fk_app_patient FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    CONSTRAINT fk_app_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);

-- TABLE: medical_inventory
-- Purpose: Store medical inventory items (medications, supplies)
CREATE TABLE medical_inventory (
    item_id  SERIAL PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    unit_price NUMERIC(10,2) NOT NULL
);

-- TABLE: prescriptions
-- Purpose: Store prescription information linked to appointments
CREATE TABLE prescriptions (
    prescription_id  SERIAL PRIMARY KEY,
    appointment_id INT NOT NULL UNIQUE,
    date_issued TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    CONSTRAINT fk_pres_app FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);

-- TABLE: prescription_items
-- Purpose: Store items (medications) in each prescription (many-to-many)
CREATE TABLE prescription_items (
    prescription_id INT NOT NULL,
    item_id INT NOT NULL,
    dosage_instruction VARCHAR(255),
    quantity_dispensed INT NOT NULL,
    PRIMARY KEY (prescription_id, item_id),
    CONSTRAINT fk_pres_item_prescription  FOREIGN KEY (prescription_id) REFERENCES prescriptions(prescription_id) ON DELETE CASCADE,
    CONSTRAINT fk_pres_item_inventory FOREIGN KEY (item_id) REFERENCES medical_inventory(item_id) ON DELETE RESTRICT
);

-- TABLE: patient_feedback
-- Purpose: Store patient feedback and ratings
CREATE TABLE patient_feedback (
    feedback_id SERIAL PRIMARY KEY,
    patient_id INT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comments TEXT,
    feedback_date DATE DEFAULT CURRENT_DATE,
    CONSTRAINT fk_feedback_patient FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- INDEXES
-- Purpose: Improve query performance on frequently searched columns

-- Speed up patient search by last name
CREATE INDEX idx_patient_lastname ON patients(last_name);

-- Speed up appointment queries by date
CREATE INDEX idx_appointment_date ON appointments(appointment_date);

-- Speed up doctor lookups by department
CREATE INDEX idx_doctor_dept ON doctors(dept_id);

-- Additional recommended indexes
CREATE INDEX idx_doctor_specialization ON doctors(specialization);
CREATE INDEX idx_appointment_patient_id ON appointments(patient_id);
CREATE INDEX idx_appointment_doctor_id ON appointments(doctor_id);
CREATE INDEX idx_appointment_status ON appointments(status);

-- SAMPLE DATA (Optional - for testing)

-- Insert sample departments
INSERT INTO departments (dept_name, location_floor) VALUES
('Cardiology', 2),
('Neurology', 3),
('Pediatrics', 1),
('Orthopedics', 2),
('Emergency', 1);

-- Insert sample doctors
INSERT INTO doctors (first_name, last_name, specialization, dept_id, email, phone) VALUES
('John', 'Smith', 'Cardiologist', 1, 'john.smith@hospital.com', '555-0101'),
('Sarah', 'Johnson', 'Neurologist', 2, 'sarah.j@hospital.com', '555-0102'),
('Mike', 'Williams', 'Pediatrician', 3, 'mike.w@hospital.com', '555-0103');

-- Insert sample patients
INSERT INTO patients (first_name, last_name, dob, gender, contact_number, address) VALUES
('Alice', 'Brown', '1990-05-15', 'Female', '555-1001', '123 Main St'),
('Bob', 'Davis', '1985-08-22', 'Male', '555-1002', '456 Oak Ave'),
('Carol', 'Wilson', '1992-12-10', 'Female', '555-1003', '789 Elm St');
