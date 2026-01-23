# Hospital Management System

A comprehensive desktop application for managing hospital operations, built with Java, JavaFX, and PostgreSQL. This system demonstrates advanced software engineering practices including 3-layer architecture, caching, database optimization, and performance measurement.


### Core Functionality
**Patient Management** - Full CRUD operations with search and pagination
**Doctor Management** - Manage doctors with specializations and departments
**Department Management** - Organize hospital departments
**Appointment Scheduling** - Schedule and manage patient appointments
**Prescription Management** - Create and manage prescriptions with items
**Medical Inventory** - Track medical supplies and medications
**Patient Feedback** - Collect and view patient ratings and comments
**Reports** - View patient appointment history and statistics

### Features
**Search Functionality** - Database and in-memory search
**Sorting** - Multiple sorting algorithms (by name, ID, specialization)
**Caching** - HashMap-based caching (90-98% performance improvement)
**Database Indexing** - Optimized queries with B-tree indexes
- 

---

##  Prerequisites


---

##  Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd hospital_management_system
```

### 2. Set Up PostgreSQL Database

#### Create Database
```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE hospital_db;

# Exit psql
\q
```

#### Run Schema Script
```bash
# From project root
psql -U postgres -d hospital_db -f database/schema.sql
```

Or manually:
```bash
psql -U postgres -d hospital_db
\i database/schema.sql
\q
```

### 3. Configure Environment Variables

Create a `.env` file in the project root directory:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=hospital_db
DB_USER=postgres
DB_PASSWORD=your_password_here
```

**Note**: The `.env` file is in `.gitignore` and won't be committed. 

### 4. Build the Project

```bash
# Using Maven Wrapper (recommended)
./mvnw clean compile

# Or using Maven (if installed)
mvn clean compile
```

---

##  Configuration

### Database Configuration

The application reads database credentials from a `.env` file. Create `.env` in the project root:

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=hospital_db
DB_USER=postgres
DB_PASSWORD=your_actual_password
```

**Default Values** (if `.env` not found):
- Host: `localhost`
- Port: `5432`
- Database: `hospital_db`
- User: `postgres`
- Password: `postgres`

For more details,

---

##  Running the Application

### Start the Application

```bash
# Using Maven Wrapper
./mvnw javafx:run

# Or using Maven
mvn javafx:run
```

### Run Performance Demo

```bash
# See caching performance in action
mvn exec:java -Dexec.mainClass="hospital.hospital_management_system.test.PerformanceDemo"
```

### Run Tests

```bash
mvn test
```

---

##  Project Structure

```
hospital_management_system/
├── database/
│   ├── schema.sql                          # Database schema
│   └── DATABASE_SCHEMA_DOCUMENTATION.md   # Schema documentation
├── src/
│   ├── main/
│       ├── java/
│       │   └── hospital/
│       │       └── hospital_management_system/
│       │           ├── controller/         # UI controllers (MVC)
│       │           ├── dao/                # Data Access Objects
│       │           ├── model/              # Entity classes
│       │           ├── services/           # Business logic + caching
│       │           ├── utils/              # Utilities (DBConnection, EnvLoader)
│       │           └── test/               # Performance tests
│       └── resources/
│           └── hospital/
│               └── hospital_management_system/
│                   ├── *.fxml              # UI layouts
├── .env                                    # Database credentials (not in Git)
├── .gitignore                              # Git ignore rules
├── pom.xml                                 # Maven configuration
└── README.md                               # This file
```

### Architecture

```
┌─────────────────────────────────────┐
│     PRESENTATION LAYER              │
│  (Controllers + FXML Views)         │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│     BUSINESS LOGIC LAYER            │
│  (Services + Caching)               │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│     DATA ACCESS LAYER               │
│  (DAOs + JDBC)                      │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│     DATABASE                        │
│  (PostgreSQL)                       │
└─────────────────────────────────────┘
```

---

##  Key Features

### 2. Caching System
- **HashMap-based caching** in Service layer
- **O(1) lookup time** for cached data
- **90-98% performance improvement** for repeated access
- Automatic cache invalidation on updates/deletes


### 3. Database Optimization
- **75-97% faster** searches with indexes
- **3NF normalization** for data integrity
- **PreparedStatement** for SQL injection prevention

### 4. Search & Sort
- **Database search**: Case-insensitive ILIKE queries
- **In-memory search**: Java Streams for cached data
- **Multiple sorting**: By name, ID, specialization, date
- **TimSort algorithm**: O(n log n) complexity


##  Database Schema

### Tables (8 total)
1. **patients** - Patient demographic information
2. **doctors** - Doctor information and specializations
3. **departments** - Hospital departments
4. **appointments** - Patient-doctor appointments
5. **prescriptions** - Prescription records
6. **prescription_items** - Items in prescriptions
7. **medical_inventory** - Medical supplies and medications
8. **patient_feedback** - Patient ratings and comments

### Relationships
- One-to-Many: Patient → Appointments, Doctor → Appointments
- Many-to-Many: Prescriptions ↔ Inventory (via prescription_items)
- One-to-One: Appointment → Prescription


### Run Performance Demo
```bash
This demonstrates:
- Caching performance (90-98% improvement)
- Sorting algorithms
- Search functionality

### Run Unit Tests
```bash
mvn test
```
##  Security

**SQL Injection Prevention**: All queries use PreparedStatement
**Environment Variables**: Sensitive data in `.env` (not in Git)
**Input Validation**: Comprehensive validation in controllers
**Error Handling**: Secure error messages (no sensitive data exposed)

##  Project Statistics

**8 Database Tables** with proper relationships
**8 DAO Classes** for data access
**8 Service Classes** with caching
**8 Controllers** for UI handling
**8 FXML Views** for user interface
**90-98% Performance Improvement** with caching
**3NF Normalized Database**



## Learning Outcomes

This project demonstrates:
**3-Layer Architecture** (MVC pattern)
**Database Design** (Normalization, Indexing)
**Performance Optimization** (Caching, Indexing, Pagination)
**Data Structures** (HashMap, ArrayList)
**Algorithms** (Sorting, Searching)
**Best Practices** (PreparedStatement, Error Handling)

