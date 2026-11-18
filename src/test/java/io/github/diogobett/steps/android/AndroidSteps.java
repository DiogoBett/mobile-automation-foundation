package io.github.diogobett.steps.android;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.diogobett.constants.Constants;
import io.github.diogobett.utils.MobileUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AndroidSteps {

  private AndroidDriver driver() {
    return MobileUtility.getAndroidDriver();
  }

  @Given(Constants.ANDROID_GIVEN_STEP)
  public void something1() {
    // Cucumber Given Method Implementation

    // Example Below:
    WebElement goToFormButton = driver().findElement(By.id("[Element ID]"));
    goToFormButton.click(); // Clicks the Selected Element
  }

  @When(Constants.ANDROID_WHEN_STEP)
  public void something2() {
    // Cucumber When Method Implementation

    // Example Below:
    WebElement textFormField = driver().findElement(By.xpath("[Element XPath]"));
    textFormField.clear(); // Clears the Input Field
    textFormField.sendKeys(Constants.EXAMPLE_INPUT_TEXT); // Write in the Input Field
  }

  @Then(Constants.ANDROID_THEN_STEP)
  public void something3() {
    // Cucumber Then Method Implementation

    // Example Below:
    WebElement submitFormButton = driver().findElement(By.name("[Element Name]"));
    submitFormButton.click(); // Clicks the Selected Element
  }
}
