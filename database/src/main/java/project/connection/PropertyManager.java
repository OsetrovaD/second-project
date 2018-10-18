package project.connection;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by 1 on 11.03.2018.
 */
@UtilityClass
public class PropertyManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadApplicationProperties();
    }

    private static void loadApplicationProperties() {
        try {
            PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
