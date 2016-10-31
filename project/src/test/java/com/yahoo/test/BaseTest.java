package com.yahoo.test;

import com.common.config.TestConfig;
import com.yahoo.pages.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import javax.xml.xpath.XPathExpressionException;
import java.lang.reflect.Method;

/**
 * Created by SAI on 8/13/2016.
 */
public class BaseTest {

    protected static TestConfig config;
    protected static WebDriver webdriver;


    @BeforeSuite
    public void beforeSuite(){
        try {
            config =  new ConfigReader().getData(TestConfig.class,System.getProperty("config.xml"));
          webdriver = new DriverProvider(config).getDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @BeforeClass
    public void beforeClass(){
        System.setProperty("webdriver.gecko.driver","D:\\geckodriver.exe");
        webdriver = new DriverProvider(config).getDriver();

    }


    @AfterClass
    public void afterClass(){
        webdriver.close();
    }

    @BeforeMethod
    public void doSearch(Method m){
        //DriverProvider
        webdriver.get("http://www.yahoo.com");
    }

}
