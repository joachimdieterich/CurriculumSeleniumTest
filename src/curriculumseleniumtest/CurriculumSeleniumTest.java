/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curriculumseleniumtest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 *
 * @author joachimdieterich
 */
public class CurriculumSeleniumTest {

    /**
     * @param args the command line arguments
     */
    private static WebDriver driver = null;
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.setProperty("webdriver.chrome.driver", "selenium/webdriver/mac/chromedriver");
        driver = new ChromeDriver();
        login(driver);
        add_user(driver);
        logout(driver);
        
        /* System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        driver = new SafariDriver();
        login(driver);
        add_user(driver);
        logout(driver);
        
        System.setProperty("webdriver.gecko.driver", "selenium/webdriver/mac/geckodriver");
        driver = new FirefoxDriver();
        login(driver);
        user(driver);
        logout(driver);
        System.setProperty("webdriver.opera.driver", "selenium/webdriver/mac/operadriver");
        driver = new OperaDriver();
        login(driver);
        user(driver);
        logout(driver);*/
    }
    
    public static void login(WebDriver driver){
        driver.get("http://localhost/curriculum/public/index.php?action=login");
        driver.findElement(By.id("username")).sendKeys("admin");      
        driver.findElement(By.id("password")).sendKeys("Test123!");
        driver.findElement(By.id("login")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    
    public static void logout(WebDriver driver){
        driver.get("http://localhost/curriculum/public/index.php?action=logout");
        driver.quit();
    }
    
    public static void add_user(WebDriver driver) {
        driver.get("http://localhost/curriculum/public/index.php?action=user");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"user_btn_new\"]")).click();
        
        
        String uuid = UUID.randomUUID().toString();
        driver.findElement(By.id("usr")).sendKeys(uuid);
        driver.findElement(By.id("firstname")).sendKeys("First123");
        driver.findElement(By.id("lastname")).sendKeys("Last123");
        driver.findElement(By.id("email")).sendKeys("test123@curriculumonline.de");
        driver.findElement(By.id("pw")).sendKeys("Test1234!");
        
        driver.findElement(By.id("form_profile_btn_submit")).click();
        //search
        driver.findElement(By.id("q")).sendKeys(uuid+Keys.RETURN);
        // selecting new user in
        WebElement table = driver.findElement(By.xpath("//*[@id=\"container_userP\"]/div/div[3]/table"));   // find the customer table
        WebElement customer = table.findElement(By.xpath("//tr/td[contains(text(), '"+uuid+"')]"));           // find the row
        // click on the row
        customer.click();
        
        driver.findElement(By.xpath("//*[@id=\"nav_tab_delete\"]/a")).click();
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.id("btn_deleteUser")));    
    }
    
}
