package com.wolfesoftware.sailfish.http.responsehandler.factory;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.responsehandler.SaveToFileResponseHandler;

public class SaveToFileResponseHandlerTest {

	@Mock
	HttpResponse response;
	@Mock
	StringEntity stringEntity;
	@Mock
	InputStream inputStream;
	@Mock
	ResponseHandlerFactory responseHandlerFactory;
	
	File directory = new File("/tmp/scratch/");

	@Before
	public void before() throws IOException {
		MockitoAnnotations.initMocks(this);
		when(response.getEntity()).thenReturn(stringEntity);
		directory.mkdir();
		FileUtils.cleanDirectory(directory);
	}

	@Test
	public void shouldSaveHTMLResultsToFile() throws Exception {
		SaveToFileResponseHandler responseHandler = new SaveToFileResponseHandler(directory);
		responseHandler.handleResponse(response);
		assertTrue(directory.list().length==1);
		assertTrue(directory.listFiles()[0].exists());
		assertTrue(directory.listFiles()[0].getName().equals("file0.html"));
	}
}
