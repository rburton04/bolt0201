package com.swatsolutions.bolt.selenium;

import com.thoughtworks.gauge.Step;
import org.apache.commons.lang.RandomStringUtils;

public class DynamicSteps extends GeneralSteps {
	//TODO build in steps that enter random strings into fields, etc.
	//TODO build option for a pre-built list to used instead of random values
	//TODO ability to enter a large number of different strings into the same field in one step

	//TODO enhance these steps to use nums, alphabetic, or alphanumeric options
	@Step("Enter <num> chars into the <label> field")
	public void enterManyCharsField(int num, String label){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
	}

	@Step("Enter <num> chars into the <label> textarea")
	public void enterManyCharsTextArea(int num, String label){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
	}

	//TODO step that takes in a table?
}
