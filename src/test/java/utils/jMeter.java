package utils;
import com.googlecode.jmeter.plugins.webdriver.config.RemoteDriverConfig;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import com.googlecode.jmeter.plugins.webdriver.config.RemoteCapability;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class jMeter {

    private boolean success = false;

    //TODO Setup the nice report for the results

    public void runJMeterTest(String testName, Map<String, String> options, boolean remoteRun, String remoteUrl, String scenario) throws Exception {

        // JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        JMeterUtils.setJMeterHome(System.getProperty("user.dir") + "/jmeter/");
        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties(System.getProperty("user.dir") + "/jmeter/bin/jmeter.properties");

        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        String location = System.getProperty("user.dir") + "/" + testName;
        HashTree testPlanTree = SaveService.loadTree(new File(System.getProperty("user.dir") + "/" + testName));

        //Pass variables into JMeter script
        if(!options.isEmpty()) {
            Arguments jmeterVars = new Arguments();
            for (Map.Entry<String, String> property : options.entrySet()) {
                jmeterVars.addArgument("bolt." + property.getKey(), property.getValue());
            }
            testPlanTree.add(jmeterVars);
        }

        if(remoteRun) {
            RemoteDriverConfig seleniumHubConfig = new RemoteDriverConfig();
            switch (System.getenv("BROWSER").toUpperCase()) {
                default:
                case "SAFARI":
                case "CHROME":
                    seleniumHubConfig.setCapability(RemoteCapability.CHROME);
                    break;
                case "EDGE":
                case "IE":
                    seleniumHubConfig.setCapability(RemoteCapability.INTERNET_EXPLORER);
                    break;
                case "FIREFOX":
                    seleniumHubConfig.setCapability(RemoteCapability.FIREFOX);
                    break;
                case "HEADLESS":
                    seleniumHubConfig.setCapability(RemoteCapability.PHANTOMJS);
                    break;
            }
            //seleniumHubConfig.setCapability(RemoteCapability.CHROME);
            seleniumHubConfig.setSeleniumGridUrl(remoteUrl);

            testPlanTree.add("remoteDriverConfig", seleniumHubConfig);
        }

        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");//$NON-NLS-1$
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        //Adjust file naming as needed
        String logFile;
        if(!options.isEmpty()) {
            if(options.containsKey("index"))
                logFile = System.getProperty("user.dir") + "/jmeter/results/results_" + scenario + "_" + options.get("index") + ".xml";
            else
                logFile = System.getProperty("user.dir") + "/jmeter/results/results_" + scenario + "_" + options.get(options.entrySet().iterator().next().getKey()) + ".xml";
        }else
            logFile = System.getProperty("user.dir") + "/jmeter/results/results_" + scenario + ".xml";

        System.out.println("Log File: " + logFile);
        ResultCollector logger = new ResultCollector(summer);
        logger.setFilename(logFile);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();

        jmeter.exit();

        Scanner scan = new Scanner(logFile);
        success = true;
        while (scan.hasNextLine()){
            if(scan.nextLine().contains("s=\"false\"")){
                success = false;
            }
        }
        scan.close();
    }

    public boolean getSuccess(){
        return success;
    }
}