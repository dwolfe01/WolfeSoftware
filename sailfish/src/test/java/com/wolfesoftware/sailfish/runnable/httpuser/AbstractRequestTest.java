package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import com.wolfesoftware.sailfish.requests.AbstractRequest;

public class AbstractRequestTest {
	
	class TestAbstractSimpleRequestTest extends AbstractRequest{

		public TestAbstractSimpleRequestTest(String uri) throws URISyntaxException  {
			super(uri);
		}

		@Override
		public StatusLine makeRequest(HttpClient httpClient) throws ClientProtocolException, IOException {
			// TODO Auto-generated method stub
			return null;
		}};
	
	@Test(expected = URISyntaxException.class)
	public void shouldThrowAnExceptionIfTheRequestIsNotAURL() throws Exception {
		new TestAbstractSimpleRequestTest("bad request");
	}

}
