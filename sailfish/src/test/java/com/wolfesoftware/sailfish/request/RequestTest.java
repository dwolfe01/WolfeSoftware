package com.wolfesoftware.sailfish.request;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.wolfesoftware.sailfish.worker.httpuser.CookieTin;

/*
 * This is an integration test and requires a simple apache running on localhost:80
 */
public class RequestTest {

	// @Test
	// public void shouldHttpRequest() throws Exception {
	// Request req = new Request("http://localhost:80");
	// String responseCode = req.go();
	// assertEquals("200", responseCode);
	// }
	//
	// @Test
	// public void shouldChangeListOfCookies() throws Exception {
	// CookieTin cookieTin = new CookieTin();
	// Request req = new Request("http://www.google.co.uk:80", cookieTin);
	// String responseCode = req.go();
	// assertEquals("200", responseCode);
	// assertEquals(true, cookieTin.size() >= 1);
	// }

	// never have a unit test dependent on a third party!!!
	@Test
	public void shouldSendBackToServerListOfCookies() throws Exception {
		CookieTin cookieTin = new CookieTin();
		Request req = new Request("http://www.google.co.uk", cookieTin);
		Request secondReq = new Request("http://www.google.co.uk", cookieTin);
		String responseCode = req.go();
		String secondResponseCode = secondReq.go();
		secondReq.go();
		assertEquals("200", responseCode);
		assertEquals("200", secondResponseCode);
		assertEquals(true, cookieTin.size() >= 1);
	}

	@Test(expected = IOException.class)
	public void shouldValidateHttpRequest() throws Exception {
		new Request("htt/www.validrequestcouk");
	}

}
