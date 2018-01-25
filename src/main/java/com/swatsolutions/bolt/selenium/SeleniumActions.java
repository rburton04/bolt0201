package com.swatsolutions.bolt.selenium;

import com.swatsolutions.bolt.utils.BoltLibrary;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class SeleniumActions {
	protected WebDriver webDriver = null;
	protected int elementWaitTime = 0;
	protected WebElement lastElement = null;
	protected List<WebElement> lastElements = null;
	protected Map<String, String> elementDefinitions = new HashMap<String, String>();
	protected boolean positiveTest = true;
	/*
	elementWaitTime
	lastElement
	lastElements
	elementDefinitions
	positiveTest

	send in the driver object instead of the webdriver object, and all values can be set

	 */

	protected enum relativeTypes {PARENT, AUNT, CHILD, SIBLING};
	protected enum elementTypes {CLASS, NAME, ID, XPATH, LINKTEXT, PARTIAL_LINKTEXT, TAG};

	public SeleniumActions(){
		webDriver = BoltDriver.getWebDriver();
		elementWaitTime = BoltDriver.elementWaitTime;
		lastElement = BoltDriver.lastElement;
		lastElements = BoltDriver.lastElements;
		elementDefinitions = BoltDriver.elementDefinitions;
		positiveTest = BoltDriver.positiveTest;
	}

	/**
	 * @param elementDef
	 * @param expectedResult
	 * @param index
	 */
	protected void checkElementExists (String elementDef, boolean expectedResult, int index){
		try {
			boolean passed = false;
			int tempWaitTime = elementWaitTime;
			elementWaitTime = 2;
			List<WebElement> elements = getElements(elementDef, true);
			elementWaitTime = tempWaitTime;
			if (expectedResult) {
				//expected to be found
				if (elements != null) {
					if (elements.size() > index) {
						if (elementIsVisible(elements.get(index)))
							passed = true;
					}
				}
				if (!passed)
					fail("Failed to find expected element.");
			} else {
				//not expected
				passed = true;
				if (elements != null) {
					if (elements.size() > index) {
						//Verify this is effective, it may not be worth doing
						if (elementIsVisible(elements.get(index)))
							passed = false;
					}
				}
				if (!passed)
					fail("Found unexpected element.");
			}
		} catch (Exception e){fail("An error occurred while attempting to check if an element exists."); }
	}

	/**
	 * @param url url to compare the current url to
	 * @param expectedResult true if the values should match
	 */
	protected void compareUrl(String url, boolean expectedResult){
		if(expectedResult)
			Assert.assertTrue("Current URL does not match expected URL.", getCurrentUrl().equalsIgnoreCase(url));
		else
			Assert.assertFalse("Current URL matches the expected URL.", getCurrentUrl().equalsIgnoreCase(url));
	}

	/**
	 * @return the url of the current page
	 */
	protected String getCurrentUrl(){
		return webDriver.getCurrentUrl();
	}

	/**
	 * @param url url of the site to go to
	 */
	protected void goToSite(String url){
		try {
			webDriver.get(url);
		} catch (Exception e){fail("Unable to navigate to site: " + url);}
	}

	/**
	 * @param elementDef String of the reference to identify what element to click
	 */
	protected void click(String elementDef){
		try {
			getElement(elementDef).click();
		} catch (Exception e){fail("Unable to click element: " + elementDef);}
	}

	/**
	 * Clicks element with the given element definition and given text
	 *
	 * @param elementDef String of the reference to identify what element to click
	 * @param text Text of the exact element to click
	 */
	protected void clickByText(String elementDef, String text){
		try{
			getElementByText(elementDef, text).click();
		} catch (Exception e){fail("Unable to click element by text: " + elementDef + " text: " + text);}
	}

	/**
	 * @param elementDef element reference to the defined element in element_definition.csv
	 * @param text text on the element
	 * @param index index of the element with given text
	 */
	protected void clickByTextAndIndex(String elementDef, String text, int index){
		try{
			List<WebElement> elements = getElementsByText(elementDef, text);
			if(elements.size() >= index) {
				elements.get(index).click();
				lastElement = elements.get(index);
			} else
				fail("Index for element to click by text and index is out of bounds.");
		} catch (Exception e){fail("Unable to click element by text and index: " + elementDef + " text: " + text + " index: " + index);}
	}

	/**
	 * @param elementDef String of the reference to identify what element to click
	 * @param index Index (0-based) of the element to click
	 */
	protected void clickByIndex(String elementDef, int index){
		try {
			getElements(elementDef).get(index).click();
		} catch (Exception e){fail("Unable to click element: " + elementDef + " index: " + index);}
	}

	/**
	 * clicks on the first element found with the given attribute and value
	 *
	 * @param att Name of the attribute
	 * @param val Value of the given attribute
	 */
	protected void clickByAttributeAndValue(String att, String val){
		try{
			getElementByAttributeAndValue(att, val).click();
		} catch (Exception e){fail("Unable to click element with attribute: " + att + " value: " + val);}
	}

	/**
	 * @param elementDef String of the reference to identify what element to click
	 */
	protected void rightClick(String elementDef){
		//TODO future expantion due to using the mouse for this action
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param text Text to be entered into the given element
	 */
	protected void enterText(String elementDef, String text) {
		WebElement element = getElement(elementDef);
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param text Text to be entered into the given element and index
	 * @param index Index of the element to click
	 */
	protected void enterTextByIndex(String elementDef, String text, int index){
		try {
			WebElement element = getElements(elementDef).get(index);
			lastElement = element;
			element.clear();
			element.sendKeys(text);
		} catch (IndexOutOfBoundsException e){fail("Element index is not valid, please verify it is correct: " + elementDef + " index: " + index);
		} catch (Exception e){fail("Unable to send keys to: " + elementDef + " index: " + index);}
	}

	/**
	 * Separate method to validate only when applicable as textareas do not work well
	 * @param expectedText the text that is expected in the last element
	 */
	protected void validateTextEntry(String expectedText){
		try {
			assertTrue(lastElement.getText().equals(expectedText));
		} catch (Exception e){fail("Failed to validate text entry.");}
	}

	//TODO build some sort of default or random selection for dropdowns
	//TODO build a method to select dropdown option by index

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param desiredOption String of the desired selection from the dropdown
	 */
	protected void selectDropdown(String elementDef, String desiredOption){
		selectDropdownByIndex(elementDef, desiredOption, 0);
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param desiredOption String of the desired selection from the dropdown
	 * @param index Index of the element to select from
	 */
	protected void selectDropdownByIndex(String elementDef, String desiredOption, int index){
		try {
			Select dropdown = new Select(getElements(elementDef).get(index));
			selectDropdown(desiredOption, dropdown);
			if (!desiredOption.isEmpty() && positiveTest)
				assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
		} catch (Exception e){fail("Issue selecting dropdown: " + elementDef + " index: " + index);}
	}

	/**
	 * @param desiredOption String of the option to select from the dropdown
	 * @param dropdown Select of the specific dropdown to select values from
	 */
	protected void selectDropdown(String desiredOption, Select dropdown){
		if(BoltLibrary.elementListToUppercaseStringList(dropdown.getOptions()).contains(desiredOption.toUpperCase())){
			dropdown.selectByValue(desiredOption);
		} else{
			//TODO error
		}
	}

	protected String selectDropdown(int optionIndex, Select dropdown, boolean clearSelections){
		String desiredOption = "";

		//if(clearSelections)
		//	dropdown.deselectAll();

		if(dropdown.getOptions().size() > optionIndex) {
			desiredOption = dropdown.getOptions().get(optionIndex).getText();
			dropdown.selectByIndex(optionIndex);
		}else
			fail("Option Index out of range for the given element.");
		return desiredOption;
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param optionIndex Index of the desired option
	 */
	protected void selectDropdownOptionByIndex(String elementDef, int optionIndex){
		selectDropdownAndOptionByIndex(elementDef, 0, optionIndex);
	}

	protected void selectDropdownOptionRandom(String elementDef){
		try{
			int size = new Select(getElements(elementDef).get(0)).getOptions().size();
			selectDropdownOptionByIndex(elementDef, ThreadLocalRandom.current().nextInt(0,size));
		} catch (Exception e){fail("Issue selecting random dropdown: " + elementDef);}
	}

	protected void selectDropdownOptionRandom(String elementDef, int dropdownIndex){
		try{
			int size = new Select(getElements(elementDef).get(0)).getOptions().size();
			selectDropdownAndOptionByIndex(elementDef, dropdownIndex, ThreadLocalRandom.current().nextInt(0,size));
		} catch (Exception e){fail("Issue selecting random dropdown: " + elementDef);}
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param dropdownIndex Index of the desired dropdown
	 * @param optionIndex Index of the desired option
	 */
	protected void selectDropdownAndOptionByIndex(String elementDef, int dropdownIndex, int optionIndex){
		try {
			Select dropdown = new Select(getElements(elementDef).get(dropdownIndex));
			String desiredOption = selectDropdown(optionIndex, dropdown, true);
			if (positiveTest)
				assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
		} catch (Exception e){fail("Issue selecting dropdown: " + elementDef + " index: " + dropdownIndex);}
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param option dropdown option to check (case sensitive)
	 * @return true if the option exists, else false
	 */
	protected void checkOptionExistsInDropdown(String elementDef, String option){
		try{
			if(!getSelectedDropdownValues(elementDef, 0).contains(option))
				fail(option + " was not found in the dropdown.");
		} catch (Exception e){fail("Issue selecting dropdown: " + elementDef + " and checking if option existed: " + option);

		}
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param index Index of the element
	 * @param option dropdown option to check (case sensitive)
	 * @return true if the option exists, else false
	 */
	protected boolean checkOptionExistsInDropdown(String elementDef, int index, String option){
		return getSelectedDropdownValues(elementDef, index).contains(option);
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param index Index of the dropdown to get values from
	 * @return List String of all options in the dropdown
	 */
	protected List<String> getSelectedDropdownValues(String elementDef, int index){
		Select dropdown = null;
		try {
			dropdown = new Select(getElements(elementDef).get(index));
		} catch (Exception e){fail("Issue getting dropdown values, element: " + elementDef + " index: " + index);}
		if(dropdown != null)
			return BoltLibrary.elementListToUppercaseStringList(dropdown.getAllSelectedOptions());
		else
			return new ArrayList<String>();
	}

	/**
	 * @param dropdown Select dropdown of the dropdown to get options from
	 * @param enabledOptions true to get enabled options
	 * @return List String of all options in the dropdown
	 */
	private List<String> getDropdownOptions(Select dropdown, boolean enabledOptions){
		List<String> disiredOptions = new ArrayList<>();
		try {
			List<WebElement> options = dropdown.getOptions();
			for (WebElement tempElement : options) {
				if (tempElement.isEnabled() == enabledOptions) {
					disiredOptions.add(tempElement.getText());
				}
			}
		} catch (Exception e){fail("Issue getting dropdown options");}
		return disiredOptions;
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param index Index of the element to get options from
	 * @param enabledOptions true to get enabled options
	 * @return List String of all options in the dropdown
	 */
	protected List<String> getDropdownOptions(String elementDef, int index, boolean enabledOptions){ //possibly just get select instead
		return getDropdownOptions(new Select(getElements(elementDef).get(index)), enabledOptions);
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @param desiredOptions List String of all options to select from the dropdown
	 */
	protected void multiSelectDropdown(String elementDef, List<String> desiredOptions){
		try {
			Select dropdown = new Select(getElement(elementDef));
			desiredOptions.replaceAll(String::toUpperCase);

			dropdown.deselectAll();
			for (String desiredOption : desiredOptions) {
				selectDropdown(desiredOption, dropdown);
			}

			if (positiveTest)
				assertTrue(BoltLibrary.elementListToUppercaseStringList(dropdown.getAllSelectedOptions()).containsAll(desiredOptions));
		} catch (Exception e){fail("Issue selecting multiple dropdown options: " + elementDef);}
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 */
	protected void clickCheckbox(String elementDef){
		click(elementDef);
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 * @return true if the checkbox is selected, otherwise false
	 */
	protected boolean getCheckboxStatus(String elementDef){
		return getElement(elementDef).isSelected();
	}

	/**
	 * @param elementDef String of the reference to identify what element to click
	 * @param value true to select the checkbox, false otherwise
	 */
	protected void setCheckboxToValue(String elementDef, boolean value){
		if(value =! getCheckboxStatus(elementDef)) {
			clickCheckbox(elementDef);
			if(positiveTest)
				assertTrue(getCheckboxStatus(elementDef) == value);
		}
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 */
	protected void selectRadial(String elementDef){

	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 */
	protected void multiSelectRadial(String elementDef){

	}

	/**
	 * @param elementStartDef String of the reference to identify what element to start dragging from
	 * @param elementEndDef String of the reference to identify what element to stop dragging at
	 */
	protected void dragAndDrop(String elementStartDef, String elementEndDef){
		try {
			Actions action = new Actions(webDriver);
			action.dragAndDrop(getElement(elementStartDef), getElement(elementEndDef));
		} catch (Exception e){fail("Issue dragging and dropping with actions and elements");}
	}

	/**
	 * @param x1 x-coordinate to start dragging from
	 * @param y1 y-coordinate to start dragging from
	 * @param x2 x-coordinate to stop dragging from
	 * @param y2 y-coordinate to stop dragging from
	 */
	protected void dragAndDrop(int x1, int y1, int x2, int y2){
		try {
			Robot robo = new Robot();
			robo.mouseMove(x1, y1);
			robo.mousePress(InputEvent.BUTTON1_MASK);
			robo.mouseMove(x2, y2);
			robo.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (Exception e){
			fail("Issue dragging and dropping with robot");
		}
	}

	/**
	 * @param elementDef String of the reference to identify the element to drag
	 * @param x The distance to drag in the x plane
	 * @param y The distance to drag in the y plane
	 */
	protected void dragAndDrop(String elementDef, int x, int y){
		try {
			Actions action = new Actions(webDriver);
			action.dragAndDropBy(getElement(elementDef), x, y);
		} catch (Exception e){fail("Issue dragging and dropping with actions");}
	}

	/**
	 * @param expectedURL Url that is expected for the current page
	 */
	protected void verifyCurrentPage(String expectedURL){
		assertTrue(webDriver.getCurrentUrl().toUpperCase().equals(expectedURL.toUpperCase()));
	}

	/**
	 * @param elementDef String of the reference to identify the element to get text from
	 * @return String of the elements' text
	 */
	protected String getText(String elementDef){
		return getElement(elementDef).getText();
	}

	/**
	 * @param keyToPress string matching the key to be pressed
	 */
	protected void keyPress(String keyToPress){
		Keys key = Keys.valueOf(keyToPress.trim().toUpperCase());
		Actions action = new Actions(webDriver);
		action.sendKeys(key);
		action.build().perform();
	}

	/**
	 * @param keysToPress all keys to press at the same time
	 */
	protected void multiKeyPress(List<String> keysToPress){
		Actions actions = new Actions(webDriver);
		ArrayList<Keys> allKeys = new ArrayList<>();
		for(String key:keysToPress){
			allKeys.add(Keys.valueOf(key.trim().toUpperCase()));
			actions.keyDown(allKeys.get(allKeys.size() - 1));
		}
		for(Keys key: allKeys){
			actions.keyUp(key);
		}
		actions.build().perform();
	}

	protected void scrollUp(){
		scroll(-1000);
	}

	protected void scrollDown(){
		scroll(1000);
	}

	/**
	 * @param amount The amount to scroll, negative numbers scroll down
	 */
	protected void scroll(int amount){
		try {
			JavascriptExecutor jse = (JavascriptExecutor) webDriver;
			jse.executeScript("scroll(0," + amount + ");");
		} catch (Exception e){
			fail("Issue scrolling: " + amount);
		}
	}

	/**
	 * @param elementDef String of the reference to identify the element to use
	 */
	protected void handlePopUp(String elementDef){

	}

	protected void selectCalendarDate(){

	}

	protected void hoverOverElement(String text){
		try{
			Actions action = new Actions (webDriver);
			action.moveToElement(getElementsByTypeAndValue(elementTypes.LINKTEXT, text).get(0)).build().perform();
			BoltLibrary.implicitWait(webDriver,250);
		} catch (Exception e){fail("Unable to hover on element with text: " + text);}
	}

	protected void hoverOverElementPartialText(String text){
		try{
			Actions action = new Actions (webDriver);
			action.moveToElement(getElementsByTypeAndValue(elementTypes.PARTIAL_LINKTEXT, text).get(0)).build().perform();
			BoltLibrary.implicitWait(webDriver, 250);
		} catch (Exception e){fail("Unable to hover on element with text: " + text);}
	}

	/**
	 * @param elementDef String of the reference to identify the element to focus on
	 */
	protected void focusOnElement(String elementDef){
		focusOnElement(elementDef, 0);
	}

	/**
	 * @param elementDef String of the reference to identify the element to focus on
	 * @param index Index of the given element
	 */
	protected void focusOnElement(String elementDef, int index){
		focusOnElement(getElements(elementDef).get(index));
	}

	/**
	 * @param element WebElement to focus on
	 */
	protected void focusOnElement(WebElement element){
		try{
			new Actions(webDriver).moveToElement(element).perform();
		} catch (Exception e){fail("Issue focusing on the provided element.");}
	}

	//TODO wait for navigation/page change?? just use get elements?? or get element????????????

	//TODO check/validate values that are displayed

	/**
	 * @param elementDefinition String of the reference to identify the element to use
	 * @return true if the element is enabled
	 */
	protected boolean isEnabled(String elementDefinition){
		return getElement(elementDefinition).isEnabled();
	}

	/**
	 * @param elementDefinition String of the reference to identify the element to get
	 * @return Desired WebElement
	 */
	protected WebElement getElement(String elementDefinition){
		lastElement = getElements(elementDefinition).get(0);
		return lastElement;
		//TODO handle nulls and add focus to the element?
	}

	/**
	 * @param elementDefinition String of the reference to identify what element to get
	 * @param text Text of the element to get
	 * @return Desired WebElement
	 */
	protected WebElement getElementByText(String elementDefinition, String text){
		List<WebElement> elements = getElements(elementDefinition);
		WebElement foundElement = elements.get(0);
		text = text.toUpperCase();

		for(WebElement tempElement:elements){
			if(elementIsVisible(tempElement)) {
				if (tempElement.getText().toUpperCase().equals(text)) {
					foundElement = tempElement;
					break;
				}
			}
		}
		lastElement = foundElement;
		return foundElement;
	}

	protected List<WebElement> getElementsByText(String elementDefinition, String text){
		List<WebElement> elements = getElements(elementDefinition);
		List<WebElement> foundElements = new ArrayList<>();
		text = text.toUpperCase();

		for(WebElement tempElement:elements){
			if(elementIsVisible(tempElement)) {
				if (tempElement.getText().toUpperCase().equals(text)) {
					foundElements.add(tempElement);
				}
			}
		}
		return foundElements;
	}

	/**
	 * @param elementDef String of the reference to identify what element to use
	 * @param attribute String of the attribute to get from the element
	 * @return String of the attributes' value
	 */
	protected String getElementAttribute(String elementDef, String attribute){
		return getElementAttribute(getElement(elementDef), attribute);
	}

	/**
	 * @param element WebElement to get an attribute from
	 * @param attribute Name of the attribute to get
	 * @return Value of the attribute
	 */
	protected String getElementAttribute(WebElement element, String attribute){
		return element.getAttribute(attribute);
	}

	/**
	 * @param elementDefinition String of the reference to identify what element to check
	 * @return true if the element is visible
	 */
	protected boolean elementIsVisible(String elementDefinition){
		return elementIsVisible(getElement(elementDefinition));
	}

	/**
	 * @param element WebElement of the element to check
	 * @return true if the element is visible
	 */
	protected boolean elementIsVisible(WebElement element){
		return element.isDisplayed();
	}

	//TODO utilize element.findelement for searching through sibbling elements for a type of element

	/**
	 * Get all of the elements with a given relationship to the original
	 *
	 * @param initialElement WebElement to start looking from
	 * @param relationship Type of relationship between the given elementDefinition and the relativeDefinition
	 * @return WebElement of the related element
	 */
	protected List<WebElement> getElementRelatives(WebElement initialElement, relativeTypes relationship){//String relationship){
		List<WebElement> desiredElements = new ArrayList<>();
		try {
			switch (relationship) {
				case PARENT:
					desiredElements.add(initialElement.findElement(By.xpath("./..")));
					break;
				case AUNT:
					desiredElements = initialElement.findElement(By.xpath("./../..")).findElements(By.xpath(".//*"));
					desiredElements.remove(initialElement.findElement(By.xpath("./..")));
					break;
				case CHILD:
					desiredElements = initialElement.findElements(By.xpath(".//*"));
					break;
				case SIBLING:
					desiredElements = initialElement.findElement(By.xpath("./..")).findElements(By.xpath(".//*"));
					desiredElements.remove(initialElement);
					break;
			}
		} catch (Exception e){fail("Failed to find: " + relationship + " element(s)");}

		//TODO expand to include multiple elements returned
		return desiredElements;
	}

	/**
	 * Get the relatives of an element before or after the starting element (or its' parent)
	 *
	 * @param initialElement WebElement to start looking from
	 * @param relationship Type of relationship between the given elementDefinition and the relativeDefinition
	 * @return WebElement of the related element
	 */
	protected List<WebElement> getElementRelatives(WebElement initialElement, relativeTypes relationship, boolean beforeElement){
		List<WebElement> desiredElements = new ArrayList<>();
		boolean initialElementFound = false;
		try {
			switch (relationship) {
				case PARENT:
					desiredElements.add(initialElement.findElement(By.xpath("./..")));
					break;
				case AUNT:
					desiredElements = initialElement.findElement(By.xpath("./../..")).findElements(By.xpath(".//*"));
					//reset the initialElement for the search for elements before or after the initial
					initialElement = initialElement.findElement(By.xpath("./.."));
					break;
				case CHILD:
					desiredElements = initialElement.findElements(By.xpath(".//*"));
					break;
				case SIBLING:
					desiredElements = initialElement.findElement(By.xpath("./..")).findElements(By.xpath(".//*"));
					break;
			}
		} catch (Exception e){
			fail("Failed to find: " + relationship + " element(s)");
		}

		List<WebElement> finalList = new ArrayList<>();
		for(WebElement checkElement:desiredElements){

			if(checkElement.equals(initialElement)) {
				initialElementFound = true;
				continue;
			}

			if(initialElementFound != beforeElement)
				finalList.add(checkElement);
		}

		return finalList;
	}

	/**
	 * @param elementDefinition String of the reference to identify what element to use
	 * @param relationship Type of relationship between the given elementDefinition and the relativeDefinition
	 * @param relativeDefinition String of the reference to identify what element to refer to
	 * @return WebElement of the related element
	 */
	protected WebElement getElementRelative(String elementDefinition, relativeTypes relationship, String relativeDefinition){
		WebElement initialElement = getElement(elementDefinition);
		WebElement desiredElement = initialElement;
		try {
			switch (relationship) {
				case PARENT:
					desiredElement = initialElement.findElement(By.xpath("./.."));
					break;
				case AUNT:
					desiredElement = getRelativeElements(initialElement.findElement(By.xpath("./../..")), relativeDefinition).get(0);
					break;
				case CHILD:
					desiredElement = getRelativeElements(initialElement, relativeDefinition).get(0);
					break;
				case SIBLING:
					desiredElement = getRelativeElements(initialElement.findElement(By.xpath("./..")), relativeDefinition).get(0);
					break;
			}
		} catch (Exception e){fail("Issue focusing on the element: " + elementDefinition + " relationship: " + relationship + " relation definition: " + relativeDefinition);}

		//TODO expand to include multiple elements returned
		return desiredElement;
	}

	/**
	 * @param startingElement Element to start from to find the relative
	 * @param relativeDefinition String of the reference to identify the related element
	 * @return List of all WebElements that are children of the startingElement and match the relativeDefinition description
	 */
	protected List<WebElement> getRelativeElements(WebElement startingElement, String relativeDefinition){
		List<WebElement> elements = new ArrayList<WebElement>();
		String[] elementData;

		try{
			if(elementDefinitions.containsKey(relativeDefinition)) {
				elementData = elementDefinitions.get(relativeDefinition).split("~");
				if(elementData.length == 2){
					switch (elementTypes.valueOf(elementData[0].toUpperCase(Locale.ENGLISH))) {
						case CLASS:
							elements = startingElement.findElements(By.className(elementData[1]));// webDriver.findElements(By.className(value));
							break;
						case NAME:
							elements = startingElement.findElements(By.name(elementData[1]));
							break;
						case ID:
							elements = startingElement.findElements(By.id(elementData[1]));
							break;
						case XPATH:
							elements = startingElement.findElements(By.xpath(elementData[1]));
							break;
						case LINKTEXT:
							elements = startingElement.findElements(By.linkText(elementData[1]));
							break;
						case PARTIAL_LINKTEXT:
							elements = startingElement.findElements(By.partialLinkText(elementData[1]));
							break;
						case TAG:
							elements = startingElement.findElements(By.tagName(elementData[1]));
							break;
					}
					assertTrue("Element(s) " + relativeDefinition + " were not found as " + elementData[0],elements != null && elements.size() > 0);

				} else{
					//TODO error
				}
			} else{
				//TODO error
			}
		} catch (Exception e){fail("Issue getting relative element: " + relativeDefinition);}
		return elements;
	}

	protected List<WebElement> getElements(String elementDefinition){
		return getElements(elementDefinition, false);
	}

	/**
	 * Finds all elements with a given element definition
	 *
	 * @param elementDefinition page:object
	 * @return WebElements of the desired elementDefinition
	 */
	protected List<WebElement> getElements(String elementDefinition, boolean ignoreFailure){
		List<WebElement> elements = new ArrayList<WebElement>();
		String[] elementData;

		try {
			if (elementDefinitions.containsKey(elementDefinition)) {
				elementData = elementDefinitions.get(elementDefinition).split("~");
				if (elementData.length == 2) {
					elements = getElementsByTypeAndValue(elementTypes.valueOf(elementData[0].toUpperCase(Locale.ENGLISH)), elementData[1]);
					//TODO check for null
					assertTrue("Element(s) " + elementDefinition + " were not found on this page", elements != null && elements.size() > 0);
				} else {
					//TODO error
				}
			} else {
				//TODO error
			}
		} catch (Exception e){
			if(ignoreFailure){
				return null;
			} else
				fail("Issue focusing on the element: " + elementDefinition);
		}
		return elements;
	}

	/**
	 * Get all elements with a type of attribute and given value
	 *
	 * @param type Describes the type of the value (Class, Name, Id, etc.)
	 * @param value Value to search for
	 * @return List of WebElements that match the given type and value
	 */
	protected List<WebElement> getElementsByTypeAndValue(elementTypes type, String value){
		List<WebElement> elements = new ArrayList<WebElement>();
		WebDriverWait wait = new WebDriverWait(webDriver, elementWaitTime); //TODO make this variable
		//TODO add wait for the element and possibly loop a couple times
		try {
				switch (type) {
					case CLASS:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(value)));// webDriver.findElements(By.className(value));
						break;
					case NAME:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(value)));
						break;
					case ID:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(value)));
						break;
					case XPATH:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(value)));
						break;
					case LINKTEXT:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(value)));
						break;
					case PARTIAL_LINKTEXT:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(value)));
						break;
					case TAG:
						elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(value)));
						break;
				}
		} catch(TimeoutException e) {
			switch (type) {
				case CLASS:
					elements = webDriver.findElements(By.className(value));
					break;
				case NAME:
					elements = webDriver.findElements(By.name(value));
					break;
				case ID:
					elements = webDriver.findElements(By.id(value));
					break;
				case XPATH:
					elements = webDriver.findElements(By.xpath(value));
					break;
				case LINKTEXT:
					elements = webDriver.findElements(By.linkText(value));
					break;
				case PARTIAL_LINKTEXT:
					elements = webDriver.findElements(By.partialLinkText(value));
					break;
				case TAG:
					elements = webDriver.findElements(By.tagName(value));
					break;
			}
		} catch (Exception e){
			//TODO report errors to an error report
			fail("Exception thrown while getting elements");
		}
		lastElements = elements;
		return elements;
	}

	/**
	 * @param attribute attribute of an element
	 * @param value value of the attribute
	 * @return webelement of the first element found with the given attribute and value
	 */
	protected WebElement getElementByAttributeAndValue(String attribute, String value){
		WebElement element = null;
		try {
			element = webDriver.findElement(By.xpath("//*[@" + attribute + "='" + value + "']"));
		} catch (Exception e){
			fail("Failed to find element by attribute: " + attribute + " and value: " + value);
		}
		return element;
	}

	/**
	 * @param key Key to identify the needed values from local storage
	 * @return String of the value for the given key
	 */
	protected String getValuesFromLocalStorage(String key) {
		JavascriptExecutor js = ((JavascriptExecutor) webDriver);
		return (String) js.executeScript(String.format("return window.localStorage.getItem('%s');", key));
	}

	//TODO videos
	protected void checkVideoExists(){
		//check the iFrame exists (using selectFrame)
		//use FlashSelenium or flex-ui-selenium for interacting with the video

		//find the title
			//option for the title on the video or the title above the video
		//get the whole iframe
		//verify the video exists in the frame

		//add interactions to the video

		//TODO likely need to wait for more advancements in finding elements
		List<WebElement> iframes = getElementsByTypeAndValue(elementTypes.TAG,"iframe");


		//TODO sort through the iframes to see if the desired video exists.

		webDriver.switchTo().defaultContent();
	}

	protected void checkHTML5Video(){
		//custom stuff for HTML5 videos:
		//check controls functionality and playback using those elements
		//uses CSS elements

		//https://advancedtestautomation.blogspot.com/2016/07/test-video-using-selenium-webdriver.html
		//http://toolsqa.com/selenium-webdriver/selenium-webdriver-basics-video-tutorials/
	}

	//TODO slow down mouse movements
	//doesn't work. possibly not realistic due to running on remote machines
	//move mouse to hover first
/*	protected void realisticMouseMovement(WebElement element){

		Point starting = MouseInfo.getPointerInfo().getLocation();
		Point going = new Point(element.getLocation().getX(), element.getLocation().getY());

		Actions action = new Actions(webDriver);

		action.moveToElement(getElementsByTypeAndValue("LINKTEXT", "WEATHER").get(0)).build().perform();
		BoltLibrary.hardDelay(250);

		int xDiff = going.x - starting.x;
		int yDiff = going.y - starting.y;

		if(Math.abs(xDiff) > 20 || Math.abs(yDiff) > 20){
			for(int i = 0; i < 10; i++){
				action.moveByOffset((xDiff/10),(yDiff/10)).build().perform();
				BoltLibrary.hardDelay(80);
			}
		}

		action.moveToElement(element).click().build().perform();



	}
	*/
}
