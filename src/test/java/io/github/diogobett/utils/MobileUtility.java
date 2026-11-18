package io.github.diogobett.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.github.diogobett.constants.Constants;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class MobileUtility {
  private static AndroidDriver androidDriver;
  private static IOSDriver iosDriver;
  private static WebDriverWait wait;
  private static boolean testingAndroid;
  private static boolean testingIOS;

  private MobileUtility() {}

  public static void startAndroidApp() {
    UiAutomator2Options options =
        new UiAutomator2Options()
            .setDeviceName(MobileConfig.getRequired(Constants.ANDROID_DEVICE_NAME_PROPERTY));
    setIfPresent(options, Constants.ANDROID_DEVICE_UDID_PROPERTY, "appium:udid");
    setIfPresent(options, Constants.ANDROID_DEVICE_VERSION_PROPERTY, "appium:platformVersion");

    String app = MobileConfig.get(Constants.ANDROID_APP_PROPERTY);
    String appPackage = MobileConfig.get(Constants.ANDROID_APP_PACKAGE_PROPERTY);
    String appActivity = MobileConfig.get(Constants.ANDROID_APP_ACTIVITY_PROPERTY);
    if (!app.isBlank()) {
      options.setApp(app);
    } else {
      options.setAppPackage(requiredValue(Constants.ANDROID_APP_PACKAGE_PROPERTY, appPackage));
      options.setAppActivity(requiredValue(Constants.ANDROID_APP_ACTIVITY_PROPERTY, appActivity));
    }
    options.setNoReset(MobileConfig.getBoolean(Constants.ANDROID_NO_RESET_PROPERTY, false));

    androidDriver = new AndroidDriver(getServerUrl(), options);
    testingAndroid = true;
    wait = new WebDriverWait(androidDriver, Duration.ofSeconds(10));
  }

  public static void startIOSApp() {
    XCUITestOptions options =
        new XCUITestOptions()
            .setDeviceName(MobileConfig.getRequired(Constants.IOS_DEVICE_NAME_PROPERTY));
    setIfPresent(options, Constants.IOS_DEVICE_UDID_PROPERTY, "appium:udid");
    setIfPresent(options, Constants.IOS_DEVICE_VERSION_PROPERTY, "appium:platformVersion");

    String app = MobileConfig.get(Constants.IOS_APP_PROPERTY);
    String bundleId = MobileConfig.get(Constants.IOS_BUNDLE_ID_PROPERTY);
    if (!app.isBlank()) {
      options.setApp(app);
    } else {
      options.setBundleId(requiredValue(Constants.IOS_BUNDLE_ID_PROPERTY, bundleId));
    }

    iosDriver = new IOSDriver(getServerUrl(), options);
    testingIOS = true;
    wait = new WebDriverWait(iosDriver, Duration.ofSeconds(10));
  }

  public static void stop() {
    WebDriver activeDriver = testingAndroid ? androidDriver : testingIOS ? iosDriver : null;
    try {
      if (activeDriver != null) {
        activeDriver.quit();
      }
    } finally {
      androidDriver = null;
      iosDriver = null;
      wait = null;
      testingAndroid = false;
      testingIOS = false;
    }
  }

  public static void validateScreen(String activityOrController) {
    Assertions.assertTrue(getActiveDriver().getCurrentUrl().contains(activityOrController));
  }

  public static void validateElementTextEquals(WebElement element, String expectedText) {
    Assertions.assertEquals(expectedText, element.getText());
  }

  public static void validateElementTextContains(WebElement element, String expectedText) {
    Assertions.assertTrue(element.getText().contains(expectedText));
  }

  public static void scroll(String textOrDirection) {
    if (testingAndroid) {
      String escapedText = textOrDirection.replace("\\", "\\\\").replace("\"", "\\\"");
      androidDriver.findElement(
          AppiumBy.androidUIAutomator(
              "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                  + "new UiSelector().textContains(\""
                  + escapedText
                  + "\"))"));
    } else if (testingIOS) {
      iosDriver.executeScript("mobile: scroll", Map.of("direction", textOrDirection.toLowerCase()));
    } else {
      throw new IllegalStateException(Constants.ANDROID_SESSION_REQUIRED_MESSAGE);
    }
  }

  public static void hideKeyboard() {
    if (testingAndroid) {
      androidDriver.hideKeyboard();
    } else if (testingIOS) {
      iosDriver.hideKeyboard();
    } else {
      throw new IllegalStateException(Constants.KEYBOARD_SESSION_REQUIRED_MESSAGE);
    }
  }

  public static void takeScreenshot(String scenarioId) {
    WebDriver driver = getActiveDriver();
    Path screenshotDirectory =
        Path.of(
            MobileConfig.get(
                testingAndroid
                    ? Constants.ANDROID_SCREENSHOTS_PROPERTY
                    : Constants.IOS_SCREENSHOTS_PROPERTY));
    String safeScenarioId = scenarioId.replaceAll(Constants.SCENARIO_ID_UNSAFE_CHARACTERS, "_");
    try {
      Files.createDirectories(screenshotDirectory);
      Path screenshot =
          screenshotDirectory.resolve(safeScenarioId + Constants.SCREENSHOT_FILE_EXTENSION);
      Files.copy(
          ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath(),
          screenshot,
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException exception) {
      throw new IllegalStateException(
          Constants.SCREENSHOT_SAVE_FAILED_MESSAGE.formatted(scenarioId), exception);
    }
  }

  public static void useFingerprint() {
    if (testingAndroid) {
      androidDriver.executeScript("mobile: fingerprint", Map.of("fingerprintId", 1));
    } else if (testingIOS) {
      iosDriver.executeScript("mobile: performTouchID", Map.of("match", true));
    } else {
      throw new IllegalStateException(Constants.BIOMETRIC_SESSION_REQUIRED_MESSAGE);
    }
  }

  public static AndroidDriver getAndroidDriver() {
    if (androidDriver == null) {
      throw new IllegalStateException(Constants.ANDROID_DRIVER_NOT_STARTED_MESSAGE);
    }
    return androidDriver;
  }

  public static IOSDriver getIosDriver() {
    if (iosDriver == null) {
      throw new IllegalStateException(Constants.IOS_DRIVER_NOT_STARTED_MESSAGE);
    }
    return iosDriver;
  }

  public static WebDriverWait getWait() {
    if (wait == null) {
      throw new IllegalStateException(Constants.WAIT_SESSION_REQUIRED_MESSAGE);
    }
    return wait;
  }

  public static boolean isTestingAndroid() {
    return testingAndroid;
  }

  public static boolean isTestingIOS() {
    return testingIOS;
  }

  private static WebDriver getActiveDriver() {
    if (testingAndroid) {
      return androidDriver;
    }
    if (testingIOS) {
      return iosDriver;
    }
    throw new IllegalStateException(Constants.NO_ACTIVE_SESSION_MESSAGE);
  }

  private static URL getServerUrl() {
    try {
      return URI.create(MobileConfig.getRequired(Constants.APPIUM_SERVER_URL_PROPERTY)).toURL();
    } catch (IllegalArgumentException | MalformedURLException exception) {
      throw new IllegalStateException(Constants.INVALID_APPIUM_SERVER_URL_MESSAGE, exception);
    }
  }

  private static String requiredValue(String key, String value) {
    return value.isBlank() ? MobileConfig.getRequired(key) : value;
  }

  private static void setIfPresent(
      UiAutomator2Options options, String property, String capability) {
    String value = MobileConfig.get(property);
    if (!value.isBlank()) {
      options.setCapability(capability, value);
    }
  }

  private static void setIfPresent(XCUITestOptions options, String property, String capability) {
    String value = MobileConfig.get(property);
    if (!value.isBlank()) {
      options.setCapability(capability, value);
    }
  }
}
