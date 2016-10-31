package com.common.config;

import com.yahoo.pages.ConfigReader;
//import junit.framework.Test;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

/**
 * Created by SAI on 8/13/2016.
 */
//TODO: Pull the parameter xml values and assign it to each variable

public class TestConfig {
    public String testurl;
    public String username;
    public String password;
    public String chromeDriverPath="D:/chromedriver.exe";
    public String ffDriverPath="D:/geckodriver.exe";
    public String iedriverpath = null;
    public String sysbrowser = "ff";

    public TestConfig() {
    }
}
