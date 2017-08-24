package org.swat.core.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Swatantra Agrawal
 * on 24-Aug-2017.
 */
public final class HierarchicalConfig extends BaseConfig {
  private final BaseConfig[] configs;

  public HierarchicalConfig(BaseConfig... configs) {
    this.configs = configs;
  }

  public HierarchicalConfig addConfig(BaseConfig config) {
    return new HierarchicalConfig(ArrayUtils.add(configs, config));
  }

  @Override
  protected String getLevel() {
    throw new UnsupportedOperationException("This method must never be called.");
  }

  @Override
  String getStringInternal(final String key) {
    throw new UnsupportedOperationException("This method must never be called.");
  }

  @Override
  public ConfigInfo getConfigInfo(String... keys) {
    for (BaseConfig config : configs) {
      ConfigInfo info = config.getConfigInfo(keys);
      if (StringUtils.isNotBlank(info.getString())) {
        return info;
      }
    }
    return NULL_INFO;
  }

  @Override
  public String getString(final String... keys) {
    for (BaseConfig config : configs) {
      String value = config.getString(keys);
      if (StringUtils.isNotBlank(value)) {
        return value;
      }
    }
    return null;
  }
}
