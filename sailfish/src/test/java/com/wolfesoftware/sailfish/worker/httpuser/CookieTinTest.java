package com.wolfesoftware.sailfish.worker.httpuser;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CookieTinTest {

	private static final String cookieString = "sailfish=fastfish";
	@Mock
	HttpURLConnection urlConnection;
	CookieTin cookies;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		cookies = new CookieTin();
		Mockito.when(urlConnection.getHeaderFieldKey(1)).thenReturn(
				"Set-Cookie");
		Mockito.when(urlConnection.getHeaderField(1)).thenReturn(cookieString);
	}

	@Test
	public void shouldGetCookiesFromUrlConnection() throws Exception {
		cookies.getCookiesFromUrlConnection(urlConnection);
		assertEquals(1, cookies.size());
	}

	@Test
	public void shouldSetCookiesOnUrlConnection() {
		cookies.getCookiesFromUrlConnection(urlConnection);
		cookies.setCookiesOnUrlConnection(urlConnection);
		Mockito.verify(urlConnection).setRequestProperty(CookieTin.COOKIE,
				cookieString);
	}
}
