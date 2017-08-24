package org.swat.core.config;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by Swatantra Agrawal
 * on 24-Aug-2017.
 */
public class ConfigInfo {
  private final String level;
  private final String key;
  private final String value;

  public ConfigInfo(String level, String key, String value) {
    this.level = level;
    this.key = key;
    this.value = value;
  }

  public String getLevel() {
    return level;
  }

  public String getKey() {
    return key;
  }

  public String getString() {
    return value;
  }

  public String getString(String defaultValue) {
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  public int getInteger() {
    return getInteger(0);
  }

  public int getInteger(int defaultValue) {
    return NumberUtils.toInt(value, defaultValue);
  }

  public long getLong() {
    return getLong(0L);
  }

  public long getLong(long defaultValue) {
    return NumberUtils.toLong(value, defaultValue);
  }

  public boolean getBoolean() {
    return getBoolean(false);
  }

  public boolean getBoolean(boolean defaultValue) {
    if (value == null) {
      return defaultValue;
    }
    return Boolean.parseBoolean(value);
  }
}
