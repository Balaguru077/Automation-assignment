package stepDefinitions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import manager.DriverManager;



public class ToDoStepDefinition {

	public RemoteWebDriver driver = DriverManager.getDriver();
	WebDriverWait wait;
	List<String> windowHandles;

	@Given("User navigates to {string}")
	public void userNavigatesTo(String url) throws InterruptedException {
		System.out.println(driver.getCapabilities());
		driver.get(url);
		System.out.println("Session ID: " + driver.getSessionId());
		wait = new WebDriverWait(driver, 50);

	}

	@When("User performs an explicit wait until all elements are available")
	public void explicitWaitForElements() {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ep_stats")));
		driver.findElement(By.cssSelector(".ep_stats")).getText();
	}

	@And("User scrolls to the WebElement 'SEE ALL INTEGRATIONS' using scrollIntoView method")
	public void scrollToSeeAllIntegrations() {
		WebElement seamlessCollaboration = driver.findElement(By.xpath("//h2[text()='Seamless Collaboration']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", seamlessCollaboration);
	}

	@And("User clicks on the link")
	public void clickSeeAllIntegrations() {
		
			
		WebElement seeAllIntegrations = driver.findElement(By.xpath("//a[contains(text(),'See All Integrations')]"));
		Actions actions = new Actions(driver);

		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			actions.keyDown(Keys.CONTROL).click(seeAllIntegrations).keyUp(Keys.CONTROL).build().perform();
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			actions.keyDown(Keys.COMMAND).click(seeAllIntegrations).keyUp(Keys.COMMAND).build().perform();
		} else {
			throw new UnsupportedOperationException("Unsupported OS");
		}
		


	}

	@Then("User saves the window handles in a List")
	public void saveWindowHandles() {
		windowHandles = new ArrayList<>(driver.getWindowHandles());
		System.out.println("Window Handles: " + windowHandles);
	}

	@And("User verifies the URL in the new tab")
	public void verifyURLInNewTab() throws Exception {
		if (windowHandles.size() >= 2) {
			String originalHandle = driver.getWindowHandle();
			String newTabHandle = windowHandles.get(1);

			driver.switchTo().window(newTabHandle);

			String expectedURL = "https://www.lambdatest.com/integrations";
			String actualURL = driver.getCurrentUrl();
			assert actualURL.equals(expectedURL) : "URL mismatch!";
			
		
            // Get and print console logs
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logs) {
                System.out.println(entry.getMessage());
            }
			
			
			
			driver.close(); // Close the new tab
			Thread.sleep(2000);
			driver.switchTo().window(originalHandle); // Switch back to the original window
		} else {
			throw new IllegalStateException("Not enough windows/tabs were opened");
		}
	}

	@And("I close the current browser window")
	public void iCloseTheCurrentBrowserWindow() {
		driver.close();
		driver.quit();
	}

}