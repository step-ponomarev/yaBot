package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private Properties properties;

    public ApplicationProperties() {
        properties = new Properties();
    }

    public void init(final InputStream propertiesInputStream) throws IOException {
        properties.load(propertiesInputStream);
    }

    public String getProperty(final String key) {
        return this.properties.getProperty(key);
    }
}
