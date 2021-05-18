package com.automation.foundation.utilityClasses;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.automation.foundation.utilityClasses.MobileStarter.getValue;

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

        caps.setCapability("deviceName", getValue("android.device.name")); // Emulator Name
        caps.setCapability("udid", getValue("android.device.udid")); // Emulator UDID
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", getValue("android.device.version")); // Android Version
        caps.setCapability("appPackage", getValue("android.apk.package"));
        caps.setCapability("appActivity", getValue("android.apk.activity"));
        caps.setCapability("app", getValue("android.apk"));

        try {
            androidDriver = new AndroidDriver<MobileElement>(new URL(getValue("appium.address")), caps);
            androidDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); // Set Find Element Timeout
            wait = new WebDriverWait(androidDriver, 60);
        } catch (MalformedURLException ex) {
            androidDriver.resetApp();
        }

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

    public static WebElement findElementByClassAndText(String elementClass, String elementText) {

        List<MobileElement> elements;
        WebElement foundElement = null;

        if (testingIOS) {
            elements = iosDriver.findElementsByClassName(elementClass);

            for (MobileElement element : elements) {
                if (element.getAttribute("name").contains(elementText)) {
                    foundElement = element;
                    break;
                }
            }
        }

        if (testingAndroid) {
            elements = androidDriver.findElementsByClassName(elementClass);

            for (MobileElement element : elements) {
                if (element.getAttribute("text").contains(elementText)) {
                    foundElement = element;
                    break;
                }
            }
        }

        return foundElement;
    }

    public static void checkKeyboard() {

        if (testingAndroid && androidDriver.isKeyboardShown()) {
            androidDriver.hideKeyboard();
        }

        if (testingIOS && iosDriver.isKeyboardShown()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Done\"]\n"))).click();
        }

    }

    public static String randomNumber() {

        int randomNumber = (int) (Math.random() * 999999);
        return Integer.toString(randomNumber);
    }

    public static void clickDeleteButton() {

        if (testingAndroid) {
            androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.DEL));
        }

        if (testingIOS) {
            iosDriver.getKeyboard().pressKey(Keys.DELETE);
        }

    }

    public static void touchFingerprintSensor() {

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
