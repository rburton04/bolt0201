package com.swatsolutions.bolt.selenium;

import com.thoughtworks.gauge.Step;
import org.junit.Assert;

public class ValidationSteps extends GeneralSteps {
	//TODO positive validation

	//check text matches expected
	@Step("Verify text in textbox labeled <label> matches <text>")
	protected void verifyText(String label, String text){
		Assert.assertTrue("Textbox text did not match the expected value", text.equalsIgnoreCase(getTextByLabel(label, "input")));
	}

	@Step("Verify text in textbox labeled <label> index <index> matches <text>")
	protected void verifyText(String label, int index, String text){
		Assert.assertTrue("Textbox text did not match the expected value", text.equalsIgnoreCase(getTextByLabel(label, "input", index)));
	}

	@Step("Verify text in textarea labeled <label> matches <text>")
	protected void verifyTextArea(String label, String text){
		Assert.assertTrue("Textarea text did not match the expected value", text.equalsIgnoreCase(getTextByLabel(label, "textarea")));
	}

	@Step("Verify text in textarea labeled <label> index <index> matches <text>")
	protected void verifyTextArea(String label, int index, String text){
		Assert.assertTrue("Textarea text did not match the expected value", text.equalsIgnoreCase(getTextByLabel(label, "textarea", index)));
	}

	//check page title
	@Step("Verify page title matches <title>")
	protected void verifyTitle(String title){
		Assert.assertTrue("Page title failed to match.", title.equalsIgnoreCase(getText("h1", "TAG")));
	}

	//Check page changed
	@Step("Verify page changed")
	protected void verifyPageChange(){
		compareUrl(storedUrl, false);
	}

	//Check the current page is as expected
	@Step("Verify page url matches <url>")
	protected void verifyUrl(String url){
		compareUrl(url, true);
	}

	//TODO negative validation

	//Check for error pop-up with given text
	@Step("Verify popup with text <text> is displayed")
	protected void verifyPopupText(String text){
//possibly use the "checkIfTextExists" method
		checkIfTextExists(text, 0);
	}

	//Check text does not match what was entered
	@Step("Verify text in textbox labeled <label> does not match <text>")
	protected void verifyTextMismatch(String label, String text){
		Assert.assertFalse("Textbox text matched when it should not have", text.equalsIgnoreCase(getTextByLabel(label, "input")));
	}

	@Step("Verify text in textbox labeled <label> index <index> does not match <text>")
	protected void verifyTextMismatch(String label, int index, String text){
		Assert.assertFalse("Textbox text matched when it should not have", text.equalsIgnoreCase(getTextByLabel(label, "input", index)));
	}

	@Step("Verify text in textarea labeled <label> does not match <text>")
	protected void verifyTextAreaMismatch(String label, String text){
		Assert.assertFalse("Textarea text matched when it should not have", text.equalsIgnoreCase(getTextByLabel(label, "textarea")));
	}

	@Step("Verify text in textarea labeled <label> index <index> does not match <text>")
	protected void verifyTextAreaMismatch(String label, int index, String text){
		Assert.assertFalse("Textarea text matched when it should not have", text.equalsIgnoreCase(getTextByLabel(label, "textarea", index)));
	}

	//Check error message text exists
	@Step("Verify error message <message> exists")
	protected void verifyErrorMessageDisplayed(String message){
		checkIfTextExists(message, 0);
	}

	@Step("Verify error message <message> index <index> exists")
	protected void verifyErrorMessageDisplayed(String message, int index){
		checkIfTextExists(message, index);
	}

	@Step("Save current Url")
	protected void storeCurrUrl(){
		storedUrl = getCurrentUrl();
	}

	//Check page did not change upon button click
	@Step("Verify page did not change")
	protected void verifyPageNoChange(){
		compareUrl(storedUrl, true);
	}

	//Verify a button does not exist
	@Step("Verify button <button> does not exist")
	protected void verifyButtonDoesntExist(String button){
		verifyButtonStatusDynamically(verifyButtonStatus.NONEXISTANT, button);
	}

	@Step("Verify button labeled <label> does not exist")
	protected void verifyButtonDoesntExistLabel(String label){
		verifyButtonStatusByLabel(verifyButtonStatus.NONEXISTANT, label);
	}

	@Step("Verify button with text <text> does not exist")
	protected void verifyButtonDoesntExistText(String text){
		verifyButtonStatusByText(verifyButtonStatus.NONEXISTANT, text);
	}

	@Step("Verify button with text <text> and index <index> does not exist")
	protected void verifyButtonDoesntExistText(String text, int index){
		verifyButtonStatusByText(verifyButtonStatus.NONEXISTANT, text, index);
	}

	//Verify text does not exist
	@Step("Verify text <text> does not exist")
	protected void verifyTextDoesntExist(String text){
		checkIfTextDoesNotExist(text, 0);
	}

	//Verify disabled button
	@Step("Verify button <button> is disabled")
	protected void verifyButtonDisabled(String button){
		verifyButtonStatusDynamically(verifyButtonStatus.DISABLED, button);
	}

	@Step("Verify button labeled <label> is disabled")
	protected void verifyButtonDisabledLabel(String label){
		verifyButtonStatusByLabel(verifyButtonStatus.DISABLED, label);
	}

	@Step("Verify button with text <text> is disabled")
	protected void verifyButtonDisabledText(String text){
		verifyButtonStatusByText(verifyButtonStatus.DISABLED, text);
	}

	@Step("Verify button with text <text> and index <index> is disabled")
	protected void verifyButtonDisabledText(String text, int index){
		verifyButtonStatusByText(verifyButtonStatus.DISABLED, text, index);
	}

	//Verify <good or bad> symbol is displayed
	@Step("Verify <symbol> symbol is displayed")
	protected void verifySymbolDisplayed(String symbol){
		checkElementExists("SYMBOL:" + symbol, true, 0);
	}

	@Step("Verify <symbol> symbol is not displayed")
	protected void verifySymbolNotDisplayed(String symbol){
		checkElementExists("SYMBOL:" + symbol, false, 0);
	}
}