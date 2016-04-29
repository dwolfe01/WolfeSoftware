package com.wolfesoftware.sailfish.integration;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//@Ignore("there are no assertions here so these are just for sanity tests within IDE")
public class SailFishIntegrationTest {

	@Test
	public void shouldMakeHttpRequests() throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		String jsonHttpUser = readFileToString("com/wolfesoftware/sailfish/json/httpuser/httpusers.json");
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
	}

	@Test
	public void shouldNotLeaveFileHandlesOpen() throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		String jsonHttpUser = readFileToString("com/wolfesoftware/sailfish/json/httpuser/httpusers.json");
		factory.setJSON(jsonHttpUser);
		System.out.println("file descriptors: " + getNumberOfOpenFileDescriptors());
		new ReadySteadyThread(10, factory).go();
		System.out.println("file descriptors: " + getNumberOfOpenFileDescriptors());
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

	private String readFileToString(String url) throws IOException {
		String stringFromFile = "";
		InputStream resourceAsStream = this.getClass().getClassLoader()
				.getResourceAsStream(url);

		InputStreamReader is = new InputStreamReader(resourceAsStream);
		BufferedReader br = new BufferedReader(is);
		String line;
		while (null != (line = br.readLine())) {
			stringFromFile += line;
		}
		return stringFromFile;
	}

	@Test
	public void shouldMakeHttpGetAndPostRequests() throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		String jsonHttpUser = readFileToString("com/wolfesoftware/sailfish/json/httpuser/post-httpuser.json");
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
	}

	@Test
	@Ignore
	public void shouldCreateJSONFileOfRequestsFromAURLAndWriteToFile()
			throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		String jsonHttpUser = readFileToString("com/wolfesoftware/sailfish/json/httpuser/scratch.json");
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
	}

	@Test
	@Ignore
	public void createJSONFromListOfUrls() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM
				.setOutputStream(baos);
		String jsonHttpUser = readFileToString("classpath:com/wolfesoftware/sailfish/json/httpuser/scratch.json");
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
		//
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
