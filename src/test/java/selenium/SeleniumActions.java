package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SeleniumActions extends Driver{


    protected void click(){

    }
    protected void rightClick(){

    }
    protected void enterText() {

    }
    protected void slectDropdown(){

    }
    protected void multiSelectDropdown(){

    }
    protected void clickCheckbox(){

    }
    protected void selectRadial(){

    }
    protected void multiSelectRadial(){

    }
    protected void dragAndDrop(){

    }
    protected void verifyCurrentPage(){

    }
    protected String getText(){
        String elementText = "";

        return elementText;
    }
    protected void getAttribute(){

    }
    protected void scroll(){

    }
    protected void handlePopUp(){

    }
    protected void selectCalendarDate(){

    }
    protected WebElement getElement(String elementDefinition){
        //use 'getElements' and return the first object
        return getElements(elementDefinition).get(0);
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
    protected void getElementDefinition(){

    }


}
