package com.swatsolutions.bolt.selenium;

import com.thoughtworks.gauge.Step;
import org.apache.commons.lang.RandomStringUtils;

public class DynamicSteps {
	private SeleniumSmartActions smartActions = new SeleniumSmartActions();

	//TODO build in steps that enter random strings into fields, etc.
	//TODO build option for a pre-built list to used instead of random values
	//TODO ability to enter a large number of different strings into the same field in one step

	//Click Button
	@Step("Click button with label <label>")
	public void clickButtonByLabel(String label){
		smartActions.clickDynamicallyByLabel(label, "button");
	}

	@Step({"Click button with text <text>","Click text <text>"})
	public void clickButtonText(String text){
		smartActions.clickAnyText(text);
	}

	@Step("Click <button> button")
	public void clickButton(String button){
		smartActions.clickDynamicallyByType(button, "input");
	}

	@Step({"Click button with text <text> and index <index>", "Click text <text> with index <index>"})
	public void clickButtonText(String text, int index){
		smartActions.clickAnyText(text, index);
	}

	//Click

	@Step("Click <text>")
	public void clickItem(String text){
		smartActions.clickDynamically(text);
	}

	@Step("Click the <index> <button> button")
	public void clickButton(int index, String button){
		//converting 1-based counting to 0-based
		smartActions.clickDynamicallyByIndexAndType(button, "button", index-1);
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

	@Step("Enter <num> chars in field with default value <value>")
	public void enterManyCharsDefaultVal(int num, String value){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
		enterTextViaDefaultVal(generatedString, value);
	}

	@Step("Enter text <text> into input field labeled <label>")
	public void enterTextViaLabelInput(String text, String label){
		smartActions.enterTextByLabel(text, label, "input");
	}

	@Step("Enter text <text> into input field labeled <label> and index <index>")
	public void enterTextViaLabelInput(String text, String label, int index){
		smartActions.enterTextByLabelAndIndex(text, label, "input", index);
	}

	@Step("Enter text <text> into textarea labeled <label>")
	public void enterTextViaLabelTextarea(String text, String label){
		smartActions.enterTextByLabel(text, label, "textarea");
	}

	@Step("Enter text <text> into textarea labeled <label> and index <index>")
	public void enterTextViaLabelTextarea(String text, String label, int index){
		smartActions.enterTextByLabelAndIndex(text, label, "textarea", index);
	}

	@Step("Enter text <text> into field with default value <value>")
	public void enterTextViaDefaultVal(String text, String value){
		smartActions.enterTextByDefaultValues(text, value);
	}

	@Step("Enter text <text> into field with default value <value> and index <index>")
	public void enterTextViaDefaultVal(String text, String label, int index){
		smartActions.enterTextByDefaultValuesAndIndex(text, label, index);
	}

	//TODO step that takes in a table?

	//Dropdowns

	@Step("Select the <optionIndex> option from the <label> dropdown")
	public void selectDropdownLabel(int optionIndex, String label){
		smartActions.selectIndexFromDropdownByLabel(label, optionIndex-1);
	}

	@Step("Select the <optionIndex> option from dropdown with <defaultVal> default value")
	public void selectDefaultDropdownValue(int optionIndex, String defaultVal){
		smartActions.selectIndexFromDropdownWithDefaultValue(defaultVal, optionIndex-1);
	}

	@Step("Select a random value from the dropdown labeled <label>")
	public void selectDropdownRandom(String label){
		smartActions.selectRandomFromDropdownByLabel(label);
	}

	@Step("Select a random value from the dropdown with default value <value>")
	public void selectDropdownRandomDefault(String value){
		smartActions.selectRandomFromDropdownByDefaultValue(value);
	}
}
