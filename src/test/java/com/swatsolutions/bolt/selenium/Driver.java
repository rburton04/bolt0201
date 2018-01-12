package com.swatsolutions.bolt.selenium;

import com.sun.jna.Library;
import com.swatsolutions.bolt.utils.databaseConnection;
import com.swatsolutions.bolt.utils.library;
import com.thoughtworks.gauge.*;

import com.swatsolutions.bolt.utils.fileReader;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.PrintWriter;
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
    protected static String ip = System.getenv("IP");
    protected static String port = System.getenv("PORT");
    protected static String aut = System.getenv("AUT_URL");
    protected static String spec = "";
    protected static String scenario = "";
    protected static String storedUrl = "";
    protected static int elementWaitTime = 5;

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

        if(!System.getenv("ELEMENT_WAIT_TIME").isEmpty()){
        	elementWaitTime = Integer.valueOf(System.getenv("ELEMENT_WAIT_TIME"));
        }

        elementDefinitions = fileReader.processCsv(System.getenv("ELEMENT_DEFINITIONS"));

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

		    databaseConnection dbConnection = new databaseConnection(url, username, password, dbType);
		    dbConnection.querySelect(query);
		    library.writeMapToFile(dbConnection.getQueryResponse(), fileName);
		    dbConnection.closeConnection();

		    counter++;
	    }
    }

    @BeforeSpec
    public void beforeSpec(ExecutionContext context){
        spec = context.getCurrentSpecification().getName().toUpperCase();

        //Identifies tests as positive or negative
        if(!spec.toUpperCase().contains("JMETER")){
            webDriver = getDriver();
            webDriver.manage().window().maximize();
            webDriver.switchTo().window(webDriver.getWindowHandle());
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
