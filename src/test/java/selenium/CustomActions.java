package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CustomActions extends SeleniumActions{

    protected void validateResults(int value){
        List<WebElement> resultData = webDriver.findElements(By.className("atsSearchResultsData"));
        int resultsFound = resultData.size()/3;
        assertTrue(resultsFound == value);
    }
}