import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.driver.Driver;
import java.util.List;
import org.openqa.selenium.support.ui.Select;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class BasicFunctions extends Driver {

    @Step("Navigate to <tab> tab")
    public void navigateToTab(String tab) {
        //WebDriver webDriver = Driver.webDriver;
        //TODO figure out if the linkText thing works.
        webDriver.findElement(By.linkText(tab)).click();

    }

    @Step ("Click <buttonName> button")
    public void clickButton (String buttonName) {
        WebDriver webDriver = Driver.webDriver;
        List<WebElement> buttons;
        if(buttonName.equals("SEARCH")) {
            webDriver.findElements(By.className("atsButton")).get(0).click();
            return;
        } else
            buttons = webDriver.findElements(By.className("mybtn-big"));
        for(WebElement button:buttons){
            if(button.getText().toUpperCase().equals(buttonName.toUpperCase())){
                button.click();
                break;
            }
        }
    }


    @Step ("Select value <value> on dropdown <index>")
    public void selectDropdown (String value, int index) {
        WebDriver webDriver = Driver.webDriver;
        List<WebElement> dropdowns = webDriver.findElements(By.className("atsSelect"));

        Select dropdown = new Select(dropdowns.get(index - 1));
        dropdown.selectByValue(value);
    }

    @Step ("Enter text <text> in field <index>")
    public void enterText (String text, int index) {
        WebDriver webDriver = Driver.webDriver;
        List<WebElement> fields = webDriver.findElements(By.name("j_id0:j_id1:atsForm:j_id77"));

        fields.get(index - 1).clear();
        fields.get(index - 1).sendKeys(text);
    }

    //* Select value <location> on dropdown "1"
    //* Enter text <keyword> in field "1"
}