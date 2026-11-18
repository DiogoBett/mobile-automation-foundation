package io.github.diogobett.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.diogobett.constants.Constants;
import io.github.diogobett.utils.MobileUtility;

public class Hooks {
  @Before(Constants.ANDROID_TAG)
  public void startAndroidApp() {
    MobileUtility.startAndroidApp();
  }

  @Before(Constants.IOS_TAG)
  public void startIOSApp() {
    MobileUtility.startIOSApp();
  }

  @After(Constants.ANDROID_TAG)
  public void stopAndroidApp(Scenario scenario) {
    stopApp(scenario);
  }

  @After(Constants.IOS_TAG)
  public void stopIOSApp(Scenario scenario) {
    stopApp(scenario);
  }

  private void stopApp(Scenario scenario) {
    try {
      if (scenario.isFailed()
          && (MobileUtility.isTestingAndroid() || MobileUtility.isTestingIOS())) {
        MobileUtility.takeScreenshot(scenario.getId());
      }
    } finally {
      MobileUtility.stop();
    }
  }
}
