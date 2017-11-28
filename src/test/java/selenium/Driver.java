package selenium;

import com.thoughtworks.gauge.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.fileReader;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Driver extends SeleniumSetup{

    protected String SWAT_URL = System.getenv("SWAT_URL");
    protected String BBC_URL = System.getenv("BBC_URL");
    protected WebElement lastElement;
    protected List<WebElement> lastElements;

    // Holds the WebDriver instance
    public static WebDriver webDriver;
    protected static boolean positiveTest = true;
    protected static Map<String,String> elementDefinitions;
    protected static String spec = "";
    protected static String scenario = "";

    // Initialize a webDriver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
    @BeforeSuite
    public void initializeDriver(){
        //This removes an old jmeter results file if it exists
        try{
            File f = new File(System.getProperty("user.dir") + "/jmeter/results");
            if(f.isDirectory())
                FileUtils.cleanDirectory(f);
        } catch (Exception e){}


        //TODO setup to read a csv for elements
        elementDefinitions = fileReader.processCsv(System.getenv("ELEMENT_DEFINITIONS"));
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext context){
        spec = context.getCurrentSpecification().getName().toUpperCase();

        //Identifies tests as positive or negative
        if(!spec.toUpperCase().contains("JMETER")){
            webDriver = getDriver();
            webDriver.manage().window().maximize();
            webDriver.switchTo().window(webDriver.getWindowHandle());
        }

        if(spec.toUpperCase().contains("NEGATIVE"))
            positiveTest = false;
        else if(spec.toUpperCase().contains("JMETER")){
            positiveTest = true;
            //TODO handle dealing with the browser that was opened if needed
            //TODO do any other jmeter setup that may be required
        } else
            positiveTest = true;

        if(spec.contains("-"))
            spec = spec.split("-")[0].trim();


        //System.setProperty("SPEC", context.getCurrentSpecification().getName());
        //scenario = context.getCurrentScenario().getName();
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext context){
        scenario = context.getCurrentSpecification().getName().toUpperCase();
    }

    // Close the webDriver instance
    @AfterSuite
    public void closeDriver(){
        if(!spec.toUpperCase().contains("JMETER"))
            webDriver.quit();
        try {
            FileUtils.deleteQuietly(new File("reports/html-report/images/logo.png"));
            FileUtils.copyFile(new File("reports/html-report/images/swatLogo.png"), new File("reports/html-report/images/logo.png"));
            System.out.println("test");
        }catch (Exception e){

        }

    }

}
