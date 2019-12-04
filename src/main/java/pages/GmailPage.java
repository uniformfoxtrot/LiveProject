package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GmailPage extends BasePage
{
    static final Logger log = LogManager.getLogger(GmailPage.class);
    String searchXpath = "//input[@name='q']";
    String submitXpath = "//input[@type='submit']";
}
