package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import utils.library;
import static org.junit.Assert.assertTrue;


public class SeleniumActions extends Driver{


    protected void click(String elementDef){
        getElement(elementDef).click();
    }
    protected void rightClick(String elementDef){
        //TODO future expantion due to using the mouse for this action
    }
    protected void enterText(String elementDef, String text) {
        getElement(elementDef).sendKeys(text);
    }
    //TODO create index version
    protected void selectDropdown(String elementDef, String desiredOption){
        Select dropdown = new Select(getElement(elementDef));
        selectDropdown(desiredOption, dropdown, true);
        assertTrue(dropdown.getFirstSelectedOption().getText().toUpperCase().equals(desiredOption));
    }

    protected void selectDropdown(String desiredOption, Select dropdown, boolean clearSelections){
        desiredOption = desiredOption.toUpperCase();

        if(library.elementListToUppercaseStringList(dropdown.getOptions()).contains(desiredOption)){
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
    protected void dragAndDrop(String elementDef){

    }
    protected void verifyCurrentPage(String expectedURL){
        assertTrue(webDriver.getCurrentUrl().toUpperCase().equals(expectedURL.toUpperCase()));
    }
    protected String getText(){
        String elementText = "";

        return elementText;
    }
    protected String getAttribute(String elementDef, String attribute){
        return getElement(elementDef).getAttribute(attribute);
    }
    protected void scroll(){

    }
    protected void handlePopUp(String elementDef){

    }
    protected void selectCalendarDate(){

    }

    protected WebElement getElement(String elementDefinition){
        return getElements(elementDefinition).get(0);
        //TODO handle nulls and add focus to the element?
    }

    /**
     * @param elementDefinition page:object
     * @return WebElements of the desired elementDefinition
     */
    protected List<WebElement> getElements(String elementDefinition){
        List<WebElement> elements = new ArrayList<WebElement>();
        String[] elementData;

        if(elementDefinitions.containsKey(elementDefinition)) {
            elementData = elementDefinitions.get(elementDefinition).split(":");
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
