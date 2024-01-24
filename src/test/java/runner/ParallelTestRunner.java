package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions(
        features = "src/test/java/features/SampleTest.feature",
        glue = "com/sneo/bdd/stepdefinitions",
        plugin = { "pretty", "html:target/cucumber-reports/cucumber.html","json:target/cucumber-reports/Cucumber.json","junit:target/cucumber-reports/Cucumber.xml" },
        monochrome = false
        // Additional configurations specific to parallel execution
)
public class ParallelTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
