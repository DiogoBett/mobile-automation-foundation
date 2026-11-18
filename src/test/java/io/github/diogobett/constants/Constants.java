package io.github.diogobett.constants;

public final class Constants {
  public static final String CONFIG_FILE_NAME = "config.properties";
  public static final String CONFIG_RESOURCE = "/" + CONFIG_FILE_NAME;
  public static final String UNCONFIGURED_VALUE_PREFIX = "[";
  public static final String REQUIRED_PROPERTY_MESSAGE =
      "Required configuration property '%s' is missing. Set it in %s or with -D%s=...";
  public static final String INVALID_BOOLEAN_PROPERTY_MESSAGE =
      "Configuration property '%s' must be true or false.";
  public static final String CONFIG_RESOURCE_NOT_FOUND_MESSAGE =
      "Could not find %s on the classpath.";
  public static final String CONFIG_RESOURCE_LOAD_FAILED_MESSAGE = "Could not load %s.";

  public static final String APPIUM_SERVER_URL_PROPERTY = "appium.server.url";
  public static final String ANDROID_DEVICE_NAME_PROPERTY = "android.device.name";
  public static final String ANDROID_DEVICE_UDID_PROPERTY = "android.device.udid";
  public static final String ANDROID_DEVICE_VERSION_PROPERTY = "android.device.version";
  public static final String ANDROID_APP_PROPERTY = "android.app";
  public static final String ANDROID_APP_PACKAGE_PROPERTY = "android.app.package";
  public static final String ANDROID_APP_ACTIVITY_PROPERTY = "android.app.activity";
  public static final String ANDROID_NO_RESET_PROPERTY = "android.noReset";
  public static final String ANDROID_SCREENSHOTS_PROPERTY = "android.screenshots";
  public static final String IOS_DEVICE_NAME_PROPERTY = "ios.device.name";
  public static final String IOS_DEVICE_UDID_PROPERTY = "ios.device.udid";
  public static final String IOS_DEVICE_VERSION_PROPERTY = "ios.device.version";
  public static final String IOS_APP_PROPERTY = "ios.app";
  public static final String IOS_BUNDLE_ID_PROPERTY = "ios.bundle.id";
  public static final String IOS_SCREENSHOTS_PROPERTY = "ios.screenshots";

  public static final String ANDROID_TAG = "@android";
  public static final String IOS_TAG = "@ios";
  public static final String GLUE_PACKAGE = "io.github.diogobett";
  public static final String ANDROID_FEATURE_PACKAGE = "features.android";
  public static final String IOS_FEATURE_PACKAGE = "features.ios";
  public static final String ANDROID_CUCUMBER_PLUGINS =
      "pretty, html:target/cucumber-reports/android.html, json:target/cucumber-reports/android.json";
  public static final String IOS_CUCUMBER_PLUGINS =
      "pretty, html:target/cucumber-reports/ios.html, json:target/cucumber-reports/ios.json";

  public static final String ANDROID_GIVEN_STEP = "^I do something in the App - Android$";
  public static final String ANDROID_WHEN_STEP = "^I do this in the App - Android$";
  public static final String ANDROID_THEN_STEP = "^This should happen - Android$";
  public static final String IOS_GIVEN_STEP = "^I do something in the App - iOS$";
  public static final String IOS_WHEN_STEP = "^I do this in the App - iOS$";
  public static final String IOS_THEN_STEP = "^This should happen - iOS$";
  public static final String EXAMPLE_INPUT_TEXT = "Example";

  public static final String ANDROID_SESSION_REQUIRED_MESSAGE =
      "Start an Android or iOS session before scrolling.";
  public static final String KEYBOARD_SESSION_REQUIRED_MESSAGE =
      "Start an Android or iOS session before hiding the keyboard.";
  public static final String SCREENSHOT_SAVE_FAILED_MESSAGE =
      "Could not save screenshot for scenario '%s'.";
  public static final String BIOMETRIC_SESSION_REQUIRED_MESSAGE =
      "Start an Android or iOS session before using biometrics.";
  public static final String ANDROID_DRIVER_NOT_STARTED_MESSAGE =
      "The Android driver has not been started.";
  public static final String IOS_DRIVER_NOT_STARTED_MESSAGE =
      "The iOS driver has not been started.";
  public static final String WAIT_SESSION_REQUIRED_MESSAGE =
      "Start an Android or iOS session before requesting a wait.";
  public static final String NO_ACTIVE_SESSION_MESSAGE = "No Android or iOS session is active.";
  public static final String INVALID_APPIUM_SERVER_URL_MESSAGE =
      "The Appium server URL is invalid.";
  public static final String SCENARIO_ID_UNSAFE_CHARACTERS = "[^A-Za-z0-9._-]";
  public static final String SCREENSHOT_FILE_EXTENSION = ".png";

  private Constants() {}
}
