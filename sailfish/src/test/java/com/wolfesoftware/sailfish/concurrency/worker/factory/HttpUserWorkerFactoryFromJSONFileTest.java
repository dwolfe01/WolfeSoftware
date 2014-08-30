package com.wolfesoftware.sailfish.concurrency.worker.factory;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;

public class HttpUserWorkerFactoryFromJSONFileTest {

	HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();

	@Before
	public void setup() throws IOException {
		// MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCreateHttpUserFromJSONFile()
			throws FileNotFoundException, IOException {
		String jsonHttpUser = FileUtils
				.readFileToString(ResourceUtils
						.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/simplehttpuser.json"));
		factory.setJSON(jsonHttpUser);
		HttpUser httpUser = factory.getWorker();
		assertEquals("http://www.twitter.com", httpUser.getRequest(0));
		assertEquals("http://www.facebook.com", httpUser.getRequest(1));
		assertEquals("http://www.vice.com", httpUser.getRequest(2));
	}

	@Test
	public void shouldConvertHttpUserToJSON() throws Exception {
		HttpUser user = new HttpUser();
		user.add("http://www.test.com").add("http://www.test.com");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(System.out, user);
	}

}
