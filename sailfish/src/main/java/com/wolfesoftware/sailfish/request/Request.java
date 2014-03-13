package com.wolfesoftware.sailfish.request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
		InputStreamReader inputStreamReader = null;
		try {
			long startTime = System.currentTimeMillis();
			inputStreamReader = new InputStreamReader(url.openConnection()
					.getInputStream());
			String result = "";
			System.out.println("Got: " + url + " Exceution time:"
					+ (System.currentTimeMillis() - startTime)
					+ " milliseconds");
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String readAndOutput(InputStreamReader inputStreamReader)
			throws IOException {
		int character;
		String result = "";
		while ((character = inputStreamReader.read()) != -1) {
			result += (char) character;
		}
		return result;
	}
}
