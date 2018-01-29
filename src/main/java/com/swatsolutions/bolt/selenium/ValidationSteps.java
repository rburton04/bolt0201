package com.swatsolutions.bolt.selenium;

import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;

public class ValidationSteps {
	private SeleniumSmartActions smartActions = new SeleniumSmartActions();
	//TODO positive validation

	//check text matches expected
	@ContinueOnFailure
	@Step("Verify text in textbox labeled <label> matches <text>")
	public void verifyText(String label, String text){
		Assert.assertTrue("Textbox text did not match the expected value", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textFieldType)));
	}
	@ContinueOnFailure
	@Step("Verify text in textbox labeled <label> index <index> matches <text>")
	public void verifyText(String label, int index, String text){
		Assert.assertTrue("Textbox text did not match the expected value", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textFieldType, index)));
	}
	@ContinueOnFailure
	@Step("Verify text in textarea labeled <label> matches <text>")
	public void verifyTextArea(String label, String text){
		Assert.assertTrue("Textarea text did not match the expected value", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textAreaType)));
	}
	@ContinueOnFailure
	@Step("Verify text in textarea labeled <label> index <index> matches <text>")
	public void verifyTextArea(String label, int index, String text){
		Assert.assertTrue("Textarea text did not match the expected value", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textAreaType, index)));
	}

	//check page title
	@ContinueOnFailure
	@Step("Verify page title matches <title>")
	public void verifyTitle(String title){
		//TODO decrease wait time for the page title??
		String pageTitle = "";
		if(!BoltDriver.pageHeadingLevel.isEmpty())
			pageTitle = smartActions.getText(BoltDriver.pageHeadingLevel, "TAG");

		int index = 1;
		while(pageTitle.isEmpty()){
			if(index > 6)
				break;
			pageTitle = smartActions.getText("h" + index, "TAG");
			index ++;
		}

		Assert.assertTrue("Page title failed to match. Found: " + pageTitle, title.equalsIgnoreCase(pageTitle));
	}

	//Check page changed
	@ContinueOnFailure
	@Step("Verify page changed")
	public void verifyPageChange(){
		smartActions.compareUrl(BoltDriver.storedUrl, false);
	}

	//Check the current page is as expected
	@ContinueOnFailure
	@Step("Verify page url matches <url>")
	public void verifyUrl(String url){
		smartActions.compareUrl(url, true);
	}

	//TODO negative validation

	//Check for error pop-up with given text
	@ContinueOnFailure
	@Step("Verify popup with text <text> is displayed")
	public void verifyPopupText(String text){
//possibly use the "checkIfTextExists" method
		smartActions.checkIfTextExists(text, 0);
	}

	//Check text does not match what was entered
	@ContinueOnFailure
	@Step("Verify text in textbox labeled <label> does not match <text>")
	public void verifyTextMismatch(String label, String text){
		Assert.assertFalse("Textbox text matched when it should not have", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textFieldType)));
	}
	@ContinueOnFailure
	@Step("Verify text in textbox labeled <label> index <index> does not match <text>")
	public void verifyTextMismatch(String label, int index, String text){
		Assert.assertFalse("Textbox text matched when it should not have", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textFieldType, index)));
	}
	@ContinueOnFailure
	@Step("Verify text in textarea labeled <label> does not match <text>")
	public void verifyTextAreaMismatch(String label, String text){
		Assert.assertFalse("Textarea text matched when it should not have", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textAreaType)));
	}
	@ContinueOnFailure
	@Step("Verify text in textarea labeled <label> index <index> does not match <text>")
	public void verifyTextAreaMismatch(String label, int index, String text){
		Assert.assertFalse("Textarea text matched when it should not have", text.equalsIgnoreCase(smartActions.getTextByLabel(label, BoltDriver.textAreaType, index)));
	}

	//Check error message text exists
	@ContinueOnFailure
	@Step("Verify error message <message> exists")
	public void verifyErrorMessageDisplayed(String message){
		smartActions.checkIfTextExists(message, 0);
	}
	@ContinueOnFailure
	@Step("Verify error message <message> index <index> exists")
	public void verifyErrorMessageDisplayed(String message, int index){
		smartActions.checkIfTextExists(message, index);
	}
	@ContinueOnFailure
	@Step("Save current Url")
	public void storeCurrUrl(){
		BoltDriver.storedUrl = smartActions.getCurrentUrl();
	}

	//Check page did not change upon button click
	@ContinueOnFailure
	@Step("Verify page did not change")
	public void verifyPageNoChange(){
		smartActions.compareUrl(BoltDriver.storedUrl, true);
	}

	//Verify a button does not exist
	@ContinueOnFailure
	@Step("Verify button <button> does not exist")
	public void verifyButtonDoesntExist(String button){
		smartActions.verifyButtonStatusDynamically(SeleniumSmartActions.verifyButtonStatus.NONEXISTANT, button);
	}
	@ContinueOnFailure
	@Step("Verify button labeled <label> does not exist")
	public void verifyButtonDoesntExistLabel(String label){
		smartActions.verifyButtonStatusByLabel(SeleniumSmartActions.verifyButtonStatus.NONEXISTANT, label, 0);
	}
	@ContinueOnFailure
	@Step("Verify button with text <text> does not exist")
	public void verifyButtonDoesntExistText(String text){
		smartActions.verifyButtonStatusByText(SeleniumSmartActions.verifyButtonStatus.NONEXISTANT, text, 0);
	}
	@ContinueOnFailure
	@Step("Verify button with text <text> and index <index> does not exist")
	public void verifyButtonDoesntExistText(String text, int index){
		smartActions.verifyButtonStatusByText(SeleniumSmartActions.verifyButtonStatus.NONEXISTANT, text, index);
	}

	@ContinueOnFailure
	@Step("Verify text <text> exists")
	public void verifyTextExists(String text){
		smartActions.checkIfTextExists(text, 0);
	}

	//Verify text does not exist
	@ContinueOnFailure
	@Step("Verify text <text> does not exist")
	public void verifyTextDoesntExist(String text){
		smartActions.checkIfTextDoesNotExist(text, 0);
	}

	//Verify disabled button
	@ContinueOnFailure
	@Step("Verify button <button> is disabled")
	public void verifyButtonDisabled(String button){
		smartActions.verifyButtonStatusDynamically(SeleniumSmartActions.verifyButtonStatus.DISABLED, button);
	}
	@ContinueOnFailure
	@Step("Verify button labeled <label> is disabled")
	public void verifyButtonDisabledLabel(String label){
		smartActions.verifyButtonStatusByLabel(SeleniumSmartActions.verifyButtonStatus.DISABLED, label, 0);
	}
	@ContinueOnFailure
	@Step("Verify button with text <text> is disabled")
	public void verifyButtonDisabledText(String text){
		smartActions.verifyButtonStatusByText(SeleniumSmartActions.verifyButtonStatus.DISABLED, text, 0);
	}
	@ContinueOnFailure
	@Step("Verify button with text <text> and index <index> is disabled")
	public void verifyButtonDisabledText(String text, int index){
		smartActions.verifyButtonStatusByText(SeleniumSmartActions.verifyButtonStatus.DISABLED, text, index);
	}

	//Verify <good or bad> symbol is displayed
	@ContinueOnFailure
	@Step("Verify <symbol> symbol is displayed")
	public void verifySymbolDisplayed(String symbol){
		smartActions.checkElementExists("SYMBOL:" + symbol, true, 0);
	}
	@ContinueOnFailure
	@Step("Verify <symbol> symbol is not displayed")
	public void verifySymbolNotDisplayed(String symbol){
		smartActions.checkElementExists("SYMBOL:" + symbol, false, 0);
	}
}