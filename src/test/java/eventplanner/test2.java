package eventplanner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin= {"html: target/cucumber.html"},
		features="uses_cases",
		glue="eventplanner")
public class test2 {

}