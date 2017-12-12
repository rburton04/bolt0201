package selenium;

import utils.library;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class SeleniumActions extends Driver{

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
     * @param elementDef String of the reference to identify what element to click
     * @param text Text of the exact element to click
     */
    protected void clickByText(String elementDef, String text){
        try{
            getElementByText(elementDef, text).click();
        } catch (Exception e){fail("Unable to click element by text: " + elementDef + " text: " + text);}
    }

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
     * @param elementDef String of the reference to identify what element to click
     * @param index Index (0-based) of the element to click
     */
    protected void clickByIndex(String elementDef, int index){
        try {
            getElements(elementDef).get(index).click();
        } catch (Exception e){fail("Unable to click element: " + elementDef + " index: " + index);}
    }

    protected void clickDynamically(String buttonText){
        clickByIndexDynamically(buttonText, 0);
    }

    protected void clickDynamicallyByType(String text, String type){
        clickDynamicallyByIndexAndType(text, type, 0);
    }

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

    protected void clickByIndexDynamically(String buttonText, int index){
        List<WebElement> elements = getElementsByTypeAndValue("LINKTEXT", buttonText);

        if(elements.size() > index){
            elements.get(index).click();
        }
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

    //TODO SmartMethods start

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
    protected void enterTextByLabel(String text, String label, String fieldType){
        WebElement element = getElementWithLabel(label, fieldType);
        if(element != null){
            element.sendKeys(text);
        }

    }

    //TODO make the attribute variable as it could be a different attribute for other sites
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

    //TODO SmartMethods end

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
            selectDropdown(desiredOption, dropdown, true);
            if (!desiredOption.isEmpty() && positiveTest)
                assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
        } catch (Exception e){fail("Issue selecting dropdown: " + elementDef + " index: " + index);}
    }

    /**
     * @param desiredOption String of the option to select from the dropdown
     * @param dropdown Select of the specific dropdown to select values from
     * @param clearSelections boolean to clear all previous selections
     */
    private void selectDropdown(String desiredOption, Select dropdown, boolean clearSelections){
        if(library.elementListToUppercaseStringList(dropdown.getOptions()).contains(desiredOption.toUpperCase())){
            dropdown.selectByValue(desiredOption);
        } else{
            //TODO error
        }
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
            return library.elementListToUppercaseStringList(dropdown.getAllSelectedOptions());
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
                selectDropdown(desiredOption, dropdown, false);
            }

            if (positiveTest)
                assertTrue(library.elementListToUppercaseStringList(dropdown.getAllSelectedOptions()).containsAll(desiredOptions));
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

    protected void scrollUp(){
        scroll(250);
    }

    protected void scrollDown(){
        scroll(-250);
    }

    /**
     * @param amount The amount to scroll, negative numbers scroll down
     */
    protected void scroll(int amount){
        try {
            JavascriptExecutor jse = (JavascriptExecutor) webDriver;
            jse.executeScript("scroll(0," + amount);
        } catch (Exception e){fail("Issue scrolling: " + amount);}
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
            action.moveToElement(getElementsByTypeAndValue("LINKTEXT", text).get(0)).build().perform();
            library.hardDelay(250);
        } catch (Exception e){fail("Unable to hover on element with text: " + text);}
    }

    protected void hoverOverElementPartialText(String text){
        try{
            Actions action = new Actions (webDriver);
            action.moveToElement(getElementsByTypeAndValue("PARTIAL_LINKTEXT", text).get(0)).build().perform();
            library.hardDelay(250);
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

    /**
     * @param initialElement WebElement to start looking from
     * @param relationship Type of relationship between the given elementDefinition and the relativeDefinition
     * @return WebElement of the related element
     */
    protected List<WebElement> getElementRelatives(WebElement initialElement, String relationship){
        List<WebElement> desiredElements = new ArrayList<>();
        try {
            switch (relationship.toUpperCase()) {
                case "PARENT":
                    desiredElements.add(initialElement.findElement(By.xpath("./..")));
                    break;
                case "AUNT":
                    desiredElements = initialElement.findElement(By.xpath("./../..")).findElements(By.xpath(".//*"));
                    desiredElements.remove(initialElement.findElement(By.xpath("./..")));
                    break;
                case "CHILD":
                    desiredElements = initialElement.findElements(By.xpath(".//*"));
                    break;
                case "SIBLING":
                    desiredElements = initialElement.findElement(By.xpath("./..")).findElements(By.xpath(".//*"));
                    desiredElements.remove(initialElement);
                    break;
            }
        } catch (Exception e){fail("Failed to find: " + relationship + " element(s)");}

        //TODO expand to include multiple elements returned
        return desiredElements;
    }

    /**
     * @param initialElement WebElement to start looking from
     * @param relationship Type of relationship between the given elementDefinition and the relativeDefinition
     * @return WebElement of the related element
     */
    protected List<WebElement> getElementRelatives(WebElement initialElement, String relationship, boolean beforeElement){
        List<WebElement> desiredElements = new ArrayList<>();
        boolean initialElementFound = false;
        try {
            switch (relationship.toUpperCase()) {
                case "PARENT":
                    desiredElements.add(initialElement.findElement(By.xpath("./..")));
                    break;
                case "AUNT":
                    desiredElements = initialElement.findElement(By.xpath("./../..")).findElements(By.xpath(".//*"));
                    //reset the initialElement for the search for elements before or after the initial
                    initialElement = initialElement.findElement(By.xpath("./.."));
                    break;
                case "CHILD":
                    desiredElements = initialElement.findElements(By.xpath(".//*"));
                    break;
                case "SIBLING":
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
    protected WebElement getElementRelative(String elementDefinition, String relationship, String relativeDefinition){
        WebElement initialElement = getElement(elementDefinition);
        WebElement desiredElement = initialElement;
        try {
            switch (relationship.toUpperCase()) {
                case "PARENT":
                    desiredElement = initialElement.findElement(By.xpath("./.."));
                    break;
                case "CHILD":
                    desiredElement = getRelativeElements(initialElement, relativeDefinition).get(0);
                    break;
                case "SIBLING":
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
                    switch (elementData[0].toUpperCase()) {
                        case "CLASS":
                            elements = startingElement.findElements(By.className(elementData[1]));// webDriver.findElements(By.className(value));
                            break;
                        case "NAME":
                            elements = startingElement.findElements(By.name(elementData[1]));
                            break;
                        case "ID":
                            elements = startingElement.findElements(By.id(elementData[1]));
                            break;
                        case "XPATH":
                            elements = startingElement.findElements(By.xpath(elementData[1]));
                            break;
                        case "LINKTEXT":
                            elements = startingElement.findElements(By.linkText(elementData[1]));
                            break;
                        case "TAG":
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

    /**
     * @param elementDefinition page:object
     * @return WebElements of the desired elementDefinition
     */
    protected List<WebElement> getElements(String elementDefinition){
        List<WebElement> elements = new ArrayList<WebElement>();
        String[] elementData;

        try {
            if (elementDefinitions.containsKey(elementDefinition)) {
                elementData = elementDefinitions.get(elementDefinition).split("~");
                if (elementData.length == 2) {
                    elements = getElementsByTypeAndValue(elementData[0].toUpperCase(), elementData[1]);
                    //TODO check for null
                    assertTrue("Element(s) " + elementDefinition + " were not found on this page", elements != null && elements.size() > 0);
                } else {
                    //TODO error
                }
            } else {
                //TODO error
            }
        } catch (Exception e){fail("Issue focusing on the element: " + elementDefinition);}
        return elements;
    }

    /**
     * @param type Describes the type of the value (Class, Name, Id, etc.)
     * @param value Value to search for
     * @return List of WebElements that match the given type and value
     */
    protected List<WebElement> getElementsByTypeAndValue(String type, String value){
        List<WebElement> elements = new ArrayList<WebElement>();
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        //TODO add wait for the element and possibly loop a couple times
        try {
                switch (type) {
                    case "CLASS":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(value)));// webDriver.findElements(By.className(value));
                        break;
                    case "NAME":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(value)));
                        break;
                    case "ID":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(value)));
                        break;
                    case "XPATH":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(value)));
                        break;
                    case "LINKTEXT":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(value)));
                        break;
                    case "PARTIAL_LINKTEXT":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(value)));
                        break;
                    case "TAG":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(value)));
                        break;
                }
        } catch(TimeoutException e) {
            switch (type) {
                case "CLASS":
                    elements = webDriver.findElements(By.className(value));
                    break;
                case "NAME":
                    elements = webDriver.findElements(By.name(value));
                    break;
                case "ID":
                    elements = webDriver.findElements(By.id(value));
                    break;
                case "XPATH":
                    elements = webDriver.findElements(By.xpath(value));
                    break;
                case "LINKTEXT":
                    elements = webDriver.findElements(By.linkText(value));
                    break;
                case "PARTIAL_LINKTEXT":
                    elements = webDriver.findElements(By.partialLinkText(value));
                    break;
                case "TAG":
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
     * @param key Key to identify the needed values from local storage
     * @return String of the value for the given key
     */
    protected String getValuesFromLocalStorage(String key) {
        JavascriptExecutor js = ((JavascriptExecutor) webDriver);
        return (String) js.executeScript(String.format("return window.localStorage.getItem('%s');", key));
    }

}
