package com.swatsolutions.bolt.selenium;

import com.swatsolutions.bolt.utils.BoltLibrary;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GeneralSteps {
	private String spec = BoltDriver.spec;
	private SeleniumSmartActions smartActions = new SeleniumSmartActions();

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

	//ASSORTED STEPS
	@Step("Delay <ms> milliseconds")
	public void delayMilliseconds(int ms){
		BoltLibrary.hardDelay(ms);
	}

	//CLICKING STEPS

	@Step("Click element with attribute <att> and value <val>")
	public void clickElementWithAttribute(String att, String val){
		smartActions.clickByAttributeAndValue(att, val);
	}

	@Step("Click defined symbol <symbol>")
	public void clickDefinedSymbol(String symbol){
		smartActions.click("SYMBOL:" + symbol);
	}

	@Step("Click <button> button by element definition")
	public void clickButtonElementDefinition(String button){
		smartActions.clickByText(spec + ":button", button);
	}

	@Step("Click on the <index> <button> button by element definition")
	public void clickButtonElementDefinition(int index, String button){
		//the -1 is used to move the index to a 0-based indexing as compared to a 1 based
		smartActions.clickByTextAndIndex(spec+":button", button, index - 1);
	}

	//HOVERING STEPS

	@Step("Hover over <tab> tab and select <option>")
	public void hoverAndClick(String tab, String option){
		hover(tab);
		navigateToTab(option);
	}

	@Step("Hover over <tab> tab")
	public void hover (String tab){
		smartActions.hoverOverElement(tab);
	}

	@Step("Hover over tab with partial text <tab>")
	public void hoverPartialText (String tab){
		smartActions.hoverOverElementPartialText(tab);
	}

	//VERIFICATION STEPS
	@ContinueOnFailure
	@Step("Verify current page is <url>")
	public void verifyPage(String url){
		smartActions.verifyCurrentPage(url);
	}

	@ContinueOnFailure
	@Step("Verify <option> is an option in dropdown")
	public void verifyOptionInDropdown(String option){
		smartActions.checkOptionExistsInDropdown(spec + ":dropdown", option);
	}

	//TODO verify <option> is an active option in the dropdown
	//TODO verify that <option> is an inactive option in the dropdown

	//URL STEPS
	@Step("Go to AUT")
	public void navigateToAUT(){
		smartActions.goToSite(BoltDriver.aut);
		int temp = BoltDriver.initialAutLoadTimeMs;
		delayMilliseconds(BoltDriver.initialAutLoadTimeMs);
	}

	@Step({"Navigate to <tab> tab", "Navigate to <tab>", "Click on <text> text"})
	public void navigateToTab(String tab) {
		smartActions.clickByLinkedText(tab);
	}

	@Step("Navigate to tab with partial text <tab>")
	public void navigateToTabPartialText(String tab) {
		smartActions.clickByLinkedTextPartialText(tab);
	}

	@Step({"Go to <website> website", "Go to <website>"})
	public void navToWebsite(String website){
		smartActions.goToSite(website);
	}

	//DROPDOWN STEPS

	//TODO select the value by index from a dropdown with label

	@Step("Select <value> from dropdown with <defaultVal> default value")
	public void selectDefaultDropdownValue(String value, String defaultVal){
		smartActions.selectValueFromDropdownWithDefaultValue(defaultVal, value);
	}

	@Step("Select <value> from the <label> dropdown")
	public void selectDropdownLabel(String value, String label){
		smartActions.selectValueFromDropdownByLabel(label, value);
	}

	@Step("Select <dropdown> from dropdown")
	public void selectDropdown (String dropdown){
		smartActions.selectDropdown(spec + ":dropdown", dropdown);
	}

	@Step("Select <dropdown1>, <dropdown2> from dropdown")
	public void multiDropdowns(String dw1, String dw2){
		List<String> selections = new LinkedList<String>();
		selections.add(dw1);
		selections.add(dw2);
		smartActions.multiSelectDropdown(spec + ":dropdown",selections);
	}

	@Step("Select value <value> on dropdown <index>")
	public void selectDropdown (String value, int index) {
		//-1 is used as people don't count with 0-based indexing
		smartActions.selectDropdownByIndex(spec + ":dropdown", value, index - 1);
	}

	@Step("Select the <optionIndex> option from dropdown")
	public void selectDropdown(int optionIndex){
		smartActions.selectDropdownOptionByIndex(spec + ":dropdown", optionIndex-1);
	}

	@Step("Select the <optionIndex> option from dropdown <index>")
	public void selectDropdown(int optionIndex, int index){
		smartActions.selectDropdownAndOptionByIndex(spec + ":dropdown", index, optionIndex-1);
	}

	@Step("Select a random value from the dropdown")
	public void selectDropdownRandom(){
		smartActions.selectDropdownOptionRandom(spec + ":dropdown");
	}

	@Step("Select a random value from the <index> dropdown")
	public void selectDropdownRandom(int index){
		smartActions.selectDropdownOptionRandom(spec + ":dropdown", index);
	}

	//ENTER TEXT
	//TODO some sites have labels that are not marked as labels...

	@Step("Enter <num> chars in field <index>")
	public void enterManyCharsBasic(int num, int index){
		String generatedString = RandomStringUtils.randomAlphanumeric(num);
		enterText(generatedString, index);
	}

	@Step("Enter text <text> in field <index>")
	public void enterText (String text, int index) {
		//-1 is used as people don't count with 0-based indexing
		smartActions.enterTextByIndex(spec + ":textField", text, index - 1);
	}

	//TODO add steps to enter text into textarea with default values

	//TODO add step(s) to add text instead of replacing all of the text in the box

	//PRESS/ENTER SPECIAL KEYS

	@Step("Press <key> key")
	public void pressKey(String key){
		smartActions.keyPress(key);
	}

	@Step("Press many keys (seperate each with a comma) <keys>")
	public void pressManyKeys(String key){
		ArrayList<String> keys = new ArrayList<String>(Arrays.asList(key.split(",")));
		smartActions.multiKeyPress(keys);
	}

	//TABLES

	//TODO modify this step as this is only for testing
	@Step("Get data from table <table>")
	public void getTableData(String table){
		smartActions.readTableTo2DArray(table);
	}

	//CHECKBOX AND RADIO BUTTONS

	//TODO do something with this nasty looking step
	@Step("<check> checkbox")
	public void checkbox(String selection){
		if(selection.toUpperCase().equals("UNCHECK"))
			smartActions.setCheckboxToValue(spec + ":checkbox", false);
		else
			smartActions.setCheckboxToValue(spec + ":checkbox", true);
	}

	@Step("Click <text> checkbox")
	public void checkboxClick(String text){
		smartActions.smartClickCheckbox(text);
	}

	@Step({"Check the <text> checkbox","Select the <text> radio button"})
	public void checkboxSelection(String text){
		smartActions.smartSetCheckboxToValue(text, true);
	}

	@Step("Uncheck the <text> checkbox")
	public void uncheckboxSelection(String text){
		smartActions.smartSetCheckboxToValue(text, false);
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
			smartActions.scrollUp();
		else
			smartActions.scrollDown();
	}

	@Step("Scroll <direction> <amount>")
	public void scroll(String direction, int amount){
		if(direction.toUpperCase().equals("UP"))
			smartActions.scroll(-Math.abs(amount));
		else
			smartActions.scroll(Math.abs(amount));
	}
}
