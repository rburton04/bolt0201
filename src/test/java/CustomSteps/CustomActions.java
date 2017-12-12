package CustomSteps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import selenium.GeneralSteps;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CustomActions extends GeneralSteps {

    protected void validateResults(int value){
        try {
            List<WebElement> resultData = webDriver.findElements(By.className("atsSearchResultsData"));
            int resultsFound = resultData.size() / 3;
            assertTrue(resultsFound == value);
        } catch (Exception e){
            fail("Error validating the results.");
        }
    }
}