package org.swat.core.config;

/**
 * Created by Swatantra Agrawal
 * on 24-Aug-2017.
 */
public class SystemConfig extends BaseConfig {
  private final boolean environment;

  public SystemConfig(boolean environment) {
    this.environment = environment;
  }

  @Override
  protected String getLevel() {
    return environment ? "SYS-ENV" : "SYS-PRP";
  }

  @Override
  String getStringInternal(String key) {
    return environment ? System.getenv(key) : System.getProperty(key);
  }
}
