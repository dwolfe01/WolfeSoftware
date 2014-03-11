package com.wolfesoftware.sailfish;
import java.io.IOException;

import org.junit.Test;

import com.wolfesoftware.sailfish.request.Request;

public class RequestTest {
	
	@Test
	public void shouldHttpRequest() throws Exception {
		Request req = new Request("http://localhost:80");
		req.go();
	}
	
	@Test(expected=IOException.class)
	public void shouldValidateHttpRequest() throws Exception {
		new Request("htt/www.validrequestcouk");
	}

}
