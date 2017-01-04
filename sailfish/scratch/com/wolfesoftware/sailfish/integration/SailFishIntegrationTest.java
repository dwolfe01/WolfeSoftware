package com.wolfesoftware.sailfish.integration;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.UnixOperatingSystemMXBean;
import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.requests.GetRequest;
import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.responsehandler.ResponseHandlerFactory.ResponseHandlers;
import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;

public class SailFishIntegrationTest {
	
	HttpUserWorkerFactoryFromJSONFile factory;

	@Test
	public void shouldMakeHttpRequests() throws Exception {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpusers.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser, new ResponseHandlerFactory());
		new ReadySteadyThread(10, factory).go();
	}

	@Ignore
	@Test
	public void shouldNotLeaveFileHandlesOpen() throws Exception {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpusers.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		long numberOfOpenFileDescriptors = getNumberOfOpenFileDescriptors();
		new ReadySteadyThread(10, factory).go();
		assertEquals(numberOfOpenFileDescriptors,getNumberOfOpenFileDescriptors());
	}

	private long getNumberOfOpenFileDescriptors() {
		OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		long openFileDescriptorCount = 0;
		if (os instanceof UnixOperatingSystemMXBean) {
			openFileDescriptorCount = ((UnixOperatingSystemMXBean) os)
					.getOpenFileDescriptorCount();
		}
		return openFileDescriptorCount;
	}

	

	@Test
	public void shouldMakeHttpGetAndPostRequests() throws Exception {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpusers.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		new ReadySteadyThread(10, factory).go();
	}


	@Test
	@Ignore
	public void createJSONFromListOfUrls() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		List<String> extractUrls = extractUrls(baos);
		ObjectMapper mapper = new ObjectMapper();
		for (String url : extractUrls) {
			System.out.println(url);
			HttpUser user = new HttpUser();
			user.addGetRequest(new GetRequest(url));
			if (Math.random() > 0.8) {
				mapper.writeValue(System.out, user);
				user = new HttpUser();
			}
		}
	}

	private List<String> extractUrls(ByteArrayOutputStream baos) {
		List<String> urls = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<a.+href=\"(.+?)\"");
		Matcher matcher = pattern.matcher(baos.toString());
		while (matcher.find()) {
			urls.add(matcher.group());
		}
		return urls;
	}
}
