package com.sneo.bdd.pages;

import com.sneo.bdd.utilities.ElementHelper;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The purpose of this class is to manage different actions of Sample Page.
 * For example, Redirect to Specific menu, Verify redirection, etc.
 */
@Log4j2
public class SamplePage {
    private WebDriver driver;
    private ElementHelper elementHelper;

    /**
     * Constructor to assign driver.
     *
     * @param driver
     */
    public SamplePage(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
    }

    public void clickOnLink(String menuName) {
        elementHelper.click(String.format("//div[@id='headerPanel']//a[text()='%s']",menuName));
    }

    public boolean verifyRedirectedToAboutUsPage(String linkTextToVerify) {
        String aboutUsLink = driver.getCurrentUrl();
        log.info("About Us link : " + aboutUsLink);
        return aboutUsLink.contains(linkTextToVerify);
    }
}
