package com.wolfesoftware.sailfish.http.worker.factory;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromLogFile;

public class HttpUserWorkerFactoryFromLogFileTest {

	HttpUserWorkerFactoryFromLogFile factory;
	
	@Mock
	LogFileReader logFileReader;

	@Before
	public void setup() throws IOException, BadLogFileException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnWorkerWithAllFourRequestsAndSetFinishedToTrue()
			throws Exception {
		List<String> requests = createArrayListOfRequests(4);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory = new HttpUserWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
		factory.getWorker();
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
	}

	@Test
	public void shouldReturnWorkerWithAllFourRequestsAndNOTSetFinishedToTrue()
			throws Exception {
		List<String> requests = createArrayListOfRequests(5);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory = new HttpUserWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
		factory.getWorker();
		assertEquals(true, factory.isThereAnyMoreWorkToDo());
	}

	@Test
	public void shouldReturnTwoWorkersWithSevenRequestsAndSetFinishedToTrue()
			throws Exception {
		List<String> requests = createArrayListOfRequests(7);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory = new HttpUserWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
		factory.getWorker();
		factory.getWorker();
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
	}

	@Test
	public void shouldReturnOneWorkerWithAllRequestsAndCallToGetWorkerShouldNotFailAndSetFinishedToTrue()
			throws Exception {
		List<String> requests = createArrayListOfRequests(4);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory = new HttpUserWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
		factory.getWorker();
		factory.getWorker();
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
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
