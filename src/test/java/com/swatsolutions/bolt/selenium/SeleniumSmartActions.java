package com.swatsolutions.bolt.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SeleniumSmartActions extends SeleniumActions{
	//these methods need more advanced ways to find the desired elements.
	//check if any parts can be broken out of what exists
	//add needed javadocs
	//add try-catch statements where needed

	//TODO select a checkbox by name/text
	//TODO radial button(s)

	/**
	 * @param label The label next to the checkbox
	 */
	protected void smartClickCheckbox(String label){
		WebElement element = getElementWithLabel(label, "input");
		if(element != null)
			element.click();
	}

	/**
	 * @param text Text of the element to click. Finds the first element with the given text.
	 */
	protected void clickByLinkedText(String text){
		try{
			getElementsByTypeAndValue("LINKTEXT", text).get(0).click();
		} catch (Exception e){fail("Unable to click linked text: " + text);}
	}

	/**
	 * @param text Text of the element to click. Finds the first element with the given text.
	 */
	protected void clickByLinkedTextPartialText(String text){
		try{
			getElementsByTypeAndValue("PARTIAL_LINKTEXT", text).get(0).click();
		} catch (Exception e){fail("Unable to click linked text: " + text);}
	}

	/**
	 * @param text text on the element to click
	 * @param index index of the element to click
	 */
	protected void clickByIndexDynamically(String text, int index){
		List<WebElement> elements = getElementsByTypeAndValue("LINKTEXT", text);

		if(elements.size() > index){
			elements.get(index).click();
		}
	}

	/**\
	 * @param label The label next to the checkbox
	 * @param value true to select the checkbox, false to uncheck it
	 */
	protected void smartSetCheckboxToValue(String label, boolean value){
		WebElement element = getElementWithLabel(label, "input");
		WebElement element3 = null;
		List<WebElement> temp = getElementsByTypeAndValue("LINKTEXT", label);
		for(WebElement element2:temp){
			if(elementIsVisible(element2)){
				element3 = element2;
				break;
			}
		}

		element = findElementRelativeByType(element3,"input");

		if(element != null) {

			if (value != element.isSelected()) {
				smartClickCheckbox(label);
				if (positiveTest)
					assertTrue(element.isSelected() == value);
			}
		}
	}

	/**
	 * @param buttonText text on the element to be clicked
	 */
	protected void clickDynamically(String buttonText){
		clickByIndexDynamically(buttonText, 0);
	}

	/**
	 * @param text text on the element to be clicked
	 * @param type the type of element to be clicked (tag name)
	 */
	protected void clickDynamicallyByType(String text, String type){
		clickDynamicallyByIndexAndType(text, type, 0);
	}

	/**
	 * @param text text on the element to be clicked
	 * @param type the type of element to be clicked (tag name)
	 * @param index index of the element to click
	 */
	protected void clickDynamicallyByIndexAndType(String text, String type, int index){
		List<WebElement> elements = getAllElementsOfGivenType(type);
		int numFound = 0;
		boolean clicked = false;
		for(WebElement element:elements){
			if(element.getText().equalsIgnoreCase(text)){
				if(numFound == index) {
					element.click();
					clicked = true;
					break;
				} else
					numFound++;
			}
		}
		if(!clicked){
			fail("Failed to click the element of type: " + type + " text: " + text + " and index: " + index);
		}
	}

	/**
	 * @param label the label identifying the element to be clicked
	 * @param type the type of element to be clicked (tag name)
	 */
	protected void clickDynamicallyByLabel(String label, String type){
		WebElement element = getElementWithLabel(label, type);
		if(element != null)
			element.click();
		else
			fail("Element with label: " + label + " was not found");

	}

	/**
	 * @param defaultValue The default value of the desired dropdown
	 * @param desiredOption The desired option to be selected
	 */
	protected void selectValueFromDropdownWithDefaultValue(String defaultValue, String desiredOption){
		WebElement element = getDropdownWithDefaultValue(defaultValue);
		if(element != null){
			Select dropdown = new Select(element);
			selectDropdown(desiredOption, dropdown, true);
			if (!desiredOption.isEmpty() && positiveTest)
				assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
		}
	}

	/**
	 * @param defaultValue the pre-selected value of the desired dropdown
	 * @return the desired dropdown, or null if it is not found
	 */
	protected WebElement getDropdownWithDefaultValue(String defaultValue){
		WebElement desiredElement = null;
		try{
			List<WebElement> elements = getAllElementsOfGivenType("select");

			for(WebElement element:elements){
				Select select = new Select(element);
				if(select.getFirstSelectedOption().getText().equalsIgnoreCase(defaultValue)) {
					desiredElement = element;
					break;
				}
			}
		} catch (Exception e){
			fail("Failed to find dropdown with default value: " + defaultValue);
		}

		return desiredElement;
	}

	/**
	 * @param label The label that identifies the dropdown
	 * @param desiredOption The option to be selected in the dropdown
	 */
	protected void selectValueFromDropdownByLabel(String label, String desiredOption){
		WebElement element = getElementWithLabel(label, "select");//getDropdownWithDefaultValue(defaultValue);
		if(element != null){
			Select dropdown = new Select(element);
			selectDropdown(desiredOption, dropdown, true);
			if (!desiredOption.isEmpty() && positiveTest)
				assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
		}
	}

	/**
	 * @param label the label identifying the dropdown
	 * @param optionIndex the index of the item to select
	 */
	protected void selectIndexFromDropdownByLabel(String label, int optionIndex){
		WebElement element = getElementWithLabel(label, "select");//getDropdownWithDefaultValue(defaultValue);
		if(element != null){
			Select dropdown = new Select(element);
			String desiredOption = selectDropdown(optionIndex, dropdown, true);

			if (positiveTest)
				assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
		}
	}

	/**
	 * @param defaultValue The default value of the dropdown
	 * @param index Index of the item to select
	 */
	protected void selectIndexFromDropdownWithDefaultValue(String defaultValue, int index){
		WebElement element = getDropdownWithDefaultValue(defaultValue);
		if(element != null){
			Select dropdown = new Select(element);
			String desiredOption = selectDropdown(index, dropdown, true);

			if (positiveTest)
				assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
		}
	}

	/**
	 * @param label the label identifying the dropdown
	 */
	protected void selectRandomFromDropdownByLabel(String label){
		try{
			int size = new Select(getElementWithLabel(label, "select")).getOptions().size();
			selectIndexFromDropdownByLabel(label, ThreadLocalRandom.current().nextInt(0,size));
		} catch (Exception e){fail("Issue selecting random dropdown labeled: " + label);}
	}

	/**
	 * @param defaultValue The default value of the dropdown
	 */
	protected void selectRandomFromDropdownByDefaultValue(String defaultValue){
		try{
			int size = new Select(getDropdownWithDefaultValue(defaultValue)).getOptions().size();
			selectIndexFromDropdownWithDefaultValue(defaultValue, ThreadLocalRandom.current().nextInt(0,size));
		} catch (Exception e){fail("Issue selecting random dropdown with default value: " + defaultValue);}
	}

	//TODO adjust this so that it will look for both input fields and for textareas by default.

	/**
	 * @param text the text to enter
	 * @param label the label that identifies the element
	 * @param fieldType type of field to enter text into (field, textarea, etc.)
	 */
	protected void enterTextByLabel(String text, String label, String fieldType){
		try {
			WebElement element = getElementWithLabel(label, fieldType);
			if (element != null) {
				element.sendKeys(text);
			} else
				fail("Failed to enter text by the label: " + label);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail("Failed to enter text by the label: " + label);
		}
	}

	/**
	 * @param text the text to enter
	 * @param label the label that identifies the element
	 * @param fieldType type of field to enter text into (field, textarea, etc.)
	 * @param index the index of the element to enter text into
	 */
	protected void  enterTextByLabelAndIndex(String text, String label, String fieldType, int index){
		try{
			List<WebElement> elements = getElementsWithLabel(label, fieldType);
			if(index < elements.size()) {
				if(elements.get(index) != null)
					elements.get(index).sendKeys(text);
				else
					fail("Failed to enter text by the label: " + label + " and Index: " + index);
			} else
				fail("Failed to enter text by the label: " + label + " and Index: " + index);
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail("Failed to enter text by the label: " + label + " and Index: " + index);
		}
	}

	//TODO make the attribute variable as it could be a different attribute for other sites

	/**
	 * @param text text to be entered
	 * @param defaultVal the default value that exists in an element
	 */
	protected void enterTextByDefaultValues(String text, String defaultVal){
		try {
			List<WebElement> elements = getAllElementsOfGivenType("input");
			for (WebElement element : elements) {
				String placeholder = element.getAttribute("placeholder");
				String ariaLabel = element.getAttribute("aria-label");
				if(placeholder == null)
					placeholder = "";
				if(ariaLabel == null)
					ariaLabel = "";
				if (placeholder.equalsIgnoreCase(defaultVal) ||
						ariaLabel.equalsIgnoreCase(defaultVal)) {
					//TODO verify if this is even correct. Might change with different applications
					//TODO verify if .getText would work instead
					element.sendKeys(text);
					break;
				}
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @param text text to be entered
	 * @param defaultVal the default value that exists in an element
	 * @param index the index of the element to enter text into
	 */
	protected void enterTextByDefaultValuesAndIndex(String text, String defaultVal, int index){
		try {
			List<WebElement> elements = getAllElementsOfGivenType("input");
			int numFound = 0;
			for (WebElement element : elements) {
				String placeholder = element.getAttribute("placeholder");
				String ariaLabel = element.getAttribute("aria-label");
				if(placeholder == null)
					placeholder = "";
				if(ariaLabel == null)
					ariaLabel = "";
				if ((placeholder.equalsIgnoreCase(defaultVal) ||
						ariaLabel.equalsIgnoreCase(defaultVal)) &&
						numFound == index) {
					//TODO verify if this is even correct. Might change with different applications
					//TODO verify if .getText would work instead
					element.sendKeys(text);
					break;
				} else
					numFound++;
			}

			if(numFound != index)
				fail("Failed to enter text by default value into: " + defaultVal + " and index: " + index);

		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	//TODO read and store table

	//TODO compare table method
	//TODO check value exists in table
	//TODO check if row exists in table
	//TODO etc.

	protected void verifyTableContains(String value, boolean expectedResult, ArrayList<ArrayList<String>> discoveredTable){
		boolean valueFound = false;
		for(ArrayList<String> discoveredRow:discoveredTable){
			if(discoveredRow.contains(value))
				valueFound = true;
		}

		if(valueFound != expectedResult){
			if(expectedResult)
				fail("The value: " + value + " was not found in the table when it was not expected.");
			else
				fail("The value: " + value + " was found in the table when it was not expected.");
		}
	}

	protected void verifyTableContains(List<String> row, boolean expectedResult, ArrayList<ArrayList<String>> discoveredTable){
		if(discoveredTable.contains(row) != expectedResult){
			if(expectedResult)
				fail("The row: '" + row.toString() + "' was not found in the table when it was not expected.");
			else
				fail("The row: '" + row.toString() + "' was found in the table when it was not expected.");
		}
	}

	protected void verifyTableContains(ArrayList<ArrayList<String>> expectedTable, boolean expectedResult, ArrayList<ArrayList<String>> discoveredTable){

	}

	protected void verifyTablesMatch(ArrayList<ArrayList<String>> expectedTable, boolean expectedResult, ArrayList<ArrayList<String>> discoveredTable){
		boolean match = true;
		if(expectedTable.size() == discoveredTable.size()){
			for(int counter = 0; counter < expectedTable.size(); counter++){
				ArrayList<String> expectedRow = expectedTable.get(counter);
				ArrayList<String> discoveredRow = discoveredTable.get(counter);

				if(expectedRow.size() != discoveredRow.size()) {
					match = false;
					break;
				}

				for(int index = 0; index < expectedRow.size(); index++){
					if(!expectedRow.get(index).equals(discoveredRow.get(index))){
						match = false;
						break;
					}
				}

				if(!match)
					break;
			}
		}

		if(match != expectedResult){
			if(expectedResult)
				fail("The provided tables were not found to match when they were expected to.");
			else
				fail("The provided tables were found to match when they were not expected to.");
		}
	}

	/**
	 * @param tableName header of the table that is found above the given table
	 */
	protected ArrayList<ArrayList<String>> readTableTo2DArray (String tableName){

		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		WebElement table = getTable(tableName);

		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(WebElement row:rows){
			ArrayList<String> rowVals = new ArrayList<>();
			List<WebElement> column = row.findElements(By.tagName("td"));
			for(WebElement val:column){
				rowVals.add(val.getText());
			}
			results.add(rowVals);
		}

		return results;
	}

	/**
	 * @param tableName The title for the desired table
	 * @return the whole table element else null;
	 */
	private WebElement getTable(String tableName){
		WebElement desiredTable = null;
		WebElement heading = null;
		List<WebElement> elements;//getAllElementsOfGivenType("table");
		List <WebElement> titles = getElementsByTypeAndValue("LINKTEXT",tableName);

		for(WebElement element : titles) {
			if (elementIsVisible(element)) {
				focusOnElement(element);
				heading = element;

//check siblings
				elements = getElementRelatives(heading, "SIBLING", false); //TODO not working right
				for (WebElement sibling : elements) {
					if (sibling.getTagName().equalsIgnoreCase("table")) {
						return sibling;
					}
				}

//check for the first and second aunt after the tableName and the given sibblings
				int count;

				elements = getElementRelatives(heading, "AUNT", false); //TODO not working right
				if (elements.size() > 2)
					count = 2;
				else
					count = elements.size();

				for (int index = 0; index < count; index++) {
					if (elements.get(index).getTagName().equalsIgnoreCase("table")) {
						return elements.get(index);
					}
//check the aunts' children
					List<WebElement> children = getElementRelatives(elements.get(index), "CHILD");
					for (WebElement child : children) {
						if (child.getTagName().equalsIgnoreCase("table")) {
							return child;
						}
					}
				}
			}
		}

		return desiredTable;
	}

	/**
	 * @param label The text of the label to search for
	 * @param elementType The type of element that is desired
	 * @return WebElement of the desired type with the given label
	 */
	protected WebElement getElementWithLabel(String label, String elementType){
		WebElement desiredElement = null;
		try{
			//TODO may need more intelegence to start looking from the starting place, not just from the start of all the elements found.
			//TODO break out some parts to helper methods
			//TODO handle ':' and other types of potential chars at the end of the label string
			List<WebElement> elements = getAllElementsOfGivenType("label");
			WebElement labelElement = null;
			//List<WebElement> elements = getAllElementsOfGivenType("select");

			for(WebElement element:elements){
				///List<WebElement> sibblings = getElementRelatives(element, "SIBLING");
				if(element.getText().equalsIgnoreCase(label)){
					labelElement = element;
					break;
				}
			}
			desiredElement = findElementRelativeByType(labelElement, elementType);

		} catch (Exception e){
			fail("Failed to validate text entry.");
		}

		return desiredElement;
	}

	protected List<WebElement> getElementsWithLabel(String label, String elementType){
		List<WebElement> desiredElements = null;
		try{
			//TODO may need more intelegence to start looking from the starting place, not just from the start of all the elements found.
			//TODO break out some parts to helper methods
			//TODO handle ':' and other types of potential chars at the end of the label string
			List<WebElement> elements = getAllElementsOfGivenType("label");
			List<WebElement> labelElement = null;
			//List<WebElement> elements = getAllElementsOfGivenType("select");

			for(WebElement element:elements){
				///List<WebElement> sibblings = getElementRelatives(element, "SIBLING");
				if(element.getText().equalsIgnoreCase(label)){
					labelElement.add(element);
				}
			}

			for(WebElement elementLabel:labelElement){
				WebElement foundElement = findElementRelativeByType(elementLabel, elementType);
				if(foundElement != null)
					desiredElements.add(foundElement);
			}

		} catch (Exception e){
			fail("Failed to validate text entry.");
		}
		return desiredElements;
	}

	/**
	 * @param baseElement WebElement by which to start looking
	 * @param elementType Type of the desired element (tagname)
	 * @return WebElement of the found element, else null
	 */
	private WebElement findElementRelativeByType(WebElement baseElement, String elementType){
		try{
			//check for a sibbling that is a dropdown
			List<WebElement> sibblings = getElementRelatives(baseElement, "SIBLING");
			for(WebElement element: sibblings){
				if(element.getTagName().equalsIgnoreCase(elementType)){
					return element;
				}
			}

			List<WebElement> aunts = getElementRelatives(baseElement, "AUNT");
			//check the aunts
			for(WebElement element: aunts){
				if(element.getTagName().equalsIgnoreCase(elementType)){
					return element;
				}
			}

			//check the cousins
			for(WebElement element: aunts){
				List<WebElement> cousins = getElementRelatives(element, "CHILD");
				for(WebElement element2: cousins) {
					if (element.getTagName().equalsIgnoreCase(elementType)) {
						return element;
					}
				}
			}
		} catch(Exception e){

		}
		return null;
	}

	/**
	 * @param type the type of the elements desired
	 * @return all elements of the given type that are visible
	 */
	private List<WebElement> getAllElementsOfGivenType(String type){
		List<WebElement> elements = new ArrayList();
		try{
			elements = webDriver.findElements(By.tagName(type));

			int elementCount = elements.size();
			for(int index = 0; index < elementCount; index++){
				if(!elementIsVisible(elements.get(index))) {
					elements.remove(index);
					elementCount--;
					index--;
				}
			}
		} catch (Exception e){
			fail("Issue looking for all " + type + " elements.");
		}

		return elements;
	}
}
