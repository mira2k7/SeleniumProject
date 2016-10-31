package com.yahoo.pages;

//import com.d.yahoo.test.CapitalService;
//import com.demo.test.yahoo.test.CapitalServiceJasonPath;

public class SearchDataObject {

    public String searchKeyword;
    public String verifyKeyword;
    public String  urlPattern;

    public SearchDataObject()
    {

    }
    
    public SearchDataObject(String searchKeyword){
        this.searchKeyword = searchKeyword;
        this.urlPattern="https://search";
        this.verifyKeyword=searchKeyword;
    }
}
