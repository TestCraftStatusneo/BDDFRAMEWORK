package com.sneo.bdd.utilities;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private WebDriverManager() {
        throw new IllegalStateException("WebDriverManager class cannot be instantiated.");
    }

    public static String remote_url_chrome = "http://localhost:4444";

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {


            if(CommonUtils.getProperty("docker").equals("true")){
                ChromeOptions options = new ChromeOptions();
                options.setAcceptInsecureCerts(true);
                System.setProperty("java.awt.headless", "false");
                try {
                    driver=new RemoteWebDriver(new URL(remote_url_chrome), options);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }else {
                ChromeOptions chromeOptions = new ChromeOptions();
                io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
            }
            driverThreadLocal.set(driver);
            launchURL(driver);
        }
        return driver;
    }


    public static void launchURL(WebDriver driver){
        System.out.println("####################################");
        System.out.println(CommonUtils.getProperty("dev_ui_env"));
        System.out.println("####################################");
        driver.get(CommonUtils.getProperty("dev_ui_env"));
        driver.manage().window().maximize();
    }

    public static void quitDriver(Scenario scenario) {
        WebDriver driver = driverThreadLocal.get();
        try {
            if (scenario.isFailed()) {

                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the WebDriver instance
            if (driver != null) {
                driver.quit();
                driverThreadLocal.remove();
            }
        }
    }
}
