package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    private Properties properties;

    public ApplicationProperties(final InputStream propertiesInputStream) {
        properties = new Properties();
//TODO: Класс не должен отвечать за обработку своих ошибок
        try {
            properties.load(propertiesInputStream);

            propertiesInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(final String key) {
        return this.properties.getProperty(key);
    }
}
