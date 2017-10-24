package selenium;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;

public class GeneralSteps extends SeleniumActions {

    /*
    navigate to..
    go to <url>
    click ...
    select ...

     */

    @Step("Navigate to <tab> tab")
    public void navigateToTab(String tab) {
        //WebDriver webDriver = Driver.webDriver;
        //TODO figure out if the linkText thing works.
        webDriver.findElement(By.linkText(tab)).click();

    }

    @Step("Click <button> button")
    public void clickButton(String button){

    }

    @Step("Go to <url>")
    public void navigateToUrl(String url){
        webDriver.get(url);
    }

    @Step("Select <dropdown> from dropdown")
    public void selectDropdown (String dropdown){

    }

}
