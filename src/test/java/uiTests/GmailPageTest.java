package uiTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GmailPageTest
{
    WebDriver driver;
    static final Logger log = LogManager.getLogger(GmailPageTest.class);

    private static final String YAML_DATA =
            "username: regressionjun25\n"+
            "password: Coimbatore123\n";
    String currentPath = System.getProperty("user.dir");
    String YAML_FILE=currentPath+"/src/test/java/resources/login-ui.yml";

    @BeforeTest
    void setUpMethod()
    {
        System.setProperty("webdriver.chrome.driver","/Users/lee/Downloads/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }

    @AfterTest()
    void tearDown()
    {
        driver.quit();
    }

    @DataProvider(name="directString")
    public Object[][] parseYaml()
    {
        Yaml yaml = new Yaml();
        Map<String,String> map = new HashMap<String,String>();
        map = (Map<String,String>) yaml.load(YAML_DATA);

        return new Object[][] {map.values().toArray()};

    }

    @DataProvider(name="yamlFile")
    public Object[][] yamlFile() throws Exception
    {
        Yaml yaml = new Yaml();
        InputStream iStream = new FileInputStream(new File(YAML_FILE));
        Collection<Map<String,String>> userCollection = (Collection<Map<String,String>>) yaml.load(iStream);

        // Declare 2D array to return
        String [][] user2DArray = new String[userCollection.size()][2];

        // parse login into 2D array
        int i=0;
        for(Map<String,String> usersMap:userCollection) // ITERATE OVER COLLECTION OF USERS
        {
            Collection userValuesCollection = usersMap.values();
            for(Object userValue:userValuesCollection) // ITERATE OVER COLLECTION OF USERV ALUES
            {
                Map<String,String> userMap = (Map<String,String>) userValue;
                for(Map.Entry<String,String> credentials:userMap.entrySet()) // ITERATE OVER MAP OF EACH VALUE
                {
                    if(credentials.getKey().equals("username")) user2DArray[i][0] = credentials.getValue();
                    if(credentials.getKey().equals("password")) user2DArray[i][1] = credentials.getValue();
                }
            }
            i++;
        }
        return user2DArray;
    }


    @Test(dataProvider = "directString")
    void signIn(String user,String passwd)
    {
        driver.get("https://artsandculture.google.com/");
        driver.findElement(By.linkText("Sign in")).click();

        Set<String> tabs = driver.getWindowHandles();
        for(String tab: tabs)
        {
            driver.switchTo().window(tab);
            if(driver.getTitle().equals("Sign in - Google Accounts"))
            {
                break;
            }
        }

        driver.findElement(By.id("identifierId")).sendKeys(user);
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        driver.findElement(By.name("password")).sendKeys(passwd);
        driver.findElement(By.xpath("//span[text()='Next']")).click();

//        WebDriverWait wait = new WebDriverWait(driver,10);
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@title='Profile']/div/div"))));
//        Assert.assertTrue(driver.findElement(By.xpath("//a[@title='Profile']/div/div")).isDisplayed());


    }

    // Using Yaml file for Data Driven Testing

    @Test(dataProvider = "yamlFile")
    void signIn1(String user1,String passwd1)
    {
        driver.get("https://artsandculture.google.com/");
        driver.findElement(By.linkText("Sign in")).click();

        Set<String> tabs = driver.getWindowHandles();
        for (String tab : tabs) {
            driver.switchTo().window(tab);
            if (driver.getTitle().equals("Sign in - Google Accounts")) {
                break;
            }
        }

        driver.findElement(By.id("identifierId")).sendKeys(user1);
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        driver.findElement(By.name("password")).sendKeys(passwd1);
        driver.findElement(By.xpath("//span[text()='Next']")).click();
    }
}
