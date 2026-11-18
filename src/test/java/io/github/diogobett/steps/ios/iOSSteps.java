package io.github.diogobett.steps.ios;

import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.diogobett.constants.Constants;
import io.github.diogobett.utils.MobileUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class iOSSteps {

  private IOSDriver driver() {
    return MobileUtility.getIosDriver();
  }

  @Given(Constants.IOS_GIVEN_STEP)
  public void something1() {
    // Cucumber Given Method Implementation

    // Example Below:
    WebElement goToFormButton = driver().findElement(By.id("[Element ID]"));
    goToFormButton.click(); // Clicks the Selected Element
  }

  @When(Constants.IOS_WHEN_STEP)
  public void something2() {
    // Cucumber When Method Implementation

    // Example Below:
    WebElement textFormField = driver().findElement(By.xpath("[Element XPath]"));
    textFormField.clear(); // Clears the Input Field
    textFormField.sendKeys(Constants.EXAMPLE_INPUT_TEXT); // Write in the Input Field
  }

  @Then(Constants.IOS_THEN_STEP)
  public void something3() {
    // Cucumber Then Method Implementation

    // Example Below:
    WebElement submitFormButton = driver().findElement(By.name("[Element Name]"));
    submitFormButton.click(); // Clicks the Selected Element
  }
}
