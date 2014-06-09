package com.wolfesoftware.sailfish.worker.httpuser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.LoggingLevel;
import org.pmw.tinylog.writers.LoggingWriter;

import com.wolfesoftware.sailfish.request.Request;

public class HttpUserTest {

	private static final String HTTP_REFERER_COM = "http://referer.com";

	HttpUser httpUser;

	@Mock
	Request request;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		httpUser = new HttpUser();
		Mockito.when(request.go()).thenReturn("200");
		Mockito.when(request.getUrl()).thenReturn(HTTP_REFERER_COM);
	}

	@Test
	public void shouldEstablishASessionWithTheServer() throws Exception {
		httpUser.establishSession(request).go();
		Mockito.verify(request, Mockito.times(1)).go();
	}

	@Test
	public void shouldExecuteRequestsOneAfterAnother() throws Exception {
		httpUser.add(request).add(request);
		httpUser.go();
		Mockito.verify(request, Mockito.times(2)).go();
	}

	@Test
	public void shouldMaintainRefererAsBeingTheRequestThatGeneratedTheLast200Response()
			throws Exception {
		httpUser.add(request).add(request);
		httpUser.go();
		assertEquals(HTTP_REFERER_COM, httpUser.getReferer());
		Mockito.verify(request, Mockito.times(1)).setReferer(HTTP_REFERER_COM);
	}

	/*
	 * I couldn't really work out how to test Thread.sleep was being called.
	 * This is a bad test.
	 */
	@Test
	public void shouldExecuteRequestsOneAfterAnotherWithWaitTime()
			throws Exception {
		long startTime = System.currentTimeMillis();
		long sleepTime = 50;
		httpUser.setWaitTimeInMilliseconds(sleepTime).add(request).add(request)
				.go();
		Mockito.verify(request, Mockito.times(2)).go();
		long completionTime = System.currentTimeMillis() - startTime;
		assertEquals("Not pausing for long enough", true, completionTime > 100);
	}

	/*
	 * I couldn't really work out how to test Thread.sleep was being called.
	 * This is a bad test.
	 */
	@Test
	public void shouldOnlyLogIfTimeToProcessIsBiggerThanLoggingThreshold()
			throws Exception {
		long sleepTime = 0;
		httpUser.setLoggingThreshold(1000);
		TestLogWriter logWriter = new TestLogWriter();
		Configurator.defaultConfig().writer(logWriter).activate();
		httpUser.setWaitTimeInMilliseconds(sleepTime).add(request).add(request)
				.go();
		Mockito.verify(request, Mockito.times(2)).go();
		assertEquals("", logWriter.getMessage());
	}

	/*
	 * I couldn't really work out how to test Thread.sleep was being called.
	 * This is a bad test.
	 */
	@Test
	public void shouldLogIfTimeToProcessIsBiggerThanLoggingThreshold()
			throws Exception {
		long sleepTime = 50;
		httpUser.setLoggingThreshold(50);
		TestLogWriter logWriter = new TestLogWriter();
		Configurator.defaultConfig().writer(logWriter).activate();
		httpUser.setWaitTimeInMilliseconds(sleepTime).add(request).add(request)
				.go();
		Mockito.verify(request, Mockito.times(2)).go();
		assertEquals(true, logWriter.getMessage().length() > 1);
	}
}

class TestLogWriter implements LoggingWriter {

	private String message = "";

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(LoggingLevel arg0, String arg1) {
		setMessage(arg1);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
