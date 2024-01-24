package com.sneo.bdd.stepdefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.sneo.bdd.pages.SamplePage;
import com.sneo.bdd.utilities.ElementHelper;
import com.sneo.bdd.utilities.TestDataUtil;
import com.sneo.bdd.utilities.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Log4j2
public class CommonStepDefinitions {

    private Scenario scenario;
    private WebDriver driver;
    private JsonNode testData;
    private ElementHelper elementHelper;
    private SamplePage samplePage;

    @Before
    public void setUp(Scenario scenario) {
        driver = WebDriverManager.getDriver();
        samplePage = new SamplePage(driver);
        elementHelper = new ElementHelper(driver);
        this.scenario = scenario;
        testData = TestDataUtil.getJsonNode(scenario);
        log.info("Scenario: " + scenario.getName()); // Log scenario name
    }

    @After
    public void quit() {
        WebDriverManager.quitDriver(scenario);
        TestDataUtil.removeDataNode();
        log.info("WebDriver quit.");
    }

    @And("Wait for {int} seconds")
    public void wait(int time) throws InterruptedException {
        Thread.sleep(time * 1000);
    }

    @And("^click on \"([^\"]*)\" menu$")
    public void clickOnLink(String menuName) throws InterruptedException {
        samplePage.clickOnLink(menuName);
    }

    @And("^verify the user redirect to the \"([^\"]*)\" page$")
    public void verifyRedirectedToAboutUsPage(String linkTextToVerify) throws InterruptedException {
        Assert.assertTrue(samplePage.verifyRedirectedToAboutUsPage(linkTextToVerify));
    }

    @And("^click on \"([^\"]*)\" menu with Data Driven$")
    public void clickOnLinkDataDriven(String menuName) throws InterruptedException {
        samplePage.clickOnLink(testData.get(menuName).asText());
    }

    @And("^verify the user redirect to the \"([^\"]*)\" page with Data Driven$")
    public void verifyRedirectedToAboutUsPageDataDriven(String linkTextToVerify) throws InterruptedException {
        Assert.assertTrue(samplePage.verifyRedirectedToAboutUsPage(testData.get(linkTextToVerify).asText()));
    }
}
