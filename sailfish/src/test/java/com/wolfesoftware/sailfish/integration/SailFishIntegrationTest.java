package com.wolfesoftware.sailfish.integration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory.ResponseHandlers;
import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;

@Ignore
public class SailFishIntegrationTest {

	@Test
	public void shouldMakeHttpRequests() throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		String jsonHttpUser = FileUtils
				.readFileToString(ResourceUtils
						.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/httpusers.json"));
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
	}

	@Test
	public void shouldCreateJSONFileOfRequestsFromAURLAndWriteToFile()
			throws Exception {
		FileOutputStream fos = new FileOutputStream(new File("/tmp/output.txt"));
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM
				.setOutputStream(fos);
		String jsonHttpUser = FileUtils
				.readFileToString(ResourceUtils
						.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/scratch.json"));
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
	}

	@Test
	public void createJSONFromListOfUrls() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		ResponseHandlerFactory.ResponseHandlers.OUTPUTSTREAM
				.setOutputStream(baos);
		String jsonHttpUser = FileUtils
				.readFileToString(ResourceUtils
						.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/scratch.json"));
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
		//
		List<String> extractUrls = extractUrls(baos);
		ObjectMapper mapper = new ObjectMapper();
		for (String url : extractUrls) {
			System.out.println(url);
			HttpUser user = new HttpUser();
			user.add(url);
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
