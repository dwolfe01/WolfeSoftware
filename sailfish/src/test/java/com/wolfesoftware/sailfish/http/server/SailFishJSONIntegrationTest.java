package com.wolfesoftware.sailfish.http.server;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory.ResponseHandlers;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromJSONFile;

public class SailFishJSONIntegrationTest {

	@Test
	@Ignore
	public void shouldRunRequestsFromJSONFileAgainstJettyServer() throws Exception {
		JettyServer js = null;
		try {
			js = new JettyServer();
			js.go();
			URI uri = this.getClass().getResource("/httpuser.json").toURI();
			String jsonHttpUser = FileUtils.readFileToString(new File(uri));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ResponseHandlerFactory responseHandlerFactory = new ResponseHandlerFactory();
			responseHandlerFactory.setOutputStream(baos);
			HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser, responseHandlerFactory);
			
			new ReadySteadyThread(10, factory).go();
			
			assertEquals("j_username=dwolfe%40wiley.com&j_password=password", baos.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			js.stop();
		}
	}

}
