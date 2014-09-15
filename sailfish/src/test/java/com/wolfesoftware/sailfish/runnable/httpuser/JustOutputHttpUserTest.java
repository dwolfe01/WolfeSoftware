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
	public void shouldMakeTwoRequestsAndOutputToStream()
			throws ClientProtocolException, IOException {
		httpUser = new JustOutputHttpUser(out);
		String request1 = "http://www.myurl.com";
		httpUser.add(request1);
		String request2 = "http://www.myurl2.com";
		httpUser.add(request2);
		// when
		httpUser.run();
		// then
		verify(out, times(1)).write(request1.getBytes());
		verify(out, times(1)).write(request2.getBytes());
	}

}
