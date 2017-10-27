package utils;

import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;

public class library {

    /**
     * @param elementList List of elements that text values are needed from.
     * @return List String  of the text from each element in the list.
     */
    public static List<String> elementListToUppercaseStringList(List<WebElement> elementList){
        List<String> uppercaseList = new LinkedList<String>();
        for(WebElement element:elementList){
            uppercaseList.add(element.getText().toUpperCase());
        }
        return uppercaseList;
    }
}
