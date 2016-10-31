package com.yahoo.pages;


import com.common.config.TestConfig;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ConfigReader {

    public ConfigReader() {
    }

    public <T> T getData(Class<T> classToFill, String xmlParameterfile) throws IllegalAccessException, InstantiationException, XPathExpressionException, ParserConfigurationException, IOException, SAXException {
            Field[] allFields = classToFill.getDeclaredFields();
            Object obj = classToFill.newInstance();

             File fXmlFile = new File(xmlParameterfile);
             DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
              DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
              Document doc = dBuilder.parse(fXmlFile);
               doc.getDocumentElement().normalize();
            for (Field field : allFields) {

                if(field.getName().startsWith("sys")){
                    String fieldName=field.getName().replaceFirst("sys","");
                    if(System.getProperty(fieldName)!= null && System.getProperty(fieldName).length()>0)
                        field.set(obj,System.getProperty(fieldName));
                }else {
                    XPathFactory factory1 = XPathFactory.newInstance();
                    XPath path = factory1.newXPath();
                    XPathExpression expression = path.compile("//entry[@key='" + field.getName() + "']");
                    Object result = expression.evaluate(doc, XPathConstants.NODESET);

                    NodeList nodes = (NodeList) result;
                    field.set(obj, nodes.item(0).getTextContent());
                }
            }

        return (T) obj;
    }

    public static void main(String[] args) throws IllegalAccessException, XPathExpressionException, InstantiationException, IOException, SAXException, ParserConfigurationException {
        ConfigReader c=new ConfigReader();
        c.getData(TestConfig.class,"src/test/resources/qASeleniumParameters.xml");
    }
}
