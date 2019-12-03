package uiTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTest
{
    static final Logger log = LogManager.getLogger(HomePageTest.class);
    HomePage hp = new HomePage();

    @Test
    public void search()
    {
        log.debug("Before calling the search");
        hp.search("Selenium WebDriver");
        log.info("Search is completed");
        System.out.println(System.getProperty("user.dir"));
    }
}
