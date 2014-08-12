package com.wolfesoftware.sailfish.request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.wolfesoftware.sailfish.request.annotation.Timed;
import com.wolfesoftware.sailfish.worker.UnitOfWork;
import com.wolfesoftware.sailfish.worker.httpuser.CookieTin;

public class Request implements UnitOfWork<String> {

	URL url;
	public String referer = "";

	protected CookieTin cookieTin = null;

	public Request() {
	}

	public Request(String url) throws IOException {
		this.url = new URL(url);
	}

	public Request(String url, CookieTin cookies) throws IOException {
		this(url);
		this.cookieTin = cookies;
	}

	@Override
	@Timed
	public String go() {
		HttpURLConnection connection = null;
		try {
			long startTime = System.currentTimeMillis();
			HttpURLConnection.setFollowRedirects(false);
			connection = (HttpURLConnection) url.openConnection();
			setCookies(connection);
			connection.setRequestProperty("REFERER", this.getReferer());
			String responseCode = connection.getResponseCode() + "";
			getCookies(connection);
			// Logger.info(readAndOutput(connection));
			// Logger.info(doOutput(startTime, responseCode));
			return responseCode;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.disconnect();
		}
	}

	protected String doOutput(long startTime, String responseCode) {
		String output = "Got: " + url + " response code: " + responseCode
				+ " Exceution time:" + (System.currentTimeMillis() - startTime)
				+ " milliseconds";
		return output;
	}

	protected void getCookies(HttpURLConnection connection) {
		if (cookieTin != null) {
			cookieTin.getCookiesFromUrlConnection(connection);
		}
	}

	protected void setCookies(HttpURLConnection connection) {
		if (cookieTin != null) {
			cookieTin.setCookiesOnUrlConnection(connection);
		}
	}

	public void setCookieTin(CookieTin cookieTin) {
		this.cookieTin = cookieTin;
	}

	protected String readAndOutput(final HttpURLConnection urlConnection)
			throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(
				urlConnection.getInputStream());
		int character;
		String result = "";
		while ((character = inputStreamReader.read()) != -1) {
			result += (char) character;
		}
		return result;
	}

	public String getUrl() {
		if (null!=url){
			return url.toString();
		}else{
			return "no url";
		}
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getReferer() {
		return referer;
	}

}
