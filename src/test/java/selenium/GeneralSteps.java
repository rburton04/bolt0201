package selenium;

import com.thoughtworks.gauge.Step;
import utils.jMeter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GeneralSteps extends CustomActions {
    //protected String spec = Var.spec;
    /*
    navigate to..
    go to <url>
    click ...
    select ...
     */

    @Step ("Run JMeter Script <testName> <searchOption>")
    public void jMeterScript(String testName, String searchOption){
        try {
            jMeter jmeter = new jMeter();
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("test", searchOption);
            jmeter.runJMeterTest(testName, vars);
        } catch (Exception e){
            System.out.println("error");
        }
    }

    @Step("Navigate to <tab> tab")
    public void navigateToTab(String tab) {
        clickByLinkedText(tab);
    }

    @Step("Click <button> button")
    public void clickButton(String button){
        clickByText(spec + ":button", button);
    }

    @Step("Select <dropdown> from dropdown")
    public void selectDropdown (String dropdown){
        selectDropdown(spec + ":dropdown", dropdown);
    }

    @Step({"Go to <website> website", "Go to <website>"})
    public void navToWebsite(String website){
        goToSite(website);

    }

    @Step("Select value <value> on dropdown <index>")
    public void selectDropdown (String value, int index) {
        //-1 is used as people don't count with 0-based indexing
        selectDropdownByIndex(spec + ":dropdown", value, index - 1);
    }

    @Step("Enter text <text> in field <index>")
    public void enterText (String text, int index) {
        //-1 is used as people don't count with 0-based indexing
        enterTextByIndex(spec + ":textField", text, index - 1);
    }

    @Step("<check> checkbox")
    public void checkbox(String selection){
        if(selection.toUpperCase().equals("UNCHECK"))
            setCheckboxToValue(spec + ":checkbox", false);
        else
            setCheckboxToValue(spec + ":checkbox", true);
    }

    @Step("Scroll <direction>")
    public void scroll(String direction){
        if(direction.toUpperCase().equals("UP"))
            scrollUp();
        else
            scrollDown();
    }

    @Step("Select <dropdown1>, <dropdown2> from dropdown")
    public void multiDropdowns(String dw1, String dw2){
        List<String> selections = new LinkedList<String>();
        selections.add(dw1);
        selections.add(dw2);
        multiSelectDropdown(spec + ":dropdown",selections);
    }
}
