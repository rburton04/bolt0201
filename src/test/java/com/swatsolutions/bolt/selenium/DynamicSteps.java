package com.swatsolutions.bolt.selenium;

import com.thoughtworks.gauge.Step;
import org.apache.commons.lang.RandomStringUtils;

public class DynamicSteps extends GeneralSteps {
	//TODO build in steps that enter random strings into fields, etc.
	//TODO build option for a pre-built list to used instead of random values
	//TODO ability to enter a large number of different strings into the same field in one step

	//Click Button
	@Step("Click button with label <label>")
	public void clickButtonByLabel(String label){
		clickDynamicallyByLabel(label, "button");
	}

	@Step({"Click button with text <text>","Click text <text>"})
	public void clickButtonText(String text){
		clickAnyText(text);
	}

	@Step("Click <button> button")
	public void clickButton(String button){
		clickDynamicallyByType(button, "button");
	}

	@Step({"Click button with text <text> and index <index>", "Click text <text> with index <index>"})
	public void clickButtonText(String text, int index){
		clickAnyText(text, index);
	}

	//Click

	@Step("Click <text>")
	public void clickItem(String text){
		clickDynamically(text);
	}

	@Step("Click the <index> <button> button")
	public void clickButton(int index, String button){
		//converting 1-based counting to 0-based
		clickDynamicallyByIndexAndType(button, "button", index-1);
	}

	//Enter Text

	//TODO enhance these steps to use nums, alphabetic, or alphanumeric options
	@Step("Enter <num> chars into the <label> field")
	public void enterManyCharsField(int num, String label){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
		enterTextViaLabelInput(generatedString, label);
	}

	@Step("Enter <num> chars into the <label> textarea")
	public void enterManyCharsTextArea(int num, String label){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
		enterTextViaLabelTextarea(generatedString, label);
	}

	@Step("Enter <num> chars in field <index>")
	public void enterManyCharsBasic(int num, int index){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
		enterText(generatedString, index);
	}

	@Step("Enter <num> chars in field with default value <value>")
	public void enterManyCharsDefaultVal(int num, String value){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
		enterTextViaDefaultVal(generatedString, value);
	}

	//TODO step that takes in a table?

	//Dropdowns

	@Step("Select a random value from the dropdown")
	public void selectDropdownRandom(){
		selectDropdownOptionRandom(spec + ":dropdown");
	}

	@Step("Select a random value from the <index> dropdown")
	public void selectDropdownRandom(int index){
		selectDropdownOptionRandom(spec + ":dropdown", index);
	}

	@Step("Select a random value from the dropdown labeled <label>")
	public void selectDropdownRandom(String label){
		selectRandomFromDropdownByLabel(label);
	}

	@Step("Select a random value from the dropdown with default value <value>")
	public void selectDropdownRandomDefault(String value){
		selectRandomFromDropdownByDefaultValue(value);
	}
}