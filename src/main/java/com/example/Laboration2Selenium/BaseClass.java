package com.example.Laboration2Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.file.WatchEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BaseClass {


    public static WebDriver driver;


    private static ChromeOptions GetChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("start-maximized");
        options.addArguments("–no-sandbox");
       // options.addArguments("--incognito");

        // options.addArguments("headless");
        return options;
    }
    private static EdgeOptions GetEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("start-maximized");
        // options.addArguments("--incognito");
        // options.addArguments("headless");
        return options;
    }

    private  static  WebDriver GetChromeDriver(){

        driver = new ChromeDriver(GetChromeOptions() );
      // driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        return driver;
    }
    private  static  WebDriver GetFirefoxDriver(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        return driver;
    }
    private  static  WebDriver GetEdgeDriver(){
        driver = new EdgeDriver(GetEdgeOptions());
        driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        return driver;
    }


    /*
        public enum BrowserType{
            Firefox,
            //Safari,
            Chrome,
            Edge,
        }
        public  static void selectBrowserName(String browser)
        {
            switch (browser){
                case BrowserType.Chrome:
                    GetChromeDriver();
                    break;
                case BrowserType.Firefox:
                    GetFirefoxDriver();
                    break;
                case BrowserType.Edge:
                    GetEdgeDriver();
                    break;

                default:
                   throw new NoSuchContextException("Browser Not available");
            }

        }

     */
    public static void NavigatePage(String url){

        GetChromeDriver().get(url);

        //GetFirefoxDriver().get(url);  //för att använda firefox webbläsare

        // GetEdgeDriver().get(url);

    }
    /*
    public  static  void WaitPageLoad(int ms){
        try {
            driver.wait(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

     */
    public  static void CookiesAccept( String xp){

        WebElement element =  BaseClass.driver.findElement(By.xpath(xp));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xp)));
        element.click();

    }
    public  static void AcceptSvtCookies(){
            //It finds cookies element and accept cookies, and wait untill it remove from
        WebElement element =  BaseClass.driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/button[3]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/button[3]")));
        element.click();

        WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.className("sc-4f221cd2-1")));

    }



}



