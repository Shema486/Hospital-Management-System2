package hospital.hospital_management_system2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    
    private static final Map<String, String> envVars = new HashMap<>();
    
    static {
        loadEnv();
    }
    
    private static void loadEnv() {
        envVars.putAll(System.getenv());

        String customPath = System.getenv("ENV_PATH");
        if (customPath == null || customPath.isBlank()) {
            customPath = System.getProperty("ENV_PATH");
        }
        if (customPath != null && !customPath.isBlank()) {
            loadFromFile(Paths.get(customPath));
        }

        Path cwd = Paths.get(System.getProperty("user.dir"));
        Path cwdEnv = cwd.resolve(".env");
        loadFromFile(cwdEnv);
        loadFromParentDirs(cwd);

        loadFromClasspath(".env");
        loadFromClasspath("hospital/hospital_management_system/.env");
    }

    private static void loadFromFile(Path envPath) {
        if (envPath == null || !Files.exists(envPath)) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(envPath, StandardCharsets.UTF_8)) {
            loadFromReader(reader);
        } catch (IOException e) {
            System.err.println("Warning: Failed to read .env file at " + envPath);
        }
    }

    private static void loadFromClasspath(String resourcePath) {
        try (InputStream stream = EnvLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                loadFromReader(reader);
            }
        } catch (IOException e) {
            System.err.println("Warning: Failed to read .env resource: " + resourcePath);
        }
    }

    private static void loadFromReader(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                        (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1).trim();
                }
                String normalizedKey = key.toUpperCase();
                if (!key.isEmpty()) {
                    envVars.put(key, value);
                    envVars.put(normalizedKey, value);}
            }
        }
    }

    private static void loadFromParentDirs(Path startDir) {
        if (startDir == null) {
            return;
        }
        Path dir = startDir.toAbsolutePath();
        int maxDepth = 6;
        for (int i = 0; i < maxDepth && dir != null; i++) {
            Path envPath = dir.resolve(".env");
            if (Files.exists(envPath)) {
                loadFromFile(envPath);
                return;
            }
            dir = dir.getParent();
        }
    }
    
    public static String get(String key, String defaultValue) {
        return envVars.getOrDefault(key, defaultValue);
    }
}
