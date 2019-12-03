package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public abstract class BasePage
{
    static final Logger log = LogManager.getLogger(BasePage.class);
    public WebDriver driver;
    private Properties prop = getProperties();
    private String currentPath = System.getProperty("user.dir");

    public BasePage ()
    {
        String baseUrl = prop.getProperty("baseUrl");
        log.info("Base Url is set "+baseUrl);
        String browser = prop.getProperty("defaultBrowser");
        log.info("Browser value is  "+browser);
        try
        {
            driver = DriverUtils.getDriver(driver,browser,baseUrl);
            log.info("Driver is instantiated and base URL is loaded");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public Properties getProperties()
    {
        InetAddress host =null;
        prop = new Properties();
        try
        {
            prop.load(this.getClass().getResourceAsStream("/env-qa.properties"));
            // The above would directly fetch the file.
            //prop.load(new FileInputStream("//Users//lee//Documents//LiveProject//src//main//resources//env-qa.properties"));
            System.out.println(currentPath);
//            prop.load(new FileInputStream(currentPath+"//src//main//resources//env-qa.properties"));
            ///Users/lee/Documents/LiveProject/src/main/resources/env-qa.properties

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return prop;
    }
}
