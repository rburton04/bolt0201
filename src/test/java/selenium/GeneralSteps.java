package selenium;

import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.Step;
import utils.jMeter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GeneralSteps extends SeleniumActions {
    //protected String spec = Var.spec;
    /*
    navigate to..
    go to <url>
    click ...
    select ...
     */



    @Step("Hover over <tab> tab")
    public void hover (String tab){
        hoverOverElement(tab);
    }

    @Step("Hover over tab with partial text <tab>")
    public void hoverPartialText (String tab){
        hoverOverElementPartialText(tab);
    }

    @ContinueOnFailure
    @Step("Verify current page is <url>")
    public void verifyPage(String url){
        verifyCurrentPage(url);
    }

    @ContinueOnFailure
    @Step("Verify <option> is an option in dropdown")
    public void verifyOptionInDropdown(String option){
        checkOptionExistsInDropdown(spec + ":dropdown", option);
    }

    @Step("Go to AUT")
    public void navigateToAUT(){
        goToSite(aut);
    }

    @Step("Navigate to <tab> tab")
    public void navigateToTab(String tab) {
        clickByLinkedText(tab);
    }

    @Step("Navigate to tab with partial text <tab>")
    public void navigateToTabPartialText(String tab) {
        clickByLinkedTextPartialText(tab);
    }

    @Step("Click <button> button")
    public void clickButton(String button){
        clickByText(spec + ":button", button);
    }

    @Step("Click on the <index> <button> button")
    public void clickButton(int index, String button){
        //the -1 is used to move the index to a 0-based indexing as compared to a 1 based
        clickByTextAndIndex(spec+":button", button, index - 1);
    }

    @Step("Select <dropdown> from dropdown")
    public void selectDropdown (String dropdown){
        selectDropdown(spec + ":dropdown", dropdown);
    }

    @Step("Select <dropdown1>, <dropdown2> from dropdown")
    public void multiDropdowns(String dw1, String dw2){
        List<String> selections = new LinkedList<String>();
        selections.add(dw1);
        selections.add(dw2);
        multiSelectDropdown(spec + ":dropdown",selections);
    }

    @Step("Select value <value> on dropdown <index>")
    public void selectDropdown (String value, int index) {
        //-1 is used as people don't count with 0-based indexing
        selectDropdownByIndex(spec + ":dropdown", value, index - 1);
    }

    @Step({"Go to <website> website", "Go to <website>"})
    public void navToWebsite(String website){
        goToSite(website);

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
}
