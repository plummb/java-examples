package org.swat.core.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Swatantra Agrawal
 * on 24-Aug-2017.
 */
public class FileConfig extends BaseConfig {
  private final Properties properties = new Properties();

  public FileConfig(String... fileNames) {
    for (String fileName : fileNames) {
      loadFile(true, fileName);
    }
  }

  public void addFiles(boolean resource, String... fileNames) {
    for (String fileName : fileNames) {
      loadFile(resource, fileName);
    }
  }

  private void loadFile(boolean resource, String fileName) {
    InputStream inputStream = null;
    try {
      if (resource) {
        inputStream = FileConfig.class.getClassLoader().getResourceAsStream(fileName);
      } else {
        inputStream = new FileInputStream(fileName);
      }
      properties.load(inputStream);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected String getLevel() {
    return "FILE";
  }

  @Override
  String getStringInternal(String key) {
    return properties.getProperty(key);
  }
}
