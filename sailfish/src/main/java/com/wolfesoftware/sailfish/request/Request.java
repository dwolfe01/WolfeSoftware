package com.wolfesoftware.sailfish.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.request.annotation.Timed;
import com.wolfesoftware.sailfish.worker.UnitOfWork;
import com.wolfesoftware.sailfish.worker.httpuser.CookieTin;

public class Request implements UnitOfWork<String> {

	URL url;
	private CookieTin cookieTin = null;

	public Request(String url) throws IOException {
		this.url = new URL(url);
	}

	public Request(String url, CookieTin cookies) throws IOException {
		this(url);
		this.cookieTin = cookies;
	}

	public Request() {
	}

	@Override
	@Timed
	public String go() {
		HttpURLConnection connection = null;
		try {
			long startTime = System.currentTimeMillis();
			connection = (HttpURLConnection) url.openConnection();
			setCookies(connection);
			String responseCode = connection.getResponseCode() + "";
			getCookies(connection);
			String output = doOutput(startTime, responseCode);
			Logger.info(output);
			return responseCode;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.disconnect();
		}
	}

	private String doOutput(long startTime, String responseCode) {
		String output = "Got: " + url + " response code: " + responseCode
				+ " Exceution time:" + (System.currentTimeMillis() - startTime)
				+ " milliseconds";
		return output;
	}

	private void getCookies(HttpURLConnection connection) {
		if (cookieTin != null) {
			cookieTin.getCookiesFromUrlConnection(connection);
		}
	}

	private void setCookies(HttpURLConnection connection) {
		if (cookieTin != null) {
			cookieTin.setCookiesOnUrlConnection(connection);
		}
	}

}

// private String readAndOutput(final URLConnection urlConnection)
// throws IOException {
// InputStreamReader inputStreamReader = null;
// inputStreamReader = new InputStreamReader(
// urlConnection.getInputStream());
// int character;
// String result = "";
// while ((character = inputStreamReader.read()) != -1) {
// result += (char) character;
// }
// return result;
// }
