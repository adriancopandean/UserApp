package UserApp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
  private Properties properties = new Properties();

  public Config() {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties")) {
      if (input == null) {
        MyLogger.log.info("Unable to find test.properties");
        return;
      }
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getBaseUri() {
    return properties.getProperty("baseUri");
  }
}
