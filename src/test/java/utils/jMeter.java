package utils;
import org.apache.commons.io.FileUtils;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.*;
import java.util.Map;

public class jMeter {
    /*
    modify jmeter.properties file to include:
jmeter.save.saveservice.output_format=xml
jmeter.save.saveservice.response_data=true
jmeter.save.saveservice.samplerData=true
jmeter.save.saveservice.requestHeaders=true
jmeter.save.saveservice.url=true
jmeter.save.saveservice.responseHeaders=true


     */

    //TODO Setup passing variables along
    //TODO Setup the nice report for the results

    public void runJMeterTest(String testName, Map<String, String> options) throws Exception {

        String jmeterHome = System.getenv("JMETER_HOME");
        /*needed variables:
        path to jmeter home folder stored
        name of test to run (tests stored in a folder next to specs)
        Map of variables to add
        */

        //modifyUserPropertiesFile(options, jmeterHome + "/libexec/bin/");

        // JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        JMeterUtils.setJMeterHome(jmeterHome);
        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties(jmeterHome + "/bin/jmeter.properties");
        // The user.properities file can be modified to include other needed variables unsure if the following line needs to be used or not.
        //JMeterUtils.setProperty("test", "test22");
        //JMeterUtils.loadProperties("/path/to/your/jmeter/bin/user.properties");
        // This should be a stored variable in the properties file

        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        String location = System.getProperty("user.dir") + "/" + testName;
        //FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "/" + testName);
        HashTree testPlanTree = SaveService.loadTree(new File(System.getProperty("user.dir") + "/" + testName));
        //in.close();

        Summariser summer = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");//$NON-NLS-1$
        if (summariserName.length() > 0) {
            summer = new Summariser(summariserName);
        }

        String logFile = System.getProperty("user.dir") + "/jmeter/results.xml";
        ResultCollector logger = new ResultCollector(summer);
        logger.setFilename(logFile);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }

    private static void modifyUserPropertiesFile(Map<String, String> properties, String folderPath){
        /*
        Use a marker to identify proporty changes done with code so they can all be removed
        "---- BOLT PROPERTIES ----"

System.getProperty("user.dir") + "/jmeter")
       */

        try {
            File temp = new File(folderPath + "userSetup.txt");
            if (!temp.exists()) {
                FileUtils.copyFile(new File(folderPath + "user.properties"), new File(folderPath + "userSetup.txt"));
            }

        } catch(Exception e){
            System.out.println("error");
        }

        BufferedReader br = null;
        BufferedWriter bw = null;
        try{
            br = new BufferedReader(new FileReader(folderPath + "userSetup.txt"));//path to static properties file
            bw = new BufferedWriter(new FileWriter(folderPath + "user.properties"));
            String currLine = "";
            while((currLine = br.readLine()) != null){
                bw.write(currLine + "\n");
            }
            bw.newLine();
            bw.write("#---- BOLT PROPERTIES ----\n");
            bw.newLine();
            for(Map.Entry<String, String> property : properties.entrySet()){
                bw.write(property.getKey() + "=" + property.getValue());
            }


        } catch (Exception e){

        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) { }
            try {
                if(bw != null)
                    bw.close();
            } catch (IOException e) { }
        }

    }
}