package com.wolfesoftware;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/features"},
                  glue={"src/test/groovy/com/wolfesoftware/"},
                  format={"pretty",
                          "json:target/results.json",
                          "html:target/cucumber",
                          "junit:target/cucumber/junit.xml"})
public class TestConfig {

}
