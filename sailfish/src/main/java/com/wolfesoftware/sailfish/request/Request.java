package com.wolfesoftware.sailfish.request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.wolfesoftware.sailfish.request.annotation.Timed;
import com.wolfesoftware.sailfish.worker.UnitOfWork;

public class Request implements UnitOfWork<String> {

	URL url;

	public Request(String url) throws IOException {
		this.url = new URL(url);
	}

	@Override
	@Timed
	public String go() {
		HttpURLConnection connection = null;
		try {
			long startTime = System.currentTimeMillis();
			connection = (HttpURLConnection) url.openConnection();
			String responseCode = connection.getResponseCode() + "";
			String output = "Got: " + url + " response code: " + responseCode
					+ " Exceution time:"
					+ (System.currentTimeMillis() - startTime)
					+ " milliseconds";
			System.out.println(output);
			return responseCode;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.disconnect();
		}
	}

	private String readAndOutput(URLConnection urlConnection)
			throws IOException {
		InputStreamReader inputStreamReader = null;
		inputStreamReader = new InputStreamReader(
				urlConnection.getInputStream());
		int character;
		String result = "";
		while ((character = inputStreamReader.read()) != -1) {
			result += (char) character;
		}
		return result;
	}
}
