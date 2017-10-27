package selenium;

import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;
import utils.fileReader;

import java.util.Map;

public class Driver extends SeleniumSetup{

    public static String SWAT_URL = System.getenv("SWAT_URL");
    public static String BBC_URL = System.getenv("BBC_URL");

    // Holds the WebDriver instance
    public static WebDriver webDriver;
    protected static Map<String,String> elementDefinitions;

    // Initialize a webDriver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
    @BeforeSuite
    public void initializeDriver(){
        webDriver = getDriver();
        //TODO setup to read a csv for elements
        elementDefinitions = fileReader.processCsv(System.getenv("ELEMENT_DEFINITIONS"));
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext context){
        String spec = context.getCurrentSpecification().getName();
        String scenario = context.getCurrentScenario().getName();
    }
    // Close the webDriver instance
    @AfterSuite
    public void closeDriver(){
        webDriver.quit();
    }

    @Step("Go to swat solutions website")
    public void launchSwatApplication() {
        Driver.webDriver.get(SWAT_URL);
    }
    @Step("Go to bbc website")
    public void launchBBCApplication() {
        Driver.webDriver.get(BBC_URL);
    }

}
