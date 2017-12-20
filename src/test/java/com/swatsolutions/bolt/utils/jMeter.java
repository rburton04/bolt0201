package com.swatsolutions.bolt.utils;
import com.googlecode.jmeter.plugins.webdriver.config.RemoteDriverConfig;
import org.apache.jmeter.engine.DistributedRunner;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import com.googlecode.jmeter.plugins.webdriver.config.RemoteCapability;

import java.io.*;
import java.util.*;

public class jMeter{

	private boolean success = false;

	//TODO Setup the nice report for the results

    /**
     * @param testName name of the current test
     * @param options map of the options/paramaters to pass into JMeter
     * @param remoteRun true to run JMeter test against a remote node
     * @param remoteUrl the url of the selenium hub for remote testing (currently not in use)
     * @param scenario name of the current scenario
     * @throws Exception
     */
    public void runJMeterTest(String testName, Map<String, String> options, boolean remoteRun, String remoteUrl, String scenario) throws Exception {
        runJMeterTest(testName, options, remoteRun,remoteUrl,scenario,1);
    }

    /**
     * @param testName name of the current test
     * @param options map of the options/paramaters to pass into JMeter
     * @param remoteRun true to run JMeter test against a remote node
     * @param remoteUrl the url of the selenium hub for remote testing (currently not in use)
     * @param scenario name of the current scenario
     * @param remoteServers number of remote servers to use for JMeter Distributed Testing if running remotely
     * @throws Exception
     */
	public void runJMeterTest(String testName, Map<String, String> options, boolean remoteRun, String remoteUrl, String scenario, int remoteServers) throws Exception {

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
			//TODO is it possible to add this at the start?
			int length = testPlanTree.size();
			testPlanTree.add(jmeterVars);
		}

		if(false) {
			try {
				//ChromeDriverConfig cdriverTest = new ChromeDriverConfig();
				RemoteDriverConfig seleniumHubConfig = new RemoteDriverConfig();

				//for this use RemoteDeisredCapabilitiesFactory
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
			} catch (Exception e){
				System.out.println("");
			}
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

		if(remoteRun){

		    //TODO may need to look into the LoggerFactory
            //create a log4j2 file??


			List<JMeterEngine> engines = new LinkedList<>();
			Properties remoteProps = new Properties();
			//set properties to send to remote clients
			DistributedRunner distributedRunner = new DistributedRunner(remoteProps);

			List<String> hosts = new LinkedList<>();

			//add your JMeter slaves here
            //TODO determine a way to identify the number of remote nodes to utilize
            for(int index = 0; index < remoteServers; index++) {
                String host = System.getenv("REMOTE_HOST_" + String.valueOf(index + 1));
                if(host == null)
                    break;

                hosts.add(host);
            }

			distributedRunner.setStdout(System.out);
			distributedRunner.setStdErr(System.err);
			distributedRunner.init(hosts, testPlanTree);
			engines.addAll(distributedRunner.getEngines());
			distributedRunner.start();
			jmeter.run();
			//a delay just for good measure
            library.hardDelay(5000);
			distributedRunner.stop();
		} else
			jmeter.run();

		jmeter.exit();

		Scanner scan = new Scanner(logFile);
		success = true;
		while (scan.hasNextLine()){
			if(scan.nextLine().contains("s=\"false\"")){
				success = false;
				break;
			}
		}
		scan.close();
	}

	public boolean getSuccess(){
		return success;
	}
}