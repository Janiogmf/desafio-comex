package config;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@gestao_emprestimo_livro"},
		         features = "src/test/java/features", 
                 plugin = {"pretty" }, 
                 glue = { "steps_definitions", "config.hooks"},
                 monochrome = true)
public class runner {

}
