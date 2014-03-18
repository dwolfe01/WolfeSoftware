package com.wolfesoftware.sailfish.worker.httpuser;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/*
 * This currently only works if there is only one site setting the cookies
 * It also treats all cookies the same regadless of URL - to be improved!!
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
				// // delete me
				String headerValue = urlConnection.getHeaderField(i);
				System.out.println("get cookies from response" + headerKey
						+ ":" + headerValue);
			}
		}
	}

	public int size() {
		return cookies.size();
	}

	public void setCookiesOnUrlConnection(HttpURLConnection connection) {
		String cookieStringToSetOnURL = "";
		cookieStringToSetOnURL = getAllCookiesAsOneString(cookieStringToSetOnURL);
		connection.setRequestProperty(COOKIE,cookieStringToSetOnURL);
		System.out.println("set cookies on request: " + cookieStringToSetOnURL);
	}

	private String getAllCookiesAsOneString(String cookieStringToSetOnURL) {
		for (String cookie : cookies) {
			cookieStringToSetOnURL += cookie  + ";";
		}
		return cookieStringToSetOnURL;
	}

}
