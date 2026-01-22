package hospital.hospital_management_system2.util;

import hospital.hospital_management_system2.model.Patient;
import hospital.hospital_management_system2.service.PatientService;

import java.util.List;

public class PerformanceDemo {

    public static void main(String[] args) {
        System.out.println("=== PERFORMANCE DEMO ===\n");

        PatientService patientService = new PatientService();

        // -------------------------
        // Test 1: Cache Performance
        // -------------------------
        System.out.println("Test 1: Cache Performance");
        System.out.println("-------------------------");

        patientService.clearCache(); // Ensure fresh start

        long startDb = System.nanoTime();
        Patient patientFromDb = patientService.getPatientById(1L);
        long endDb = System.nanoTime();

        long startCache = System.nanoTime();
        Patient patientFromCache = patientService.getPatientById(1L);
        long endCache = System.nanoTime();

        long timeDb = endDb - startDb;
        long timeCache = endCache - startCache;

        double improvement = ((double)(timeDb - timeCache) / timeDb) * 100;

        System.out.println("First access (DB): " + timeDb + " ns");
        System.out.println("Second access (Cache): " + timeCache + " ns");
        System.out.println("Performance improvement: " + String.format("%.2f", improvement) + "%\n");

        // -------------------------
        // Test 2: Sorting Performance
        // -------------------------
        System.out.println("Test 2: Sorting Performance");
        System.out.println("---------------------------");

        List<Patient> patients = patientService.getAllPatients();
        System.out.println("Total patients: " + patients.size());

        long sortStart = System.nanoTime();
        List<Patient> sortedPatients = patientService.sortPatientsByLastName(patients);
        long sortEnd = System.nanoTime();

        long sortTime = sortEnd - sortStart;
        System.out.println("Sorting time: " + sortTime + " ns");

        System.out.println("Top 3 patients after sorting by last name:");
        for (int i = 0; i < Math.min(3, sortedPatients.size()); i++) {
            Patient p = sortedPatients.get(i);
            System.out.println((i + 1) + ". " + p.getLastName() + ", " + p.getFirstName());
        }
        System.out.println();

        // -------------------------
        // Test 3: Search Performance
        // -------------------------
        System.out.println("Test 3: Search Performance");
        System.out.println("---------------------------");

        String searchName = "Brown"; // Change to match a last name in your DB
        long searchStart = System.nanoTime();
        List<Patient> searchResults = patientService.searchPatientByLastName(searchName);
        long searchEnd = System.nanoTime();

        long searchTime = searchEnd - searchStart;

        System.out.println("Search for last name '" + searchName + "'");
        System.out.println("Results found: " + searchResults.size());
        System.out.println("Search time: " + searchTime + " ns");

        for (Patient p : searchResults) {
            System.out.println("- " + p.getLastName() + ", " + p.getFirstName());
        }

        System.out.println("\n=== DEMO COMPLETE ===");
    }
}
