package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
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
        element.clear();
        element.sendKeys(text);
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

    protected WebElement getElement(String elementDefinition){
        return getElements(elementDefinition).get(0);
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
        return foundElement;
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
        //TODO add wait for the element and possibly loop a couple times
        try {
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
                case "TAG":
                    elements = webDriver.findElements(By.tagName(value));
                    break;

            }
        } catch (Exception e){
            //TODO error handle how to deal with this type of issue
        }

        return elements;
    }
}
