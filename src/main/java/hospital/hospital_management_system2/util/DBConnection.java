package hospital.hospital_management_system2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String DB_URL = EnvLoader.get("DB_URL",
            EnvLoader.get("DATABASE_URL", ""));
    private static final String DB_HOST = EnvLoader.get("DB_HOST", "######");
    private static final String DB_PORT = EnvLoader.get("DB_PORT", "######");
    private static final String DB_NAME = EnvLoader.get("DB_NAME", "######");
    private static final String DB_USER = EnvLoader.get("DB_USER", "#####");
    private static final String DB_PASSWORD = EnvLoader.get("DB_PASSWORD", "######");

    private DBConnection() {} // Prevent instantiation

    public static Connection getConnection() throws SQLException {
        String url = DB_URL == null || DB_URL.isBlank()
                ? String.format("jdbc:postgresql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME)
                : DB_URL;
        try {
            if ("postgres".equals(DB_USER) && ("postgres".equals(DB_PASSWORD) || DB_PASSWORD.isBlank())) {
                System.err.println("Warning: Using default DB credentials. Set DB_USER/DB_PASSWORD.");
            }
            return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed. URL=" + url + ", user=" + DB_USER);
            throw e;
        }
    }
}
