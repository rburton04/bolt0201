package selenium;

import com.thoughtworks.gauge.*;
import org.openqa.selenium.WebDriver;
import utils.fileReader;

import java.util.Map;

public class Driver extends SeleniumSetup{

    protected String SWAT_URL = System.getenv("SWAT_URL");
    protected String BBC_URL = System.getenv("BBC_URL");

    // Holds the WebDriver instance
    public static WebDriver webDriver;
    protected static Map<String,String> elementDefinitions;
    protected static String spec = "";

    // Initialize a webDriver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
    @BeforeSuite
    public void initializeDriver(){
        webDriver = getDriver();
        //TODO setup to read a csv for elements
        elementDefinitions = fileReader.processCsv(System.getenv("ELEMENT_DEFINITIONS"));
    }

    @BeforeSpec
    public void beforeScenario(ExecutionContext context){
        spec = context.getCurrentSpecification().getName().toUpperCase();
        //System.setProperty("SPEC", context.getCurrentSpecification().getName());
        //scenario = context.getCurrentScenario().getName();
    }
    // Close the webDriver instance
    @AfterSuite
    public void closeDriver(){
        webDriver.quit();
    }

}
