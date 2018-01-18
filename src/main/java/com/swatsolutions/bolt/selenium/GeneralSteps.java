package com.swatsolutions.bolt.selenium;

import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GeneralSteps extends SeleniumSmartActions {

	//TODO start a video?
	//TODO check if a video is there
	//TODO check for ads on the page
		//look for id="google_image_div" and/or and img with src containing: "//www.google.com/ads/"
		//look for class="plistaList" or "plista_widget_sidebar_item"
	//TODO move mouse to an element before interacting with it
		//found that in some situations, it is impossible to get to options on a dropdown that has to be hovered over.
		//must do something so the mouse moves at normal speeds, not super speed.
		//find location of the hover and the location of where it is going, break it into small segments and then complete segments with a delay between each segment
		//Hover over <tab> and select option <option>
		//Hover over <tab> and select option <option> realistically


	//TODO add some steps that allow ways to get around potential issues, like "click element with attribute <att> equal to value <val>"


	//TODO build a step to handle multiple input boxes after a label
	// or multiple drop downs

	//CLICKING STEPS

	@Step("Click element with attribute <att> and value <val>")
	public void clickElementWithAttribute(String att, String val){
		clickByAttributeAndValue(att, val);
	}

	@Step("Click defined symbol <symbol>")
	public void clickDefinedSymbol(String symbol){
		click("SYMBOL:" + symbol);
	}

	@Step("Click <button> button by element definition")
	public void clickButtonElementDefinition(String button){
		clickByText(spec + ":button", button);
	}

	@Step("Click on the <index> <button> button by element definition")
	public void clickButtonElementDefinition(int index, String button){
		//the -1 is used to move the index to a 0-based indexing as compared to a 1 based
		clickByTextAndIndex(spec+":button", button, index - 1);
	}

	//HOVERING STEPS

	@Step("Hover over <tab> tab and select <option>")
	public void hoverAndClick(String tab, String option){
		hover(tab);
		navigateToTab(option);
	}

	@Step("Hover over <tab> tab")
	public void hover (String tab){
		hoverOverElement(tab);
	}

	@Step("Hover over tab with partial text <tab>")
	public void hoverPartialText (String tab){
		hoverOverElementPartialText(tab);
	}

	//VERIFICATION STEPS
	@ContinueOnFailure
	@Step("Verify current page is <url>")
	public void verifyPage(String url){
		verifyCurrentPage(url);
	}

	@ContinueOnFailure
	@Step("Verify <option> is an option in dropdown")
	public void verifyOptionInDropdown(String option){
		checkOptionExistsInDropdown(spec + ":dropdown", option);
	}

	//TODO verify <option> is an active option in the dropdown
	//TODO verify that <option> is an inactive option in the dropdown

	//URL STEPS
	@Step("Go to AUT")
	public void navigateToAUT(){
		goToSite(aut);
	}

	@Step({"Navigate to <tab> tab", "Navigate to <tab>", "Click on <text> text"})
	public void navigateToTab(String tab) {
		clickByLinkedText(tab);
	}

	@Step("Navigate to tab with partial text <tab>")
	public void navigateToTabPartialText(String tab) {
		clickByLinkedTextPartialText(tab);
	}

	@Step({"Go to <website> website", "Go to <website>"})
	public void navToWebsite(String website){
		goToSite(website);

	}

	//DROPDOWN STEPS

	//TODO select the value by index from a dropdown with label

	@Step("Select <value> from dropdown with <defaultVal> default value")
	public void selectDefaultDropdownValue(String value, String defaultVal){
		selectValueFromDropdownWithDefaultValue(defaultVal, value);
	}

	@Step("Select <value> from the <label> dropdown")
	public void selectDropdownLabel(String value, String label){
		selectValueFromDropdownByLabel(label, value);
	}

	@Step("Select <dropdown> from dropdown")
	public void selectDropdown (String dropdown){
		selectDropdown(spec + ":dropdown", dropdown);
	}

	@Step("Select <dropdown1>, <dropdown2> from dropdown")
	public void multiDropdowns(String dw1, String dw2){
		List<String> selections = new LinkedList<String>();
		selections.add(dw1);
		selections.add(dw2);
		multiSelectDropdown(spec + ":dropdown",selections);
	}

	@Step("Select value <value> on dropdown <index>")
	public void selectDropdown (String value, int index) {
		//-1 is used as people don't count with 0-based indexing
		selectDropdownByIndex(spec + ":dropdown", value, index - 1);
	}

	@Step("Select the <optionIndex> option from dropdown")
	public void selectDropdown(int optionIndex){
		selectDropdownOptionByIndex(spec + ":dropdown", optionIndex-1);
	}

	@Step("Select the <optionIndex> option from dropdown <index>")
	public void selectDropdown(int optionIndex, int index){
		selectDropdownAndOptionByIndex(spec + ":dropdown", index, optionIndex-1);
	}

	@Step("Select the <optionIndex> option from the <label> dropdown")
	public void selectDropdownLabel(int optionIndex, String label){
		selectIndexFromDropdownByLabel(label, optionIndex-1);
	}

	@Step("Select the <optionIndex> option from dropdown with <defaultVal> default value")
	public void selectDefaultDropdownValue(int optionIndex, String defaultVal){
		selectIndexFromDropdownWithDefaultValue(defaultVal, optionIndex-1);
	}

	//ENTER TEXT
	//TODO some sites have labels that are not marked as labels...

	@Step("Enter text <text> in field <index>")
	public void enterText (String text, int index) {
		//-1 is used as people don't count with 0-based indexing
		enterTextByIndex(spec + ":textField", text, index - 1);
	}

	@Step("Enter text <text> into input field labeled <label>")
	public void enterTextViaLabelInput(String text, String label){
		enterTextByLabel(text, label, "input");
	}

	@Step("Enter text <text> into input field labeled <label> and index <index>")
	public void enterTextViaLabelInput(String text, String label, int index){
		enterTextByLabelAndIndex(text, label, "input", index);
	}

	@Step("Enter text <text> into textarea labeled <label>")
	public void enterTextViaLabelTextarea(String text, String label){
		enterTextByLabel(text, label, "textarea");
	}

	@Step("Enter text <text> into textarea labeled <label> and index <index>")
	public void enterTextViaLabelTextarea(String text, String label, int index){
		enterTextByLabelAndIndex(text, label, "textarea", index);
	}

	@Step("Enter text <text> into field with default value <value>")
	public void enterTextViaDefaultVal(String text, String value){
		enterTextByDefaultValues(text, value);
	}

	@Step("Enter text <text> into field with default value <value> and index <index>")
	public void enterTextViaDefaultVal(String text, String label, int index){
		enterTextByDefaultValuesAndIndex(text, label, index);
	}

	//TODO add steps to enter text into textarea with default values

	//TODO add step(s) to add text instead of replacing all of the text in the box

	//PRESS/ENTER SPECIAL KEYS

	@Step("Press <key> key")
	public void pressKey(String key){
		keyPress(key);
	}

	@Step("Press many keys (seperate each with a comma) <keys>")
	public void pressManyKeys(String key){
		ArrayList<String> keys = new ArrayList<String>(Arrays.asList(key.split(",")));
		multiKeyPress(keys);
	}

	//TABLES

	//TODO modify this step as this is only for testing
	@Step("Get data from table <table>")
	public void getTableData(String table){
		readTableTo2DArray(table);
	}

	//CHECKBOX AND RADIO BUTTONS

	//TODO do something with this nasty looking step
	@Step("<check> checkbox")
	public void checkbox(String selection){
		if(selection.toUpperCase().equals("UNCHECK"))
			setCheckboxToValue(spec + ":checkbox", false);
		else
			setCheckboxToValue(spec + ":checkbox", true);
	}

	@Step("Click <text> checkbox")
	public void checkboxClick(String text){
		smartClickCheckbox(text);
	}

	@Step({"Check the <text> checkbox","Select the <text> radio button"})
	public void checkboxSelection(String text){
		smartSetCheckboxToValue(text, true);
	}

	@Step("Uncheck the <text> checkbox")
	public void uncheckboxSelection(String text){
		smartSetCheckboxToValue(text, false);
	}

//TODO radial buttons
	//possibly about the same as checkboxes
	//input 'type', actual type is radio
	//value may be equal to the label
//TODO select <num> radio with heading <heading>
//TODO select radio button labeled <label>

	//SCROLL

	@Step("Scroll <direction>")
	public void scroll(String direction){
		if(direction.toUpperCase().equals("UP"))
			scrollUp();
		else
			scrollDown();
	}
}
