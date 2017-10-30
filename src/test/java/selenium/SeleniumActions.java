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


public class SeleniumActions extends Driver{

    protected void goToSite(String url){
        webDriver.get(url);
    }
    protected void click(String elementDef){
        getElement(elementDef).click();
    }
    protected void clickByText(String elementDef, String text){
        getElementByText(elementDef, text).click();
    }
    protected void clickByLinkedText(String text){
        getElementsByTypeAndValue("LINKTEXT", text).get(0).click();
    }
    protected void clickByIndex(String elementDef, int index){
        getElements(elementDef).get(index).click();
    }
    protected void rightClick(String elementDef){
        //TODO future expantion due to using the mouse for this action
    }

    protected void enterText(String elementDef, String text) {
        WebElement element = getElement(elementDef);
        element.clear();
        element.sendKeys(text);
    }
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

    protected void selectDropdown(String elementDef, String desiredOption){
        selectDropdownByIndex(elementDef, desiredOption, 0);
    }
    protected void selectDropdownByIndex(String elementDef, String desiredOption, int index){
        Select dropdown = new Select(getElements(elementDef).get(index));
        selectDropdown(desiredOption, dropdown, true);
        if(!desiredOption.isEmpty())
            assertTrue(dropdown.getFirstSelectedOption().getText().equals(desiredOption));
    }
    protected void selectDropdown(String desiredOption, Select dropdown, boolean clearSelections){
        if(library.elementListToUppercaseStringList(dropdown.getOptions()).contains(desiredOption.toUpperCase())){
            dropdown.selectByValue(desiredOption);
        } else{
            //TODO error
        }
    }
    protected List<String> getSelectedDropdownValues(String elementDef, int index){
        Select dropdown = new Select(getElements(elementDef).get(index));
        return library.elementListToUppercaseStringList(dropdown.getAllSelectedOptions());
    }
    protected List<String> getDropdownOptions(Select dropdown, boolean enabledOptions){
        List<WebElement> options = dropdown.getOptions();
        List<String> disiredOptions = new ArrayList<>();
        for(WebElement tempElement:options){
            if(tempElement.isEnabled() == enabledOptions){
                disiredOptions.add(tempElement.getText());
            }
        }

        return disiredOptions;
    }
    protected List<String> getDropdownOptions(String elementDef, int index, boolean enabledOptions){ //possibly just get select instead
        return getDropdownOptions(new Select(getElements(elementDef).get(index)), enabledOptions);
    }

    protected void multiSelectDropdown(String elementDef, List<String> desiredOptions){
        Select dropdown = new Select(getElement(elementDef));
        desiredOptions.replaceAll(String::toUpperCase);

        dropdown.deselectAll();
        for(String desiredOption:desiredOptions){
            selectDropdown(desiredOption, dropdown, false);
        }

        assertTrue(library.elementListToUppercaseStringList(dropdown.getAllSelectedOptions()).containsAll(desiredOptions));
    }
    protected void clickCheckbox(String elementDef){
        getElement(elementDef).click();
    }
    protected boolean getCheckboxStatus(String elementDef){
        return getElement(elementDef).isSelected();
    }
    protected void setCheckboxToValue(String elementDef, boolean value){
        if(value =! getCheckboxStatus(elementDef)) {
            clickCheckbox(elementDef);
            assertTrue(getCheckboxStatus(elementDef) == value);
        }
    }
    protected void selectRadial(String elementDef){

    }
    protected void multiSelectRadial(String elementDef){

    }
    protected void dragAndDrop(String elementStartDef, String elementEndDef){
        Actions action = new Actions(webDriver);
        action.dragAndDrop(getElement(elementStartDef), getElement(elementEndDef));
    }
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
    protected void dragAndDrop(String elementDef, int x, int y){
        Actions action = new Actions(webDriver);
        action.dragAndDropBy(getElement(elementDef), x, y);
    }
    protected void verifyCurrentPage(String expectedURL){
        assertTrue(webDriver.getCurrentUrl().toUpperCase().equals(expectedURL.toUpperCase()));
    }
    protected String getText(String elementDef){
        return getElement(elementDef).getText();
    }
    protected String getAttribute(String elementDef, String attribute){
        return getElement(elementDef).getAttribute(attribute);
    }
    protected void scrollUp(){
        scroll("250");
    }
    protected void scrollDown(){
        scroll("-250");
    }
    protected void scroll(String amount){
        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("scroll(0," + amount);
    }
    protected void handlePopUp(String elementDef){

    }
    protected void selectCalendarDate(){

    }
    protected void focusOnElement(String elementDef){
        focusOnElement(elementDef, 0);
    }
    protected void focusOnElement(String elementDef, int index){
        try{
            new Actions(webDriver).moveToElement(getElements(elementDef).get(index)).perform();
        } catch (Exception e){
            //TODO error handling
        }
    }

    //TODO wait for navigation/page change?? just use get elements?? or get element????????????

    //TODO check/validate values that are displayed

    protected boolean isEnabled(String elementDefinition){
        return getElement(elementDefinition).isEnabled();
    }
    protected WebElement getElement(String elementDefinition){
        lastElement = getElements(elementDefinition).get(0);
        return lastElement;
        //TODO handle nulls and add focus to the element?
    }
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

    protected String getElementAttribute(String elementDef, String attribute){
        return getElementAttribute(getElement(elementDef), attribute);
    }
    protected String getElementAttribute(WebElement element, String attribute){
        return element.getAttribute(attribute);
    }

    protected boolean elementIsVisible(String elementDefinition){
        return elementIsVisible(getElement(elementDefinition));
    }
    protected boolean elementIsVisible(WebElement element){
        return element.isDisplayed();
    }

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
                    //TODO check for null

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

            } else{
                //TODO error
            }
        } else{
            //TODO error
        }
        return elements;
    }

    protected List<WebElement> getElementsByTypeAndValue(String type, String value){
        List<WebElement> elements = new ArrayList<WebElement>();
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        //TODO add wait for the element and possibly loop a couple times
        try {
            //TODO the wait may be enough and not require this while loop
            //while(elements == null) { //TODO add another limiter here so it will break at a certain point (no infinate loops!!!) also add a delay for each loop beyond the first
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
            //}
        } catch (Exception e){
            //TODO error handle how to deal with this type of issue
        }
        lastElements = elements;
        return elements;
    }

    //TODO get values from local storage??
    //(String) js.executeScript(String.format("return window.localStorage.getItem('%s');", key));

}
