## Automation Foundation

This Repository has been Created to help QA Engineer's to Automate Android and iOS Applications using Java, Selenium, Appium, Cucumber and Gherkin.

**Requirements:**
 - IntelliJ (Recommended)
 - Android Emulator (Recommended) / Real Device
 - iOS Simulator (Recommended) / Real Device
 - Appium Server

**How to Setup:**
1. Open the Repository in IntelliJ and Go to the “MobileHelper” Class
2. Go to the "startAndroidApp()" / "startIOSApp()" Method
3. Change the Appium Capabilities (caps) according to the Device being used (Virtual or Real)

**Android Specific Setup:**
- Change the 'appVariant' variable to the Path of the APK being used for Testing
- Change the 'appPackage' and 'appActivity' variable to the Main App Package and Starting Activity of the App being Tested

**iOS Specific Setup:**
- Change the 'appVariant' variable to the Path of the XCode Full Path of the .app File being used
