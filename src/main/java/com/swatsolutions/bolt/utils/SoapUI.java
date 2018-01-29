package com.swatsolutions.bolt.utils;

import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

import java.util.List;
import java.util.Map;

public class SoapUI {

	public void testRunner(String file, Map<String, String> properties) {
		try{
			//https://stackoverflow.com/questions/22908086/how-to-run-soapui-tests-from-java-resolved


			//https://www.soapui.org/apidocs/com/eviware/soapui/tools/SoapUITestCaseRunner.html
			//https://www.soapui.org/apidocs/com/eviware/soapui/tools/AbstractSoapUIRunner.html
			SoapUITestCaseRunner runner = new SoapUITestCaseRunner();
			runner.setProjectFile(file);
			//the properties seems to be "key", "value" so a map could be used and then put into a string array
			if(properties != null){
				for (Map.Entry<String, String> entry : properties.entrySet()) {
					runner.setProjectProperties(new String[]{entry.getKey(), entry.getValue()});
				}
			}
			runner.run();
			//unsure if this will get the results, but it might allow a pass/fail to be determined
			List<TestCase> bob = runner.getFailedTests();
			String bob2 = runner.getAssertionResults().toString();
			System.out.println("hi");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
