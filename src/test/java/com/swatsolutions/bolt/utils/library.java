package com.swatsolutions.bolt.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * @param miliseconds number of miliseconds to delay
     */
    public static void hardDelay(int miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	/**
	 * @param driver WebDriver
	 * @param miliseconds number of miliseconds to wait
	 */
	public static void implicitWait(WebDriver driver, long miliseconds){
        try {
            driver.manage().timeouts().implicitlyWait(miliseconds, TimeUnit.MILLISECONDS);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
