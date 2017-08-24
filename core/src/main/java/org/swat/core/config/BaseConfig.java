package org.swat.core.config;

import org.apache.commons.lang3.StringUtils;

/**
 * The type Base config.
 * <p>
 * Created by Swatantra Agrawal
 * on 24-Aug-2017.
 */
public abstract class BaseConfig {
  /**
   * The constant NULL_INFO.
   */
  protected static final ConfigInfo NULL_INFO = new ConfigInfo(null, null, null);

  /**
   * Gets level of the configuration.
   *
   * @return the level
   */
  protected abstract String getLevel();

  /**
   * Gets String value of key.
   *
   * @param key the key
   * @return the string
   */
  abstract String getStringInternal(String key);

  /**
   * Gets config info.
   *
   * @param keys the keys
   * @return the config info
   */
  public ConfigInfo getConfigInfo(String... keys) {
    for (String key : keys) {
      String value = getStringInternal(key);
      if (StringUtils.isNotBlank(value)) {
        return new ConfigInfo(getLevel(), key, value);
      }
    }
    return NULL_INFO;
  }

  /**
   * Gets the first String value of keys.
   *
   * @param keys the keys
   * @return the string
   */
  public String getString(String... keys) {
    for (String key : keys) {
      String value = getStringInternal(key);
      if (StringUtils.isNotBlank(value)) {
        return value;
      }
    }
    return null;
  }

  /**
   * Gets the first integer value of keys.
   *
   * @param keys the keys
   * @return the integer value
   */
  public final Integer getIntegerValue(String... keys) {
    String property = getString(keys);
    if (StringUtils.isNotBlank(property)) {
      return Integer.valueOf(property);
    }
    return null;
  }

  /**
   * Gets the first int value of keys.
   *
   * @param defaultValue the default value
   * @param keys         the keys
   * @return the int
   */
  public final int getInt(int defaultValue, String... keys) {
    Integer value = getIntegerValue(keys);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }

  /**
   * Gets the first long value of keys.
   *
   * @param keys the keys
   * @return the long value
   */
  public final Long getLongValue(String... keys) {
    String property = getString(keys);
    if (StringUtils.isNotBlank(property)) {
      return Long.valueOf(property);
    }
    return null;
  }

  /**
   * Gets the first long value of keys.
   *
   * @param defaultValue the default value
   * @param keys         the keys
   * @return the long
   */
  public final long getLong(int defaultValue, String... keys) {
    Integer value = getIntegerValue(keys);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }

  /**
   * Gets the first boolean value of keys.
   *
   * @param keys the keys
   * @return the boolean value
   */
  public final Boolean getBooleanValue(String... keys) {
    String property = getString(keys);
    if (StringUtils.isNotBlank(property)) {
      return Boolean.valueOf(property);
    }
    return null;
  }

  /**
   * Gets the first boolean value of keys.
   *
   * @param defaultValue the default value
   * @param keys         the keys
   * @return the boolean
   */
  public final boolean getBoolean(boolean defaultValue, String... keys) {
    Boolean value = getBooleanValue(keys);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }
}
