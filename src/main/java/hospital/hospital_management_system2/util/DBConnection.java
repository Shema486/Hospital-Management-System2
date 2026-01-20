package hospital.hospital_management_system2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

        public static Connection getConnection() throws SQLException {
            String url = "DB_URL";
            String user = "DB_USERNAME";
            String password = "DB_PASSWORD";

            return DriverManager.getConnection(url, user, password);
        }
    }


