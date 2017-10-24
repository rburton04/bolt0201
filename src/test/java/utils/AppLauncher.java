package utils;

import com.thoughtworks.gauge.Step;
import selenium.Driver;

public class AppLauncher {

    public static String SWAT_URL = System.getenv("SWAT_URL");
    public static String BBC_URL = System.getenv("BBC_URL");

    @Step("Go to swat solutions website")
    public void launchSwatApplication() {
        Driver.webDriver.get(SWAT_URL);
    }
    @Step("Go to bbc website")
    public void launchBBCApplication() {
        Driver.webDriver.get(SWAT_URL);
    }
}