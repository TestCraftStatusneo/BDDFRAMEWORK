# BDD Test Automation Framework with Sample Test
This project is a template BDD test automation framework, which provides structured and standard way of creating automated test scripts for GUI tests across projects. You can also enhance later as per your need.

This is a reusable automation framework that blends together Selenium WebDriver, Cucumber JVM (incorporating Gherkin and the BDD 'Given, When Then' testing construct).

The framework incorporates design principle of BDD (Behaviour driven development) which promotes writing acceptance tests by describing behaviour of application under test in simple english language from the perspective of its stakeholders. Having test written in Natural language helps the Project Team (Product Owners, Business Analysts, Development and QA team) to understand and track the requirements/

Supports Custom Page Object model which represents the screens of AUT as a series of objects and encapsulates the fields represented by a page which ultimately avoids duplication and improves code maintainability and readability.

## Tools & Libraries
The test automation framework comprises following tools and libraries currently, can be added/updated as per own need:
* Cucumber-JVM: BDD Framework
* Custom Page Object Pattern and utility functions
* Selenium WebDriver: Browser automation framework
* JAVA: Programming language
* TestNg: TestNg Java testing framework
* Maven: Build tool
* Lombok: Java utility api
* Intellij Or Eclipse: Integrated Development Environment

## Machine Configuration
Configure Ubuntu / Windows and setup:
* Java 8
* Git / SVN
* Maven
* Intellij or Eclipse

## Compile, Build and Run Tests
* Execution through Runner file:
  * For parallel execution, goto the ParallelTestRunner.java file and Run it.
  * For sequential execution, goto the SequentialRunner.java file and Run it.
    * Sample runner file:
      ```
      @CucumberOptions(
        features = "src/test/java/features/SampleTest.feature",
        glue = "com/sneo/bdd/stepdefinitions",
        plugin = { "pretty", "html:target/cucumber-reports/cucumber.html","json:target/cucumber-reports/Cucumber.json","junit:target/cucumber-reports/Cucumber.xml" },
        monochrome = false
      )
      public class SequentialRunner extends AbstractTestNGCucumberTests {}
      ```
* Execution through Maven:
  * Under pom.xml file, created 2 profiles: 
    * Parallel-cucumber-runner: For parallel execution. 
    * Sequential-cucumber-runner: For sequential execution.
    * Command to execute profile: mvn clean verify -P <Your Profile Name>
      * E.g.: 
      ```  
      mvn clean verify -P Sequential-cucumber-runner
      ```
## Reports
* Cucumber provides multiple formats to produce reports and configure in Runner files. Currently following reports are configured:
  * HTML Report: target/cucumber-reports/cucumber.html
  * XML Report: target/cucumber-reports/Cucumber.xml
  * JSON Report: target/cucumber-reports/Cucumber.json
 
    For Clarification conatct "nikunjkumar.gohil@statusneo.com"

