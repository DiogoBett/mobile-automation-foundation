## Mobile Automation Foundation

This Repository has been Created to help QA Engineer's to Automate Android and iOS Applications using Java, Selenium, Appium, Cucumber and Gherkin.

### Requirements

- Java 21 (LTS) or newer;
- Maven 3.9.5 or newer;
- Appium 11.0.0 or newer;
- Android Studio (Android Emulator) OR Android device;
- Xcode (iOS simulator) OR iOS device.

### Guidelines:
* Test Run:
  * Run `mvn test` to execute Android test scenarios;
  * Run `mvn test -Pios` to execute iOS test scenarios;
* Formatting:
  * Run `mvn spotless:apply` to format Java sources;
  * Run `mvn spotless:check` to check formatting without changing files.

### Configuration

| Property | Purpose |
| --- | --- |
| `appium.server.url` | Appium server URL; defaults to `http://127.0.0.1:4723` |
| `android.device.name` | Android device or emulator name (required) |
| `android.device.udid` | Optional Android device UDID |
| `android.device.version` | Optional Android platform version |
| `android.app` | Optional APK path; when omitted, package and activity are required |
| `android.app.package` | Installed Android application's package |
| `android.app.activity` | Installed Android application's launch activity |
| `android.noReset` | Preserve app state when true; defaults to false |
| `ios.device.name` | iOS simulator or device name (required for iOS) |
| `ios.device.udid` | Optional iOS device UDID |
| `ios.device.version` | Optional iOS platform version |
| `ios.app` | Optional path to an iOS app bundle |
| `ios.bundle.id` | Installed iOS application's bundle ID when `ios.app` is omitted |

### Key Components

- Appium Java Client and Selenium WebDriver for native mobile automation;
- Cucumber and Gherkin for executable scenarios;
- JUnit Platform Suite and Maven Surefire for test discovery and execution;
- Logback for logging;
- Spotless with Google Java Format for consistent Java formatting.

### Documentation

- [Appium](https://appium.io/docs/en/latest/)
- [Cucumber](https://cucumber.io/docs/cucumber/)
- [Selenium](https://www.selenium.dev/documentation/)