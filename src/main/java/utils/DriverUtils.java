package utils;


import driverFactory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static utils.Constants.*;

public class DriverUtils
{
    static final Logger log = LogManager.getLogger(DriverUtils.class);
    private static Properties prop;

    public static RemoteWebDriver getDriver(WebDriver driver, String browser, String baseURL) throws Exception
    {
        prop = new Properties();
        prop.load(new FileInputStream(DRIVER_PROP_FILE));
        if(isWindows())
        {
            if(browser.equalsIgnoreCase("firefox"))
            {
                System.setProperty("webdriver.gecko.driver",prop.getProperty(FIREFOX_DRIVER_WIN));
                driver = DriverFactory.getInstance().getDriver("firefox");
            }
            if(browser.equalsIgnoreCase("chrome"))
            {
                System.setProperty("webdriver.chrome.driver",prop.getProperty(CHROME_DRIVER_WIN));
                driver = DriverFactory.getInstance().getDriver("chrome");
            }
        }
        else if(isMac())
        {
            if(browser.equalsIgnoreCase("firefox"))
            {
                System.setProperty("webdriver.gecko.driver",prop.getProperty(FIREFOX_DRIVER_MAC));
                driver = DriverFactory.getInstance().getDriver("firefox");
            }
            if(browser.equalsIgnoreCase("chrome"))
            {
                System.setProperty("webdriver.chrome.driver",prop.getProperty(CHROME_DRIVER_MAC));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                driver = DriverFactory.getInstance().getDriver("chrome",options);
                log.info("Chrome Driver is instantiated");
            }
        }

        driver.get(baseURL);
        driver.manage().window().fullscreen();
        //long implicitWait = Long.parseLong(prop.getProperty("implicitWaitTimeout"));
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        return (RemoteWebDriver) driver;
    }

    public static RemoteWebDriver getDriver(RemoteWebDriver driver,String hub,String browser,String baseUrl)
    {

        try
        {
            driver = DriverFactory.getInstance().getDriver(browser,hub);
            driver.get(baseUrl);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  driver;
    }

    private static boolean isWindows()
    {
        String os = System .getProperty("os.name");
        return os.startsWith("Windows");
    }

    private static boolean isMac()
    {
        String os = System.getProperty("os.name");
        return os.startsWith("Mac");
    }
}
