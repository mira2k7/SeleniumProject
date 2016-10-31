package com.yahoo.test;

import com.common.config.TestConfig;
//import junit.framework.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverProvider extends BaseTest {
    private TestConfig config;

    public DriverProvider(TestConfig config){
        this.config = config;
  }


    public WebDriver getDriver(){
        System.out.println("Ttst: "+config.sysbrowser);
        WebDriver driver= null;
        if(config.sysbrowser.toLowerCase().equals("ie")) {
            System.setProperty("WebDriver", config.iedriverpath);
            driver=new InternetExplorerDriver();
        }
        else if (config.sysbrowser.toLowerCase().equals("ff")) {
            System.setProperty("webdriver.gecko.driver", config.ffDriverPath);
            driver=new FirefoxDriver();
        }
        else if (config.sysbrowser.toLowerCase().equals("chrome")){
            System.setProperty("WebDriver", config.chromeDriverPath);
            driver=new ChromeDriver();
        }
        return driver;
        }

    }

