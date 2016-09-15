package com.wolfesoftware.sailfish.http.worker.factory;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.wolfesoftware.sailfish.http.requests.AbstractRequest;
import com.wolfesoftware.sailfish.http.requests.PostRequest;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromJSONFile;

public class HttpUserWorkerFactoryFromJSONFileTest {

	HttpUserWorkerFactoryFromJSONFile factory;

	@Before
	public void setup() throws IOException {
		// MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateHttpUserFromJSONFile() throws FileNotFoundException, IOException, URISyntaxException {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpuser.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		HttpUser httpUser = (HttpUser) factory.getWorker();
		assertEquals("http://www.twitter.com", httpUser.getRequest(0).toString());
		assertEquals("http://www.facebook.com", httpUser.getRequest(1).toString());
		assertEquals("http://www.vice.com", httpUser.getRequest(2).toString());
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
	}

	@Test
	public void shouldCreateTwoHttpUsersFromJSONFile() throws FileNotFoundException, IOException, URISyntaxException {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpusers.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		HttpUser httpUser = (HttpUser) factory.getWorker();
		assertEquals("http://www.twitter.com", httpUser.getRequest(0).toString());
		assertEquals("http://www.facebook.com", httpUser.getRequest(1).toString());
		assertEquals("http://www.vice.com", httpUser.getRequest(2).toString());
		assertEquals(true, factory.isThereAnyMoreWorkToDo());
		httpUser = (HttpUser) factory.getWorker();
		assertEquals("http://www.coca-cola.com", httpUser.getRequest(0).toString());
		assertEquals("http://www.asparagus.com", httpUser.getRequest(1).toString());
		assertEquals("http://www.vice.com", httpUser.getRequest(2).toString());
		assertEquals(false, factory.isThereAnyMoreWorkToDo());
	}
	
	@Test
	public void shouldCreateHttpUsersWithPostRequestFromJSONFile() throws FileNotFoundException, IOException, URISyntaxException {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/post-httpuser.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		HttpUser httpUser = (HttpUser) factory.getWorker();
		PostRequest request1 = (PostRequest) httpUser.getRequest(0);
		assertEquals("http://test.com/j_spring_security_check", request1.toString());
		assertEquals("http://test.com:9092", httpUser.getRequest(1).toString());
	}
	
	@Test
	public void shouldOutputUsers() throws FileNotFoundException, IOException, URISyntaxException {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/post-httpuser.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		System.out.println(factory.toString());
	}


	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionIfCallGetWorkerWithNoMoreWorkToDo() throws FileNotFoundException, IOException, URISyntaxException {
		URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpusers.json").toURI();
		String jsonHttpUser = FileUtils.readFileToString(new File(uri));
		factory = new HttpUserWorkerFactoryFromJSONFile(jsonHttpUser);
		factory.getWorker();
		factory.getWorker();
		factory.getWorker();
	}

}
