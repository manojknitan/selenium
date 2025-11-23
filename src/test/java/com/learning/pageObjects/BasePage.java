package com.learning.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver webDriver)
    {
        this.driver=webDriver;
        PageFactory.initElements(driver,this);
    }
}
