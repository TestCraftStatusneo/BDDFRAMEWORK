package com.sneo.bdd.utilities;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Log4j2
public class ElementHelper {
    private WebDriver driver;
    private WebDriverWait wait;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 60);
    }

    // Find a clickable element using XPath
    public WebElement findElement(String xpath) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    // Wait for the invisibility of an element using XPath
    public Boolean findElementInvisibilityOf(String xpath) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
    }

    // Find a visible element using XPath
    public WebElement findElementByVisible(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    // Find a list of elements using a locator
    public List<WebElement> findElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // Scroll to view an element using JavaScript
    public void scrollIntoView(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Find a list of elements using XPath
    public List<WebElement> findElements(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    // Click an element using XPath
    public void click(String xpath) {
        WebElement element = findElement(xpath);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        // Log the click action
        log.info("Clicked element with XPath: " + xpath);
    }

    // Type text into an element after clearing it
    public void typeKeys(String xpath, String text) {
        WebElement element = findElement(xpath);
        clearField(element);
        element.sendKeys(text);
        // Log the type keys action
        log.info("Typed '" + text + "' into element with XPath: " + xpath);
    }

    // Type text into an element without clearing it
    public void typeKeysWithoutClear(String xpath, String text) {
        WebElement element = findElement(xpath);
        element.sendKeys(text);
        // Log the type keys action without clearing
        log.info("Typed '" + text + "' into element with XPath: " + xpath + " (without clearing)");
    }

    // Type text into an element using a WebElement
    public void typeKeys(WebElement element, String text) throws AWTException {
        clearField(element);
        element.sendKeys(text);
        // Log the type keys action using WebElement
        log.info("Typed '" + text + "' into element: " + element.toString());
    }

    // Clear a field using its XPath
    public void clearField(String xpath) throws AWTException {
        WebElement element = findElement(xpath);
        element.click();
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        element.sendKeys(del);
        element.click();
        // Log the clear field action
        log.info("Cleared field with XPath: " + xpath);
    }

    // Clear a field using a WebElement
    public void clearField(WebElement element) {
        element.click();
        String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        element.sendKeys(del);
        element.click();
        // Log the clear field action using WebElement
        log.info("Cleared field: " + element.toString());
    }

    // Get the current selected value of a dropdown using its XPath
    public String getDropDownValue(String xpath) {
        WebElement element = findElement(xpath);
        String selectedValue = element.getAttribute("innerText");
        // Log the get dropdown value action
        log.info("Got dropdown selected value: " + selectedValue + " for element with XPath: " + xpath);
        return selectedValue;
    }

    // Get all dropdown values as a list using XPath without clicking
    public List<String> getAllDropDownValuesWithoutClick(String xpath) {
        List<String> output = new ArrayList<>();
        List<WebElement> elementList;
        try {
            elementList = findElements(By.xpath("//ul//li"));
            for (WebElement option : elementList) {
                output.add(option.getAttribute("data-value"));
            }
        } catch (Exception e) {
            elementList = findElements(By.xpath("//*[contains(@id,'-option-')]"));
            for (WebElement option : elementList) {
                output.add(option.getText());
            }
        }
        // Log the get all dropdown values action without clicking
        log.info("Got all dropdown values without clicking for element with XPath: " + xpath);
        return output;
    }

    // Get all dropdown values as a list using a WebElement
    public List<String> getAllDropDownValues(WebElement element) {
        List<String> output = new ArrayList<>();
        tapEsc();
        element.click();
        List<WebElement> elementList;
        try {
            elementList = findElements(By.xpath("//ul//li"));
            for (WebElement option : elementList) {
                output.add(option.getAttribute("data-value"));
            }
        } catch (Exception e) {
            elementList = findElements(By.xpath("//*[contains(@id,'-option-')]"));
            for (WebElement option : elementList) {
                output.add(option.getText());
            }
        }
        tapEsc();
        // Log the get all dropdown values action
        log.info("Got all dropdown values for WebElement: " + element.toString());
        return output;
    }

    // Wait for an element to be visible using XPath
    public void waitForElementVisible(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        // Log the wait for element visibility action
        log.info("Waited for element visibility with XPath: " + xpath);
    }

    // Wait for an element to be clickable using XPath
    public void waitForElementClickable(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        // Log the wait for element to be clickable action
        log.info("Waited for element to be clickable with XPath: " + xpath);
    }

    // Wait for text to be present in an element using XPath
    public void waitForTextPresent(String xpath, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath), text));
        // Log the wait for text present action
        log.info("Waited for text '" + text + "' to be present in element with XPath: " + xpath);
    }

    // Wait for the page title to contain a specific text
    public void waitForTitleContains(String title) {
        wait.until(ExpectedConditions.titleContains(title));
        // Log the wait for title contains action
        log.info("Waited for title to contain: " + title);
    }

    // Wait for the page to load completely
    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }

        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
        wait.until(jQueryLoad);
        wait.until(jsLoad);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        // Log the wait for page to load action
        log.info("Waited for the page to load completely");
    }

    // Simulate pressing the ESC key
    public void tapEsc() {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
        // Log the press ESC key action
        log.info("Pressed the ESC key");
    }

    // Simulate pressing the TAB key
    public void keyTab() {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB).perform();
        // Log the press TAB key action
        log.info("Pressed the TAB key");
    }

    // Simulate pressing the BACK_SPACE key
    public void keyBACK_SPACE() {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.BACK_SPACE).perform();
        // Log the press BACK_SPACE key action
        log.info("Pressed the BACK_SPACE key");
    }

    // Press the TAB key using Robot
    public void presTab() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        // Log the press TAB key action using Robot
        log.info("Pressed the TAB key using Robot");
    }

    // Simulate pressing the ENTER key
    public void tapEnter() {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
        // Log the press ENTER key action
        log.info("Pressed the ENTER key");
    }

    // Refresh the current page
    public void refreshPage() {
        driver.navigate().refresh();
        // Log the refresh page action
        log.info("Refreshed the current page");
    }

    // Get the current date with an offset of days (previous, next, or current)
    public String getDateCurrentMinusOrPlus(int noOfDays, String previousOrNext) {
        LocalDate currentDate = LocalDate.now();
        // Subtract or add days to the current date based on the provided input
        LocalDate date = null;
        if (previousOrNext.toLowerCase().equals("previous")) {
            date = currentDate.minusDays(noOfDays);
        }
        if (previousOrNext.toLowerCase().equals("next")) {
            date = currentDate.plusDays(noOfDays);
        }
        if (previousOrNext.toLowerCase().equals("current")) {
            date = currentDate;
        }
        // Format the date as "yyyy-MM-dd"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        System.out.println("Previous Date: " + formattedDate);
        // Log the get date action
        log.info("Got date with offset " + noOfDays + " days (" + previousOrNext + "): " + formattedDate);
        return formattedDate;
    }

    // Scroll to the top of the page
    public void scrollToTop() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PAGE_UP);
        robot.keyRelease(KeyEvent.VK_PAGE_UP);
        Thread.sleep(200);
        // Log the scroll to top action
        log.info("Scrolled to the top of the page");
    }

    // Navigate to the top of the page using Robot
    public void navigateTop() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PAGE_UP);
        robot.keyRelease(KeyEvent.VK_PAGE_UP);
        log.info("Navigated to the top of the page using Robot");
    }

    public void navigateDown() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
        log.info("Navigated to the down of the page using Robot");
    }

    // Generate a random string of a given length
    public String generateRandomString(int length) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        log.info("Generated random string with length " + length + ": " + randomString);
        return randomString;
    }

    // Perform a mouse hover action on an element
    public void mouseHover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        log.info("Performed mouse hover on element: " + element.toString());
    }

    // Check if a list of strings is in ascending order
    public static boolean isAscending(List<String> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).compareTo(list.get(i - 1)) < 0) {
                System.out.println("Difference found: " + list.get(i - 1) + " > " + list.get(i));
                return false;
            }
        }
        System.out.println("Checked if the list is in ascending order");
        return true;
    }

    // Check if a list of strings is in descending order
    public static boolean isDescending(List<String> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).compareTo(list.get(i - 1)) > 0) {
                return false;
            }
        }
        log.info("Checked if the list is in descending order");
        return true;
    }

    public void rightClick(WebElement element) {
        Actions actions = new Actions(driver);
        // Perform a right-click on the element
        actions.contextClick(element).build().perform();
    }


    public static String getTodaysDate() {
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Get today's date
        LocalDate today = LocalDate.now();

        // Format the date to the desired format
        String formattedDate = today.format(formatter);

        System.out.println("Today's date in the 'YYYY-MM-DD' format: " + formattedDate);
        return formattedDate;
    }

}


