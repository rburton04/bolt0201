package com.swatsolutions.bolt.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SeleniumSmartActions extends SeleniumActions{
	//these methods need more advanced ways to find the desired elements.
	//check if any parts can be broken out of what exists
	//add needed javadocs
	//add try-catch statements where needed

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

	//TODO adjust this so that it will look for both input fields and for textareas by default.

	/**
	 * @param text the text to enter
	 * @param label the label that identifies the element
	 * @param fieldType type of field to enter text into (field, textarea, etc.)
	 */
	protected void enterTextByLabel(String text, String label, String fieldType){
		WebElement element = getElementWithLabel(label, fieldType);
		if(element != null){
			element.sendKeys(text);
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

	//TODO read and store table

	//TODO compare table method
	//TODO check value exists in table
	//TODO check if row exists in table
	//TODO etc.

	/**
	 * @param tableName header of the table that is found above the given table
	 */
	protected void readTableTo2DArray (String tableName){

		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		WebElement table = getTable(tableName);
		//get list of elements with "tr" (a row)

		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(WebElement row:rows){
			ArrayList<String> rowVals = new ArrayList<>();
			List<WebElement> column = row.findElements(By.tagName("td"));
			for(WebElement val:column){
				rowVals.add(val.getText());
			}
			results.add(rowVals);
		}
		//then go through each column using "td"
		//put together the parts to an array
		System.out.println(results);
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

			//check for a sibbling that is a dropdown
			List<WebElement> sibblings = getElementRelatives(labelElement, "SIBLING");
			for(WebElement element: sibblings){
				if(element.getTagName().equalsIgnoreCase(elementType)){
					return element;
				}
			}

			List<WebElement> aunts = getElementRelatives(labelElement, "AUNT");
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
			//check the cousins. May need to know where the original/starting element was for this to be most effective

		} catch (Exception e){
			fail("Failed to validate text entry.");
		}

		return desiredElement;
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
