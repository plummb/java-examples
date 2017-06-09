package org.swat.core.spring;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by swat
 * on 25/11/14.
 */
public class PropertyPlaceHolder extends PropertyPlaceholderConfigurer {
    public static final String CONFIG_VARIABLE = "ENV_CONF";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyPlaceHolder.class);

    public PropertyPlaceHolder() throws Exception {
        setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_OVERRIDE);
        setSearchSystemEnvironment(true);
        Properties props = new Properties();

        String confFile = getProperty(CONFIG_VARIABLE, props, "dev.properties");

        File file = new File(confFile);
        if (!file.exists()) {
            LOGGER.error("Runtime file does not exist : " + confFile);
            throw new Exception("Runtime file does not exist : " + confFile);
        }
        List<Resource> resources = new ArrayList<>();
        resources.add(new FileSystemResource(file));
        LOGGER.info("Environment configuration file is: " + confFile);

        setLocations(resources.toArray(new Resource[resources.size()]));
    }

    public String getProperty(String key, Properties properties, String defaultValue) {
        String value = System.getProperty(key);
        if (StringUtils.isBlank(value)) {
            value = System.getenv(key);
        }
        if (StringUtils.isBlank(value) && properties != null) {
            value = properties.getProperty(key);
        }
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value;
    }

}