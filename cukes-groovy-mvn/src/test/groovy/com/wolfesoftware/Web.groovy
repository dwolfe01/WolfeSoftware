package com.wolfesoftware

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import static org.junit.Assert.assertEquals
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

Given(~'I go to (.*)'){ String url -> 
	HtmlUnitDriver driver = new HtmlUnitDriver();
	driver.get(url)
	new File('target/test.htm').write(driver.getPageSource())
}

