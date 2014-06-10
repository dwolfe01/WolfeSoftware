package com.wolfesoftware.sailfish.concurrency.worker.factory;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.AnyOf;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.MockitoAnnotations;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.LoggingLevel;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.httpuser.TestLogWriter;

public class AdvancedHttpSessionWorkerFactoryTest {

	AdvancedHttpSessionWorkerFactory factory = new AdvancedHttpSessionWorkerFactory();

	@Mock
	LogFileReader logFileReader;
	@Mock
	TestLogWriter logWriter;
	String logFileRequest = "http://localhost";

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnWorkerWithAllFourRequestsAndSetFinishedToTrue()
			throws Exception {
		List<Request> requests = createArrayListOfRequests(4);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory.setUrls(logFileReader);
		factory.getWorker();
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
	}

	@Test
	public void shouldReturnWorkerWithAllFourRequestsAndNOTSetFinishedToTrue()
			throws Exception {
		List<Request> requests = createArrayListOfRequests(5);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory.setUrls(logFileReader);
		factory.getWorker();
		assertEquals(true, factory.isThereAnyMoreWorkToDo());
	}

	@Test
	public void shouldReturnTwoWorkersWithSevenRequestsAndOneDoNothingRequestAndSetFinishedToTrue()
			throws Exception {
		List<Request> requests = createArrayListOfRequests(7);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory.setUrls(logFileReader);
		factory.getWorker();
		factory.getWorker();
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
	}
	
	@Test
	@Ignore 
	//for the life of me I cannot get anyOf to work aaaaah.
	public void shouldLogInfoEvery1000Request() throws Exception{
		List<Request> requests = createArrayListOfRequests(2010);
		Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
		factory.setUrls(logFileReader);
		Configurator.defaultConfig().writer(logWriter).activate();
		while(factory.isThereAnyMoreWorkToDo()){
			factory.getWorker();
		}
		Mockito.verify(logWriter, Mockito.times(2)).write(LoggingLevel.INFO,"");
	}

	private List<Request> createArrayListOfRequests(int numberOfRequests)
			throws IOException {
		List<Request> requests = new ArrayList<Request>();
		for (int x = 0; x < numberOfRequests; x++) {
			requests.add(new Request("http://test"));
		}
		return requests;
	}

}
