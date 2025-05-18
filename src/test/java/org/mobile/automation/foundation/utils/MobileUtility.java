package org.mobile.automation.foundation.utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.mobile.automation.foundation.utils.MobileStarter.getValue;

public class MobileUtility {

    // App Platform Drivers
    private static AndroidDriver<MobileElement> androidDriver;
    private static IOSDriver<MobileElement> iosDriver;

    // Basic Required Variables
    private static WebDriverWait wait;

    // App Platform Verification
    private static boolean testingAndroid;
    private static boolean testingIOS;

    // Appium Driver Methods
    static void startIOSApp() {

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest"); // This Capability is ONLY for iOS Version 13+)
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, getValue("ios.device")); // iPhone Simulator Being Used
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, getValue("ios.version")); // iOS Version Being Used in Simulator
        caps.setCapability(MobileCapabilityType.APP, getValue("ios.app"));
        caps.setCapability("autoAcceptAlerts", true);
        caps.setCapability("useNewWDA", true);

        try {
            iosDriver = new IOSDriver<MobileElement>(new URL(getValue("appium.address")), caps);
            iosDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); // Set Find Element Timeout
            wait = new WebDriverWait(iosDriver, 60);
        } catch (MalformedURLException ex) {
            iosDriver.resetApp();
        }

    }

    static void startAndroidApp() {

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.DEVICE_NAME, getValue("android.device.name")); // Emulator Name
        caps.setCapability(MobileCapabilityType.UDID, getValue("android.device.udid")); // Emulator UDID
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, getValue("android.device.version")); // Android Version
        caps.setCapability("appPackage", getValue("android.apk.package"));
        caps.setCapability("appActivity", getValue("android.apk.activity"));
        caps.setCapability("noReset", getValue("android.reset"));

        if (getValue("android.reset").equals("false")) {
            caps.setCapability(MobileCapabilityType.APP, getValue("android.apk"));
        }

        try {
            androidDriver = new AndroidDriver<MobileElement>(new URL(getValue("appium.address")), caps);
            androidDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); // Set Find Element Timeout
            wait = new WebDriverWait(androidDriver, 60);
        } catch (MalformedURLException ex) {
            androidDriver.resetApp();
        }

    }

    // Mobile Validate Methods
    public static void validateScreen(String activityOrController) {

        if (testingAndroid) {
            Assert.assertTrue(androidDriver.getCurrentUrl().contains(activityOrController));
        }

        if (testingIOS) {
            Assert.assertTrue(iosDriver.getCurrentUrl().contains(activityOrController));
        }

    }

    public static void validateElementTextEquals(WebElement element, String elementText) {

        Assert.assertEquals(element.getText(), elementText);
    }

    public static void validateElementTextContains(WebElement element, String elementText) {

        Assert.assertTrue(element.getText().contains(elementText));
    }

    // Mobile Utility Methods
    public static void scroll(String directionOrElement) {

        HashMap<String, String> scrollSettings = new HashMap<>();
        scrollSettings.put("direction", directionOrElement.toLowerCase());

        if (testingAndroid) {
            androidDriver.findElementByAndroidUIAutomator("new UiScrollable("
                    + "new UiSelector().scrollable(true)).scrollIntoView("
                    + "new UiSelector().textContains(\"" + directionOrElement + "\"));"); // Text of the Element to Scroll To
        }

        if (testingIOS) {
            iosDriver.executeScript("mobile: scroll", scrollSettings); // The "direction" can be up / down / right / left
        }

    }

    public static void hideKeyboard() {

        if (testingAndroid && androidDriver.isKeyboardShown()) {
            androidDriver.hideKeyboard();
        }

        if (testingIOS && iosDriver.isKeyboardShown()) {
            iosDriver.hideKeyboard();
        }

    }

    public static void takeScreenshot(String scenarioId) {

        File sourceFile;
        File destinationFile;

        // Takes Screenshot of Android Simulator & Saves
        if (MobileUtility.isTestingAndroid()) {

            try {
                sourceFile = ((TakesScreenshot) MobileUtility.getAndroidDriver()).getScreenshotAs(OutputType.FILE);
                destinationFile = new File(getValue("android.screenshots") + scenarioId + ".png");
                FileUtils.copyFile(sourceFile, destinationFile);
            } catch (IOException ex) {
                System.out.println("Unable to Take Screenshot of Android Simulator");
            }

        }

        // Takes Screenshot of iOS Simulator & Saves
        if (MobileUtility.isTestingIOS()) {

            try {
                sourceFile = ((TakesScreenshot) MobileUtility.getIosDriver()).getScreenshotAs(OutputType.FILE);
                destinationFile = new File(getValue("ios.screenshots") + scenarioId + ".png");
                FileUtils.copyFile(sourceFile, destinationFile);
            } catch (IOException ex) {
                System.out.println("Unable to Take Screenshot of iOS Simulator");
            }

        }

    }

    public static void useFingerprint() {

        if (testingAndroid) {
            androidDriver.fingerPrint(1);
        }

        if (testingIOS) {
            iosDriver.performTouchID(true);
        }

    }

    // Mobile Helper Getters
    public static AndroidDriver<MobileElement> getAndroidDriver() {
        return androidDriver;
    }

    public static IOSDriver<MobileElement> getIosDriver() {
        return iosDriver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static boolean isTestingAndroid() {
        return testingAndroid;
    }

    public static boolean isTestingIOS() {
        return testingIOS;
    }

    // Mobile Setters
    public static void setTestingAndroid(boolean testingAndroid) {
        MobileUtility.testingAndroid = testingAndroid;
    }

    public static void setTestingIOS(boolean testingIOS) {
        MobileUtility.testingIOS = testingIOS;
    }

}
