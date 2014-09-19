package com.wolfesoftware.sailfish.runnable.httpuser;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.requests.GetRequest;

public class JustOutputHttpUserTest {

	JustOutputHttpUser httpUser;

	@Mock
	OutputStream out;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	// due to difficulties around matchers and generic classes
	public void shouldMakeTwoRequestsAndOutputToStream() throws ClientProtocolException, IOException {
		httpUser = new JustOutputHttpUser(out);
		String req = "http://www.myurl.com";
		GetRequest request1 = new GetRequest(req);
		GetRequest request2 = new GetRequest(req);
		httpUser.addGetRequest(request1).addGetRequest(request2);
		// when
		httpUser.run();
		// then
		verify(out, times(2)).write(req.getBytes());
	}
}
