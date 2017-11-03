package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.library;

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
        webDriver.get(url);
    }

    /**
     * @param elementDef String of the reference to identify what element to click
     */
    protected void click(String elementDef){
        getElement(elementDef).click();
    }

    /**
     * @param elementDef String of the reference to identify what element to click
     * @param text Text of the exact element to click
     */
    protected void clickByText(String elementDef, String text){
        getElementByText(elementDef, text).click();
    }

    /**
     * @param text Text of the element to click. Finds the first element with the given text.
     */
    protected void clickByLinkedText(String text){
        getElementsByTypeAndValue("LINKTEXT", text).get(0).click();
    }

    /**
     * @param elementDef String of the reference to identify what element to click
     * @param index Index (0-based) of the element to click
     */
    protected void clickByIndex(String elementDef, int index){
        getElements(elementDef).get(index).click();
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
        WebElement element = getElements(elementDef).get(index);
        lastElement = element;
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Separate method to validate only when applicable as textareas do not work well
     * @param expectedText the text that is expected in the last element
     */
    protected void validateTextEntry(String expectedText){
        assertTrue(lastElement.getText().equals(expectedText));
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
        Select dropdown = new Select(getElements(elementDef).get(index));
        selectDropdown(desiredOption, dropdown, true);
        if(!desiredOption.isEmpty())
            assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
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
     * @param index Index of the dropdown to get values from
     * @return List String of all options in the dropdown
     */
    protected List<String> getSelectedDropdownValues(String elementDef, int index){
        Select dropdown = new Select(getElements(elementDef).get(index));
        return library.elementListToUppercaseStringList(dropdown.getAllSelectedOptions());
    }

    /**
     * @param dropdown Select dropdown of the dropdown to get options from
     * @param enabledOptions true to get enabled options
     * @return List String of all options in the dropdown
     */
    private List<String> getDropdownOptions(Select dropdown, boolean enabledOptions){
        List<WebElement> options = dropdown.getOptions();
        List<String> disiredOptions = new ArrayList<>();
        for(WebElement tempElement:options){
            if(tempElement.isEnabled() == enabledOptions){
                disiredOptions.add(tempElement.getText());
            }
        }

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
        Select dropdown = new Select(getElement(elementDef));
        desiredOptions.replaceAll(String::toUpperCase);

        dropdown.deselectAll();
        for(String desiredOption:desiredOptions){
            selectDropdown(desiredOption, dropdown, false);
        }

        assertTrue(library.elementListToUppercaseStringList(dropdown.getAllSelectedOptions()).containsAll(desiredOptions));
    }

    /**
     * @param elementDef String of the reference to identify the element to use
     */
    protected void clickCheckbox(String elementDef){
        getElement(elementDef).click();
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
        Actions action = new Actions(webDriver);
        action.dragAndDrop(getElement(elementStartDef), getElement(elementEndDef));
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
            //TODO error
        }
    }

    /**
     * @param elementDef String of the reference to identify the element to drag
     * @param x The distance to drag in the x plane
     * @param y The distance to drag in the y plane
     */
    protected void dragAndDrop(String elementDef, int x, int y){
        Actions action = new Actions(webDriver);
        action.dragAndDropBy(getElement(elementDef), x, y);
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
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("scroll(0," + amount);
    }

    /**
     * @param elementDef String of the reference to identify the element to use
     */
    protected void handlePopUp(String elementDef){

    }

    protected void selectCalendarDate(){

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
        try{
            new Actions(webDriver).moveToElement(getElements(elementDef).get(index)).perform();
        } catch (Exception e){
            //TODO error handling
        }
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
            if(tempElement.getText().toUpperCase().equals(text)){
                foundElement = tempElement;
                break;
            }
        }
        lastElement = foundElement;
        return foundElement;
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
     * @param elementDefinition String of the reference to identify what element to use
     * @param relationship Type of relationship between the given elementDefinition and the relativeDefinition
     * @param relativeDefinition String of the reference to identify what element to refer to
     * @return WebElement of the related element
     */
    protected WebElement getElementRelative(String elementDefinition, String relationship, String relativeDefinition){
        WebElement initialElement = getElement(elementDefinition);
        WebElement desiredElement = initialElement;
        switch (relationship.toUpperCase()){
            case "PARENT": desiredElement = initialElement.findElement(By.xpath("./.."));
                break;
            case "CHILD": desiredElement = getRelativeElements(initialElement, relativeDefinition).get(0);
                break;
            case "SIBLING": desiredElement = getRelativeElements(initialElement.findElement(By.xpath("./..")), relativeDefinition).get(0);
                break;
        }
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
        } catch (Exception e){

        }
        return elements;
    }

    /**
     * @param elementDefinition page:object
     * @return WebElements of the desired elementDefinition
     */
    protected List<WebElement> getElements(String elementDefinition){
        List<WebElement> elements = new ArrayList<WebElement>();
        String[] elementData;

        if(elementDefinitions.containsKey(elementDefinition)) {
            elementData = elementDefinitions.get(elementDefinition).split("~");
            if(elementData.length == 2){
                elements = getElementsByTypeAndValue(elementData[0].toUpperCase(), elementData[1]);
                //TODO check for null
                assertTrue("Element(s) " + elementDefinition + " were not found on this page",elements != null && elements.size() > 0);
            } else{
                //TODO error
            }
        } else{
            //TODO error
        }
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
                    case "TAG":
                        elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(value)));
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
