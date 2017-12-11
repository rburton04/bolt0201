package CustomSteps;

import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import utils.jMeter;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class CustomClientSteps extends CustomActions {





    @Step("Go to swat solutions website")
    public void launchSwatApplication() { goToSite(SWAT_URL); }
    @Step("Go to bbc website")
    public void launchBBCApplication() { goToSite(BBC_URL); }

    @Step("Click Search button")
    public void clickSearchButton (){
        click(spec + ":searchButton");
    }

    @ContinueOnFailure
    @Step ("Verify <value> results")
    public void verifyResults (int value) {
        validateResults(value);
    }

    @ContinueOnFailure
    @Step("Verify value <value> doesn't exist in dropdown <index>")
    public void verifyValueDoesntExist(String value, int index){
        assertFalse(checkOptionExistsInDropdown(spec + ":dropdown", index - 1, value));
    }

    @Step ("Run JMeter Script <testName> <searchOption>")
    public void jMeterScript(String testName, String searchOption){
        try {
            jMeter jmeter = new jMeter();
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("searchoption", searchOption);
            jmeter.runJMeterTest(testName, vars, remoteRun, remoteUrl, scenario);
        } catch (Exception e){
            System.out.println("error");
        }
    }
    @Step ("Run JMeter Script <testName> <searchOption> <threadCount>")
    public void jMeterScript(String testName, String searchOption, String threadCount){
        try {
            jMeter jmeter = new jMeter();
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("searchoption", searchOption);
            vars.put("threadCount", threadCount);
            jmeter.runJMeterTest(testName, vars, remoteRun, remoteUrl, scenario);
        } catch (Exception e){
            System.out.println("error");
        }
    }
    @Step ("Run JMeter Script <testName> <searchOption> <threadCount> <rampUpPeriod>")
    public void jMeterScript(String testName, String searchOption, String threadCount, String rampUpPeriod){
        try {
            jMeter jmeter = new jMeter();
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("searchoption", searchOption);
            vars.put("threadCount", threadCount);
            vars.put("rampUpPeriod", rampUpPeriod);
            jmeter.runJMeterTest(testName, vars, remoteRun, remoteUrl, scenario);
        } catch (Exception e){
            System.out.println("error");
        }
    }
    @Step ("Run JMeter Script <testName> <searchOption> <threadCount> <rampUpPeriod> <loopCount>")
    public void jMeterScript(String testName, String searchOption, String threadCount, String rampUpPeriod, String loopCount){
        try {
            jMeter jmeter = new jMeter();
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("searchoption", searchOption);
            vars.put("threadCount", threadCount);
            vars.put("rampUpPeriod", rampUpPeriod);
            vars.put("loopCount", loopCount);
            jmeter.runJMeterTest(testName, vars, remoteRun, remoteUrl, scenario);
        } catch (Exception e){
            System.out.println("error");
        }
    }


    @Step ("Run JMeter Conference Script <index> <name> <feedback> <threadCount> <rampUpPeriod> <loopCount>")
    public void jmeterConferenceDemo (String index, String name, String feedback, String threadCount, String rampUpPeriod, String loopCount){
        try {
            jMeter jmeter = new jMeter();
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("index", index);
            vars.put("name", name);
            vars.put("feedback", feedback);
            vars.put("ip", ip);
            vars.put("port", port);
            vars.put("threadCount", threadCount);
            vars.put("rampUpPeriod", rampUpPeriod);
            vars.put("loopCount", loopCount);
            jmeter.runJMeterTest("jmeter/jmeter-confapp.jmx", vars, remoteRun, remoteUrl, scenario);
            if(!jmeter.getSuccess())
                fail("Failure in the JMeter test run. Please investigate");
        } catch (Exception e){
            System.out.println("error");
        }
    }
}