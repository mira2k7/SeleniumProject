package com.yahoo.test;

import com.yahoo.pages.SearchDataObject;
import com.yahoo.pages.SheetReader;
import com.yahoo.test.BaseTest;
//import com.yahoo.test.CapitalService;
//import com.yahoo.test.CapitalServiceJasonPath;
//import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SAI on 9/9/2016.
 */
public class AllInboxTestCases extends BaseTest {
   @DataProvider(name = "yahooKeywordsFromExcel")
   public Iterator<Object[]> createData(Method methodObj) throws Exception {
      SheetReader sReader = new SheetReader("C:\\Users\\vasanth\\workspace\\project\\src\\test\\resources\\data1.xls", "homePage");
      Collection<Object[]> col = new ArrayList<Object[]>();
      List<SearchDataObject> results=new ArrayList();
       try {
           results = sReader.getData(SearchDataObject.class, methodObj.getName());
       }catch(Exception e){
           e.printStackTrace();
       }
      for(SearchDataObject result : results){
          col.add(new Object[]{result});
      }
      return col.iterator();
    }
   
    @Test(dataProvider = "yahooKeywordsFromExcel")
    public void testVerifySearchKeywordsAndUrl(SearchDataObject dataHolder){
        System.out.println("data:");
        webdriver.findElement(By.id("uh-search-box")).click();
        System.out.println("data:"+dataHolder.searchKeyword);
        webdriver.findElement(By.id("uh-search-box")).sendKeys(dataHolder.searchKeyword+ Keys.ENTER);
        new WebDriverWait(webdriver,5000).until(ExpectedConditions.invisibilityOfElementLocated(By.id("uh-search-box")));
        Assert.assertTrue(webdriver.getCurrentUrl().startsWith("https://search"));
        new WebDriverWait(webdriver,5000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sbq-wrap']/input")));
        Assert.assertEquals(webdriver.findElement(By.xpath("//div[@id='sbq-wrap']/input")).getAttribute("value"),dataHolder.searchKeyword);

    }
    
}

