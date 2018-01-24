package com.swatsolutions.bolt.selenium;

import com.swatsolutions.bolt.utils.BoltLibrary;
import com.swatsolutions.bolt.utils.DatabaseConnection;
import com.thoughtworks.gauge.*;

import com.swatsolutions.bolt.utils.ProcessFiles;

import gauge.messages.Messages;
import gauge.messages.Spec;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class BoltDriver {


    protected static WebElement lastElement;
    protected static List<WebElement> lastElements;

    // Holds the WebDriver instance
    public static WebDriver webDriver;
    protected static boolean positiveTest = true;
    protected static Map<String,String> elementDefinitions;
    protected static String remoteUrl = "";
    protected static boolean remoteRun = false;
    protected static String ip = "";
    protected static String port = "";
    protected static String aut = "";
    protected static String spec = "";
    protected static String scenario = "";
    protected static String storedUrl = "";
    protected static int elementWaitTime = 5;

	protected static String buttonType = "button";
	protected static String labelType = "label";
	protected static String specialTextAttribute = "value";
	protected static String pageHeadingLevel = "";
	protected static String textFieldType = "input";
	protected static String textAreaType = "textarea";
	protected static String checkboxType = "input";
	protected static String fieldDefaultType = "";
	protected static String textareaDefaultType = "";
	//potentially add customization for dropdowns, tables, and internal table parts
	protected static int initialAutLoadTimeMs = 0;

    //TODO possibly make a map of all propery data or just get the data when needed?

    // Initialize a webDriver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver

	private void setSystemVars(){
		if(System.getenv("ELEMENT_WAIT_TIME") != null)
			elementWaitTime = Integer.valueOf(System.getenv("ELEMENT_WAIT_TIME"));

		if (System.getenv("IP") != null)
			ip = System.getenv("IP");
		if (System.getenv("PORT") != null)
			port = System.getenv("PORT");
		if (System.getenv("AUT_URL") != null)
			aut = System.getenv("AUT_URL");
		if (System.getenv("BUTTON_TYPE") != null)
			buttonType = System.getenv("BUTTON_TYPE");
		if (System.getenv("LABEL_TYPE") != null)
			labelType = System.getenv("LABEL_TYPE");
		if (System.getenv("SPECIAL_TEXT_ATTRIBUTE") != null)
			specialTextAttribute = System.getenv("SPECIAL_TEXT_ATTRIBUTE");
		if (System.getenv("PAGE_HEADING_LEVEL") != null)
			pageHeadingLevel = System.getenv("PAGE_HEADING_LEVEL");
		if (System.getenv("TEXT_FIELD_TYPE") != null)
			textFieldType = System.getenv("TEXT_FIELD_TYPE");
		if (System.getenv("TEXT_AREA_TYPE") != null)
			textAreaType = System.getenv("TEXT_AREA_TYPE");
		if (System.getenv("CHECKBOX_TYPE") != null)
			checkboxType = System.getenv("CHECKBOX_TYPE");
		if (System.getenv("FIELD_DEFAULT_FIELD_TYPE") != null)
			fieldDefaultType = System.getenv("FIELD_DEFAULT_FIELD_TYPE");
		if (System.getenv("TEXTAREA_DEFAULT_FIELD_TYPE") != null)
			textareaDefaultType = System.getenv("TEXTAREA_DEFAULT_FIELD_TYPE");
		if (System.getenv("INITIAL_AUT_LOAD_TIME_MS") != null)
			initialAutLoadTimeMs = Integer.valueOf(System.getenv("INITIAL_AUT_LOAD_TIME_MS"));

		elementDefinitions = ProcessFiles.processCsv(System.getenv("ELEMENT_DEFINITIONS"));
	}

	@BeforeSuite
    public void initializeDriver(){
        //This removes an old jmeter results file if it exists
		//Map <String, String> temp = System.getenv(); //gets all environmental properties

        try{
            File f = new File(System.getProperty("user.dir") + "/jmeter/results");
            if(f.isDirectory())
                FileUtils.cleanDirectory(f);
        } catch (Exception e){}

        setSystemVars();

        //query and setup csv files from a database based on the db.properties properties
	    int counter = 1;
	    while (System.getenv("QUERY_" + counter) != null){
	    	String query = System.getenv("QUERY_" + counter);
	    	String fileName = System.getenv("FILENAME_" + counter);
	    	//optional properties
	    	String username = System.getenv("DB_USERNAME_" + counter);
		    String password = System.getenv("DB_PASSWORD_" + counter);
		    String url = System.getenv("URL_" + counter);
		    String dbType = System.getenv("DB_TYPE_" + counter);

		    if(username == null)
		    	username = System.getenv("DB_USERNAME");
		    if(password == null)
		    	password = System.getenv("DB_PASSWORD");
		    if(url == null)
		    	url = System.getenv("DB_URL");
		    if(dbType == null)
				dbType = System.getenv("DB_TYPE");



		    if(fileName == null || username == null || password == null || url == null || dbType == null) {
		    	//message will be only printed in the console as it is not in a step, but it can be found as "checkHAH " is added in front of the message
			    Gauge.writeMessage("One of the required values to query for test data was found to be null. Please review " +
					    "the values entered for QUERY_" + counter);
			    counter++;
			    continue;
		    }

		    DatabaseConnection dbConnection = new DatabaseConnection(url, username, password, dbType);
		    dbConnection.querySelect(query);
		    BoltLibrary.writeMapToFile(dbConnection.getQueryResponse(), fileName);
		    dbConnection.closeConnection();

		    counter++;
	    }
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext context){
        spec = context.getCurrentSpecification().getName().toUpperCase();

        //Identifies tests as positive or negative
        if(!spec.toUpperCase().contains("JMETER")){
            webDriver = DriverFactory.getDriver();
            //TODO the next 2 lines should only run if running locally
	        if(!DriverFactory.remoteRun) {
		        webDriver.manage().window().maximize();
		        webDriver.switchTo().window(webDriver.getWindowHandle());
	        }
            remoteRun = DriverFactory.getRemoteRun();
            remoteUrl = DriverFactory.getRemoteUrl();
        } else{
            if(System.getenv("REMOTE").equalsIgnoreCase("TRUE"))
                remoteRun = true;
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

    @AfterScenario
    public void afterScenario(ExecutionContext context){

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
            File f = new File(System.getProperty("user.dir") + "/moveLogo.sh");
            //check if f exists, if not handle it differently
            PrintWriter writer = new PrintWriter(f);
            writer.print("cp " + System.getProperty("user.dir") + "/logo_org.png " + System.getProperty("user.dir") + "/reports/html-report/images/logo.png");
            writer.close();
            //if(f.isDirectory())
            //    f.delete();
            /*
            remove contents of the file
            add "cp " + full path to logo_org.png + " " + full path to logo.png file
             */
        } catch (Exception e){

        }
        /*try {
            FileUtils.deleteQuietly(new File("reports/html-report/images/logo.png"));
            FileUtils.copyFile(new File("reports/html-report/images/swatLogo.png"), new File("reports/html-report/images/logo.png"));
            System.out.println("test");
        }catch (Exception e){

        } finally {
            try {
                File f = new File(System.getProperty("user.dir") + "/moveLogo.sh");
                //check if f exists, if not handle it differently
                PrintWriter writer = new PrintWriter(f);
                //if(f.isDirectory())
                //    f.delete();
            /*
            remove contents of the file
            add "cp " + full path to logo_org.png + " " + full path to logo.png file

            } catch (Exception e){

            }
        }
*/
    }

    public static WebDriver getWebDriver(){
        return webDriver;
    }
}
