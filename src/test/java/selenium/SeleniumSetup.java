package selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class SeleniumSetup {

    public static WebDriver getDriver() {

        String remote = System.getenv("REMOTE");
        String remoteURL = System.getenv("REMOTEURL");
        String browser = System.getenv("BROWSER");

        if (remote.toUpperCase().equals("TRUE")) {
            DesiredCapabilities capability;
            switch(browser){
                case "IE": capability = DesiredCapabilities.internetExplorer();
                    break;
                case "CHROME": capability = DesiredCapabilities.chrome();
                    break;
                case "FIREFOX": capability = DesiredCapabilities.firefox();
                    break;
                case "SAFARI":capability = DesiredCapabilities.safari();
                    break;
                case "EDGE":capability = DesiredCapabilities.edge();
                    break;
                default: capability = DesiredCapabilities.chrome();
                    browser = "CHROME";
                    break;
            }

            capability.setBrowserName(browser);
            try {
                return new RemoteWebDriver(new URL(remoteURL), capability);
            } catch (Exception e) {
                ChromeDriverManager.getInstance().setup();
                return new ChromeDriver();
            }

        } else {
            if (browser == null) {
                ChromeDriverManager.getInstance().setup();
                return new ChromeDriver();
            }
            switch (browser) {
                case "IE":
                    InternetExplorerDriverManager.getInstance().setup();
                    return new InternetExplorerDriver();
                case "FIREFOX":
                    FirefoxDriverManager.getInstance().setup();
                    return new FirefoxDriver();
                default:
                    ChromeDriverManager.getInstance().setup();
                    return new ChromeDriver();

            }
        }
    }
}
