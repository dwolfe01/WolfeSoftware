package com.wolfesoftware

import cucumber.api.groovy.EN
import cucumber.api.groovy.Hooks
import static org.junit.Assert.assertEquals

this.metaClass.mixin(Hooks)
this.metaClass.mixin(EN)

def cucumberSalesman

World{
	cucumberSalesman = new CucumberSalesman()
}

Given(~'I start with (\\d+) cucumbers'){int cucumbers ->
	cucumberSalesman.setCucumbers(cucumbers)
}

When(~'I sell (\\d+) cucumbers'){int cucumbers ->
	cucumberSalesman.sellCucumbers(cucumbers)
}

Then(~'I have (\\d+) cucumbers'){int cucumbers ->
	assertEquals(cucumberSalesman.getCucumbers(), cucumbers)
}



