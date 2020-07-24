package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private Properties properties;

    public ApplicationProperties(final String propFileName) throws IOException {
        properties = new Properties();

        try (InputStream is = getClass().getResourceAsStream(propFileName)) {
            properties.load(is);
        }
    }

    public String getProperty(final String key) {
        return this.properties.getProperty(key);
    }
}
