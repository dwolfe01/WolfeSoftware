package com.wolfesoftware.sailfish.worker.httpuser;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/*
 * This currently only works if there is only one site setting the cookies
 * There could otherwise be a clash in names of cookies
 */
public class CookieTin {

	protected static final String COOKIE = "Cookie";
	List<String> cookies = new ArrayList<String>();

	public void getCookiesFromUrlConnection(URLConnection urlConnection) {
		String headerKey = "";
		for (int i = 1; (headerKey = urlConnection.getHeaderFieldKey(i)) != null; i++) {
			if (headerKey.equals("Set-Cookie")) {
				cookies.add(urlConnection.getHeaderField(i));
			}
			// // delete me
			String headerValue = urlConnection.getHeaderField(i);
			System.out.println(headerKey + ":" + headerValue);
		}
	}

	public int size() {
		return cookies.size();
	}

	public void setCookiesOnUrlConnection(HttpURLConnection connection) {
		for (String cookie : cookies) {
			connection.setRequestProperty(COOKIE,
					getBitOfCoookieToSendBackToServer(cookie));
			System.out.println(getBitOfCoookieToSendBackToServer(cookie));
		}
	}

	private String getBitOfCoookieToSendBackToServer(String cookie) {
		return cookie.split(";")[0];
	}

}
