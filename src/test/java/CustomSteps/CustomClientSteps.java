package CustomSteps;

import com.thoughtworks.gauge.Step;
import selenium.GeneralSteps;

import static org.junit.Assert.assertFalse;

public class CustomClientSteps extends GeneralSteps {

    @Step("Go to swat solutions website")
    public void launchSwatApplication() { goToSite(SWAT_URL); }
    @Step("Go to bbc website")
    public void launchBBCApplication() { goToSite(BBC_URL); }

    @Step("Click Search button")
    public void clickSearchButton (){
        click(spec + ":searchButton");
    }
    @Step ("Verify <value> results")
    public void verifyResults (int value) {
        validateResults(value);
    }

    @Step("Verify value <value> doesn't exist in dropdown <index>")
    public void verifyValueDoesntExist(String value, int index){
        assertFalse(checkOptionExistsInDropdown(spec + ":dropdown", index - 1, value));
    }

}