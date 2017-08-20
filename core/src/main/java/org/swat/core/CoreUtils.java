package org.swat.core;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by swat
 * on 16-Aug-2017
 */
public class CoreUtils {
  /**
   * Get boolean value for key
   *
   * @param key The key
   * @return The value
   */
  public static boolean getBoolean(String key) {
    return Boolean.valueOf(getProperty(key));
  }

  /**
   * Get boolean value for key
   *
   * @param key      The key
   * @param defValue Default if not found
   * @return The value
   */
  public static boolean getBoolean(String key, boolean defValue) {
    return Boolean.valueOf(getProperty(key, String.valueOf(defValue)));
  }

  /**
   * Get value for key
   *
   * @param key The key
   * @return The value
   */
  public static String getProperty(String key) {
    return getProperty(key, null);
  }

  /**
   * Get value for key
   *
   * @param key      The key
   * @param defValue Default if not found
   * @return The value
   */
  public static String getProperty(String key, String defValue) {
    return getProperty(key, defValue, true);
  }

  /**
   * Get value for key
   *
   * @param key      The key
   * @param defValue Default if not found
   * @param envFirst Prefer System.getenv than System.getProperty
   * @return The value
   */
  public static String getProperty(String key, String defValue, boolean envFirst) {
    if (StringUtils.isBlank(key)) {
      return null;
    }
    String value = null;
    if (envFirst) {
      value = System.getenv(key);
    }
    if (StringUtils.isBlank(value)) {
      value = System.getProperty(key);
    }
    if (!envFirst && StringUtils.isBlank(value)) {
      value = System.getenv(key);
    }
    if (StringUtils.isBlank(value)) {
      value = defValue;
    }
    return value;
  }

  public static long getLong(String key, long onError) {
    String value = getProperty(key);
    try {
      return Long.parseLong(value);
    } catch (Exception e) {//NOSONAR
      return onError;
    }
  }
}
