package com.wolfesoftware.sailfish.integration;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.responsehandler.factory.ResponseHandlerFactory.ResponseHandlers;

public class SailFishCookieCrudIntegrationTest {

	@Test
	@Ignore
	public void shouldCookieCrud() throws Exception {
		HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
		String jsonHttpUser = FileUtils.readFileToString(ResourceUtils.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/cookieCrud.json"));
		ResponseHandlerFactory.setHandler(ResponseHandlers.OUTPUTSTREAM);
		FileOutputStream fos = new FileOutputStream(new File("/tmp/output.html"));
		ResponseHandlers.OUTPUTSTREAM.setOutputStream(fos);
		factory.setJSON(jsonHttpUser);
		new ReadySteadyThread(10, factory).go();
	}

}
