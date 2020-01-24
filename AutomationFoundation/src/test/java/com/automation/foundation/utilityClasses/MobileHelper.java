package com.automation.foundation.utilityClasses;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MobileHelper {

    // App Platform Drivers
    private static AndroidDriver<MobileElement> androidDriver;
    private static IOSDriver<MobileElement> iosDriver;

    // Basic Required Variables
    private static WebDriverWait wait;
    private static TouchAction touch;

    // App Platform Verification
    private static boolean testingAndroid;
    private static boolean testingIOS;

    // Cucumber Methods

    @Given("^I have started the CBoJ Android Application$")
    public void startAndroidApp1() {

        // Testing Android
        testingAndroid = true;

        // Set Android App Variant
        setAndroidAppVariant("");

        // Starts the App
        startAndroidApp(true);
    }

    @Given("^I have started the NBI Android Application$")
    public void startAndroidApp2() {

        // Testing Android
        testingAndroid = true;

        // Set Android App Variant
        setAndroidAppVariant("");

        // Starts the App
        startAndroidApp(false);
    }

    @Given("^I have started the CBoJ iOS Application$")
    public void startIOSApp1() {

        // Testing iOS
        testingIOS = true;

        // Set iOS App Variant
        setIOSdAppVariant("");

        // Starts the App
        startIOSApp(true);
    }

    @Given("^I have started the NBI iOS Application$")
    public void startIOSApp2() {

        // Testing iOS
        testingIOS = true;

        // Set iOS App Variant
        setIOSdAppVariant("");

        // Starts the App
        startIOSApp(false);
    }

    @Then("^I should Close the Android Application$")
    public void closeAndroidApp() {

        // Testing Android
        testingAndroid = false;

        // Reset App Variant
        setAndroidAppVariant(null);

        // Close Android App
        androidDriver.closeApp();

        // Completing Test
        Assert.assertFalse(testingAndroid);
    }

    @Then("^I should Close the iOS Application$")
    public void closeIOSApp() {

        // Testing iOS
        testingIOS = false;

        // Reset App Variant
        setIOSdAppVariant(null);

        // Close iOS App
        iosDriver.closeApp();

        // Completing Test
        Assert.assertFalse(testingIOS);
    }

    @After
    public void checkIfFailed(Scenario scenario) {

        if (scenario.isFailed()) {
            takeScreenshot();
        }
    }

    // Appium Driver Methods

    private void startIOSApp(boolean environment) {

        String appVariant; // Environment 1 || Environment 2 - Use App Path provided by XCode

        if (environment) {
            appVariant = "[XCode App 1 Path]";
        } else {
            appVariant = "[XCode App 2 Path]";
        }

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11 Pro"); // iPhone Simulator Being Used
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.2"); // iOS Version Being Used
        caps.setCapability(MobileCapabilityType.APP, appVariant);
        caps.setCapability("autoAcceptAlerts", true);

        try {
            iosDriver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            wait = new WebDriverWait(iosDriver, 60);
            touch = new TouchAction(iosDriver);
        } catch (MalformedURLException ex) {
            iosDriver.resetApp();
        }

    }

    private void startAndroidApp(boolean environment) {

        String appVariant; // Environment 1 || Environment 2 - Use Full Path from Project Files
        String appPackage;
        String appActivity;

        if (environment) {
            appVariant = "[Your APK Path here]";
            appPackage = "[Your App Package here]";
            appActivity = "[Your App Activity here]";
        } else {
            appVariant = "[Your APK Path here]";
            appPackage = "[Your App Package here]";
            appActivity = "[Your App Activity here]";
        }

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("deviceName", "MobileTesting"); // Emulator Name
        caps.setCapability("udid", "emulator-5554"); // Emulator UDID
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9.0"); // Android Version
        caps.setCapability("appPackage", appPackage);
        caps.setCapability("appActivity", appActivity);
        caps.setCapability("app", appVariant);

        try {
            androidDriver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps); // Appium Server Address
            wait = new WebDriverWait(androidDriver, 60);
            touch = new TouchAction(androidDriver);
        } catch (MalformedURLException ex) {
            androidDriver.resetApp();
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

    // Mobile Utility Methods

    public static void tap(WebElement where) {

        touch.tap(PointOption.point(where.getLocation().getX(), where.getLocation().getY()))
                .perform();
    }

    public static void swipe(WebElement from, WebElement to) {

        touch.press(PointOption.point(from.getLocation().getX(), from.getLocation().getY()))
                .moveTo(PointOption.point(to.getLocation().getX(), to.getLocation().getY()))
                .release()
                .perform();
    }

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

        List<MobileElement> elements = androidDriver.findElementsByClassName(elementClass);
        WebElement foundElement = null;

        for (MobileElement element : elements) {
            if (element.getAttribute("text").contains(elementText)) {
                foundElement = element;
                break;
            }
        }

        return foundElement;
    }

    public static void waitSeconds() {

        if (testingAndroid) {
            androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        if (testingIOS) {
            iosDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

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

    private void takeScreenshot() {

        File sourceFile;
        File destinationFile;

        // Takes Screenshot of Android Simulator & Saves
        if (testingAndroid) {

            try {
                sourceFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
                destinationFile = new File("/Users/diogobettencourt/Desktop/MobileAutomatedTests/MobileAutomatedTests/src/test/resources/capitalBankFeatureFiles/androidScreenshots/Android" + randomNumber() + ".png");
                FileUtils.copyFile(sourceFile, destinationFile);
            } catch (IOException ex) {
                System.out.println("Unable to Take Screenshot of Android Simulator");
            }

        }

        // Takes Screenshot of iOS Simulator & Saves
        if (testingIOS) {

            try {
                sourceFile = ((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.FILE);
                destinationFile = new File("/Users/diogobettencourt/Desktop/MobileAutomatedTests/MobileAutomatedTests/src/test/resources/capitalBankFeatureFiles/iOSScreenshots/iOS" + randomNumber() + ".png");
                FileUtils.copyFile(sourceFile, destinationFile);
            } catch (IOException ex) {
                System.out.println("Unable to Take Screenshot of iOS Simulator");
            }

        }

    }

    // App Variant Setup Methods

    private void setAndroidAppVariant(String appVariant) {
        // Set App Android Variants Value (Optional)
    }

    private void setIOSdAppVariant(String appVariant) {
        // Set App iOS Variants Value (Optional)
    }

}
