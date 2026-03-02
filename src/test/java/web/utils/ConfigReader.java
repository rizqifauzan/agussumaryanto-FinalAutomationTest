package web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger =
            LoggerFactory.getLogger(ConfigReader.class);

    private static final Properties properties = new Properties();
    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in classpath");
            }
            properties.load(input);
            logger.info("Configuration file loaded successfully");
        } catch (Exception e) {
            logger.error("Failed to load configuration file", e);
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in config.properties", key);
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    public static int getIntProperty(String key) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            logger.error("Invalid integer value for key '{}'", key);
            throw new RuntimeException("Invalid integer value for key: " + key, e);
        }
    }
}