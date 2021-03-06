package com.swatsolutions.bolt.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    protected static boolean remoteRun = false;
    protected static String remoteUrl = "";
    /**
     * @return WebDriver driver
     */
    public static WebDriver getDriver() {

        String remoteSystem = System.getenv("REMOTE_SYSTEM");
        String remote = System.getenv("REMOTE");
        String remoteURL = System.getenv("REMOTEURL");
        String browser = System.getenv("BROWSER").toLowerCase();
        String browserVersion = System.getenv("BROWSER_VERSION");
        boolean headless = false;

        if (remote.toUpperCase().equals("TRUE")) {
            DesiredCapabilities capability;
            remoteRun = true;


            switch(browser.toUpperCase()){
                case "IE": capability = DesiredCapabilities.internetExplorer();
                    break;
                case "PHANTOMJS": headless = true;
                    capability = DesiredCapabilities.phantomjs();
                    browser = "phantomjs";

                    //options = ChromeDriverManager.getInstance().setup();

                    break;
	            case "HEADLESS": capability = DesiredCapabilities.chrome();
	                browser = "chrome";
		            ChromeOptions chromeHeadless = new ChromeOptions();
		            chromeHeadless.addArguments("headless");
		            //this may need to be changed for virtual mobile testing or something
		            chromeHeadless.addArguments("window-size=1200x600");
		            capability.setCapability(ChromeOptions.CAPABILITY, chromeHeadless);
		            break;
                case "CHROME": capability = DesiredCapabilities.chrome();
                    break;
                case "FIREFOX": capability = DesiredCapabilities.firefox();
                    break;
                case "SAFARI":capability = DesiredCapabilities.safari();
                    break;
                case "EDGE":capability = DesiredCapabilities.edge();
                    break;
	            case "ANDROID":capability = DesiredCapabilities.android();
	            	break;
	            case "IPHONE":capability = DesiredCapabilities.iphone();
	            	break;
	            case "IPAD":capability = DesiredCapabilities.ipad();
	            	break;
                default: capability = DesiredCapabilities.chrome();
                    browser = "CHROME";
                    break;
            }


            capability.setCapability("browserName", browser);

            switch(remoteSystem.toUpperCase()){
                case "BROWSERSTACK":
                    //https://www.browserstack.com/automate/capabilities
                    remoteURL = System.getenv("BROWSERSTACK_URL");
                    capability.setCapability("browserName", browser);
                    capability.setCapability("version", browserVersion);

//                  capability.setCapability("version", browserVersion);
                    capability.setCapability("browserstack.local", "true");
                    //capability.setCapability("browserstack.localIdentifier", "Test123"); --can be used when having multiple browserstack connections
                    //version not required. If not provided, runs on latest
//                    capability.setCapability("browser_version", browserVersion);
                    capability.setCapability("os", "Windows");
                    capability.setCapability("os_version", "10");
                    capability.setCapability("resolution", "1024x768");
                    capability.setCapability("build", "version1");
                    capability.setCapability("project", "newintropage");
//                    capability.setCapability("acceptSslCerts", "true");
                    break;
                case "SAUCELABS":
                    //https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options
                    remoteURL = System.getenv("SAUCELABS_URL");
                    capability.setCapability("browserName", browser);
                    capability.setCapability("version", browserVersion);

//                  capability.setCapability("version", browserVersion);
                    capability.setCapability("platform", "Windows 10");
//                    capability.setCapability("version", browserVersion);
                    capability.setCapability("name", "TESTING SauceLabs");
                    break;
                case "OPENSHIFT":
                    remoteURL = System.getenv("OPENSHIFT_URL");

//           String remoteURL = System.getenv("REMOTEURL");

                    break;
            }

            remoteUrl = remoteURL;

            //Local Testing
            //capability.setBrowserName(browser);
            try {
                return new RemoteWebDriver(new URL(remoteURL), capability);
            } catch (Exception e) {
                //report error
                //ChromeDriverManager.getInstance().setup();
                //return new ChromeDriver();
                return null;
            }

        } else {
            if (browser == null) {
                ChromeDriverManager.getInstance().setup();
                return new ChromeDriver();
            }
            switch (browser.toUpperCase()) {
                case "IE":
                    InternetExplorerDriverManager.getInstance().setup();
                    return new InternetExplorerDriver();
                case "FIREFOX":
                    FirefoxDriverManager.getInstance().setup();
                    return new FirefoxDriver();
	            case "PHANTOMJS":
		            headless = true;
		            PhantomJsDriverManager.getInstance().setup();
		            browser = "phantomjs";
	            	return new PhantomJSDriver();
	            case "HEADLESS": headless = true;
	                ChromeDriverManager.getInstance().setup();
		            ChromeOptions options = new ChromeOptions();
		            options.addArguments("headless");
		            //this may need to be changed for virtual mobile testing or something
		            options.addArguments("window-size=1200x600");
		            return new ChromeDriver(options);
                default:
                    ChromeDriverManager.getInstance().setup();
                    ChromeOptions cOption = new ChromeOptions().addArguments("disable-infobars");
                    return new ChromeDriver(cOption);

            }
        }
    }

    public static boolean getRemoteRun(){
    	return remoteRun;
    }

    public static String getRemoteUrl(){
    	return remoteUrl;
    }
}
