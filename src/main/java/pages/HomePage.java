package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage
{
    static final Logger log = LogManager.getLogger(HomePage.class);
    String searchXpath = "//input[@name='q']";
    String submitXpath = "//input[@type='submit']";
    public HomePage()
    {
        super();
    }

    public void search(String searchString)
    {
        WebElement element = driver.findElement(By.xpath(searchXpath));
//        System.out.println(driver.toString());
//        driver.findElement(By.xpath(searchXpath)).sendKeys(searchString);
//        driver.findElement(By.xpath(searchXpath)).submit();
        element.sendKeys(searchString);
        log.info("Search element is located");
        element.submit();
        log.info("Search value is submitted");
    }
}
