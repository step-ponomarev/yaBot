package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private Properties properties;

    public ApplicationProperties(final String propFileName) {
        properties = new Properties();
//TODO: Класс не должен отвечать за обработку своих ошибок
        try (InputStream is = getClass().getResourceAsStream(propFileName)) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(final String key) {
        return this.properties.getProperty(key);
    }
}
