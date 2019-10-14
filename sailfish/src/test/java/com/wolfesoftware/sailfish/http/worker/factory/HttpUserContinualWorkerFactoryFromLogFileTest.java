package com.wolfesoftware.sailfish.http.worker.factory;

import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HttpUserContinualWorkerFactoryFromLogFileTest {

    HttpUserContinualWorkerFactoryFromLogFile factory;

    @Mock
    LogFileReader logFileReader;

    @Before
    public void setup() throws IOException, BadLogFileException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnWorkerWithAllFourRequestsAndSetFinishedToFalse()
            throws Exception {
        List<String> requests = createArrayListOfRequests(4);
        Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
        factory = new HttpUserContinualWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
        factory.getWorker();
        assertEquals(true, factory.isThereAnyMoreWorkToDo());
    }

    @Test
    public void shouldReturnWorkerWithAllFourRequestsAndSetLoopBackToBeginningOfLogFile()
            throws Exception {
        List<String> requests = createArrayListOfRequests(4);
        Mockito.when(logFileReader.getAsListOfUrls()).thenReturn(requests);
        factory = new HttpUserContinualWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
        HttpUser httpUser = factory.getWorker();
        assertEquals("http://test0",httpUser.getRequest(0).getUri().toString());
        assertEquals("http://test1",httpUser.getRequest(1).getUri().toString());
        assertEquals("http://test2",httpUser.getRequest(2).getUri().toString());
        assertEquals("http://test3",httpUser.getRequest(3).getUri().toString());
        assertEquals(true, factory.isThereAnyMoreWorkToDo());

        httpUser = factory.getWorker();
        assertEquals("http://test0",httpUser.getRequest(0).getUri().toString());
        assertEquals("http://test1",httpUser.getRequest(1).getUri().toString());
        assertEquals("http://test2",httpUser.getRequest(2).getUri().toString());
        assertEquals("http://test3",httpUser.getRequest(3).getUri().toString());
        assertEquals(true, factory.isThereAnyMoreWorkToDo());
    }

    private List<String> createArrayListOfRequests(int numberOfRequests)
            throws IOException {
        List<String> requests = new ArrayList<String>();
        for (int x = 0; x < numberOfRequests;x++) {
            requests.add("http://test" + x);
        }
        return requests;
    }

}
