package com.wolfesoftware.sailfish.concurrency.worker.factory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.requests.GetRequest;
import com.wolfesoftware.sailfish.runnable.httpuser.UptimeHttpUser;
import com.wolfesoftware.sailfish.runnable.httpuser.UptimeHttpUserFactory;

/*
 * This class reads takes a list of URLS. Each URL is included for each session. The status codes are maintained and output after each request to give a sense of service availability AND uptime
 */
public class UptimeHttpUserWorkerFactoryFromLogFileTest {

	UptimeHttpUserWorkerFactoryFromLogFile factory = new UptimeHttpUserWorkerFactoryFromLogFile(); 
	@Mock
	UptimeHttpUserFactory uptimeHttpUserFactory;
	@Mock
	LogFileReader logFileReader;
	@Mock
	UptimeHttpUser uptimeHttpUser;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(uptimeHttpUserFactory.getHttpUser()).thenReturn(uptimeHttpUser);
		factory = new UptimeHttpUserWorkerFactoryFromLogFile(uptimeHttpUserFactory); 
	}

	@Test
	public void shouldReturnWorkerWithAllRequestsFinishedIsAlwaysFalse()
			throws Exception {
		List<String> requests = createArrayListOfRequests(3);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory.setUrls(logFileReader);
		factory.getWorker();
		verify(uptimeHttpUser, times(3)).addGetRequest(isA(GetRequest.class));
	}

	@Test
	public void shouldAlwaysHaveMoreWorkToDo()
			throws Exception {
		List<String> requests = createArrayListOfRequests(5);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory.setUrls(logFileReader);
		factory.getWorker();
		assertEquals(true, factory.isThereAnyMoreWorkToDo());
		factory.getWorker();
		assertEquals(true, factory.isThereAnyMoreWorkToDo());
		factory.getWorker();
		assertEquals(true, factory.isThereAnyMoreWorkToDo());
	}

	private List<String> createArrayListOfRequests(int numberOfRequests)
			throws IOException {
		List<String> requests = new ArrayList<String>();
		for (int x = 0; x < numberOfRequests; x++) {
			requests.add("http://test");
		}
		return requests;
	}

}
