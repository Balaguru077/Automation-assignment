package MyRunner;

import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;
import manager.Driver;
import manager.DriverManager;

@CucumberOptions(
	features = "src/main/java/Features/todo.feature",
	glue = {"stepDefinitions"},
	plugin = { "json:target/cucumber-reports/CucumberTestReport.json",
			 "html:target/cucumber-reports/CucumberReport.html"} )

public final class TestRunner extends AbstractTestNGCucumberTests {

	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpCucumber() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@SuppressWarnings("deprecation")
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser", "version", "platform" })
	public void setUpClass(String browser, String version, String platform) throws Exception {

		RemoteWebDriver remoteWebdriver = null;

		String username = "balaguru";
		String accesskey = "RoqE89r1JqIyUja6ZRyVCs50BV6XYwAkx7oxIaaQSsVPL05eyp";
		
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("browserName", browser);
		capability.setCapability("browserVersion", version);
		capability.setCapability("platformName", platform);

		capability.setCapability("build", "Assigment Task");
		capability.setCapability("project", "Automation Task");

		capability.setCapability("network", true);
		capability.setCapability("video", true);
		capability.setCapability("console", true);
		capability.setCapability("visual", true);
		capability.setCapability("w3c", true);

		String gridURL = "https://" + username + ":" + accesskey + "@hub.lambdatest.com/wd/hub";
		System.out.println(gridURL);
		remoteWebdriver = new RemoteWebDriver(new URL(gridURL), capability);
		System.out.println(capability);
		Driver.initDriver(remoteWebdriver);
		System.out.println(DriverManager.getDriver().getSessionId());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		testNGCucumberRunner.finish();
	}
}