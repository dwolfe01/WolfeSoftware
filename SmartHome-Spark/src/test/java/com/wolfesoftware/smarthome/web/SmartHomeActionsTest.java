package com.wolfesoftware.smarthome.web;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.wolfesoftware.smarthome.action.SmartHomeActions;

public class SmartHomeActionsTest {
	
	@Mock
	HttpClient httpClient;
	@Mock
	HttpResponse httpResponse;
	@Mock
	HttpEntity httpEntity;
	@Mock
	InputStream is;
	
	
	@Before
	public void init(){
		initMocks(this);
	}
	
	@Test
	public void shouldTransformWeatherReportFromBBC() throws Exception {
		when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(httpResponse);
		when(httpResponse.getEntity()).thenReturn(httpEntity);
		ByteArrayInputStream byteArray = getXMLFileAsByteArray();
		when(httpEntity.getContent()).thenReturn(byteArray);
		SmartHomeActions smartHomeActions = new SmartHomeActions(httpClient);
		String result = smartHomeActions.getWeather();
		assertTrue(result.contains("<div class=\"weather\">"));
	}
	
	@Test
	public void shouldPlayBBCRadio1() throws Exception {
		SmartHomeActions smartHomeActions = new SmartHomeActions(httpClient);
		String result = smartHomeActions.startRadio("bbc_radio_1");
		assertTrue(result.contains("<div class=\"weather\">"));
	}

	private ByteArrayInputStream getXMLFileAsByteArray() throws IOException {
		InputStream xml = this.getClass().getClassLoader().getResourceAsStream("weather.xml");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(xml));
		ByteArrayOutputStream outputStream  = new ByteArrayOutputStream();
		bufferedReader.lines().forEach(line -> addBytesToOutputStream(line,outputStream));
        ByteArrayInputStream byteArray = new ByteArrayInputStream(outputStream.toByteArray());
		return byteArray;
	}
	
	private void addBytesToOutputStream(String line, OutputStream os){
		try {
			os.write(line.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
