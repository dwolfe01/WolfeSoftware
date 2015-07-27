//package com.wolfesoftware.sailfish.concurrency.worker.factory;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//
//import org.apache.commons.io.FileUtils;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.util.ResourceUtils;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wolfesoftware.sailfish.requests.GetRequest;
//import com.wolfesoftware.sailfish.runnable.httpuser.HttpUser;
//import com.wolfesoftware.sailfish.runnable.httpuser.JustOutputHttpUser;
//
//public class HttpUserWorkerFactoryFromJSONFileTest {
//
//	HttpUserWorkerFactoryFromJSONFile factory = new HttpUserWorkerFactoryFromJSONFile();
//
//	@Before
//	public void setup() throws IOException {
//		// MockitoAnnotations.initMocks(this);
//	}
//
//	@Test
//	public void shouldCreateHttpUserFromJSONFile() throws FileNotFoundException, IOException {
//		String jsonHttpUser = FileUtils.readFileToString(ResourceUtils.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/httpuser.json"));
//		factory.setJSON(jsonHttpUser);
//		HttpUser httpUser = (HttpUser) factory.getWorker();
//		assertEquals("http://www.twitter.com", httpUser.getRequest(0).toString());
//		assertEquals("http://www.facebook.com", httpUser.getRequest(1).toString());
//		assertEquals("http://www.vice.com", httpUser.getRequest(2).toString());
//		assertEquals(false, factory.isThereAnyMoreWorkToDo());
//	}
//
//	@Test
//	public void shouldCreateTwoHttpUsersFromJSONFile() throws FileNotFoundException, IOException {
//		String jsonHttpUser = FileUtils.readFileToString(ResourceUtils.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/httpusers.json"));
//		factory.setJSON(jsonHttpUser);
//		HttpUser httpUser = (HttpUser) factory.getWorker();
//		assertEquals("http://www.twitter.com", httpUser.getRequest(0).toString());
//		assertEquals("http://www.facebook.com", httpUser.getRequest(1).toString());
//		assertEquals("http://www.vice.com", httpUser.getRequest(2).toString());
//		assertEquals(true, factory.isThereAnyMoreWorkToDo());
//		httpUser = (HttpUser) factory.getWorker();
//		assertEquals("http://www.coca-cola.com", httpUser.getRequest(0).toString());
//		assertEquals("http://www.asparagus.com", httpUser.getRequest(1).toString());
//		assertEquals("http://www.vice.com", httpUser.getRequest(2).toString());
//		assertEquals(false, factory.isThereAnyMoreWorkToDo());
//	}
//
//	@Test
//	public void shouldCreateJustOutputHttpUsersFromJSONFile() throws FileNotFoundException, IOException {
//		String jsonHttpUser = FileUtils.readFileToString(ResourceUtils.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/httpusers.json"));
//		factory.setDryRun(true);
//		factory.setJSON(jsonHttpUser);
//		JustOutputHttpUser httpUser = (JustOutputHttpUser) factory.getWorker();
//	}
//
//	@Test(expected = RuntimeException.class)
//	public void shouldThrowExceptionIfCallGetWorkerWithNoMoreWorkToDo() throws FileNotFoundException, IOException {
//		String jsonHttpUser = FileUtils.readFileToString(ResourceUtils.getFile("classpath:com/wolfesoftware/sailfish/json/httpuser/httpusers.json"));
//		factory.setJSON(jsonHttpUser);
//		factory.getWorker();
//		factory.getWorker();
//		factory.getWorker();
//	}
//
//	@Test
//	@Ignore
//	public void shouldConvertHttpUserToJSON() throws Exception {
//		HttpUser user = new HttpUser();
//		user.addGetRequest(new GetRequest("http://www.test.com")).addGetRequest(new GetRequest("http://www.test.com"));
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(System.out, user);
//	}
//
//}
