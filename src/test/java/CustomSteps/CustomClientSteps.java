package CustomSteps;

import com.thoughtworks.gauge.Step;
import selenium.GeneralSteps;

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

}