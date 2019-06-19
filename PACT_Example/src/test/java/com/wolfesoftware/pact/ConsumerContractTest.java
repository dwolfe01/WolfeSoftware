package com.wolfesoftware.pact;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class ConsumerContractTest {

	@Test
	@PactVerification
	public void shouldGetUser() throws IOException {
		String result = new Consumer("http://localhost:8080").getUser("1");
		String[] split = result.split(",");
		assertEquals(split[0],"DAN");
		assertEquals(split[1],"WOLFE");
	}
	
	/***** PACT contracts *****/
	
	@Rule
	public PactProviderRuleMk2 mockProvider
	  = new PactProviderRuleMk2("user_provider", "localhost", 8080, this);
	
	@Pact(consumer = "Consumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
	    return builder
	      .given("test GET")
	        .uponReceiving("GET REQUEST")
	        .path("/getUser")
	        .method("GET")
	      .willRespondWith()
	        .status(200)
	        .body("dan,wolfe").toPact();
	}

}