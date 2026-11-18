package io.github.diogobett.utils;

import io.github.diogobett.constants.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MobileConfig {
  private static final Properties PROPERTIES = loadProperties();

  private MobileConfig() {}

  public static String get(String key) {
    return System.getProperty(key, PROPERTIES.getProperty(key, "")).trim();
  }

  public static String getRequired(String key) {
    String value = get(key);
    if (value.isBlank() || value.startsWith(Constants.UNCONFIGURED_VALUE_PREFIX)) {
      throw new IllegalStateException(
          Constants.REQUIRED_PROPERTY_MESSAGE.formatted(key, Constants.CONFIG_FILE_NAME, key));
    }
    return value;
  }

  public static boolean getBoolean(String key, boolean defaultValue) {
    String value = get(key);
    if (value.isBlank()) {
      return defaultValue;
    }
    if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
      throw new IllegalStateException(Constants.INVALID_BOOLEAN_PROPERTY_MESSAGE.formatted(key));
    }
    return Boolean.parseBoolean(value);
  }

  private static Properties loadProperties() {
    Properties properties = new Properties();
    try (InputStream input = MobileConfig.class.getResourceAsStream(Constants.CONFIG_RESOURCE)) {
      if (input == null) {
        throw new IllegalStateException(
            Constants.CONFIG_RESOURCE_NOT_FOUND_MESSAGE.formatted(Constants.CONFIG_RESOURCE));
      }
      properties.load(input);
      return properties;
    } catch (IOException exception) {
      throw new IllegalStateException(
          Constants.CONFIG_RESOURCE_LOAD_FAILED_MESSAGE.formatted(Constants.CONFIG_RESOURCE),
          exception);
    }
  }
}
