package com.wolfesoftware.gds.endtoend;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/features/GoogleDriveSharer.feature"},
                  glue={"com.wolfesoftware.gds.step.definitions"})
public class GoogleDriveSharerTest {

}