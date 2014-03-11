package com.wolfesoftware.sailfish.request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.wolfesoftware.sailfish.request.annotation.Timed;
import com.wolfesoftware.sailfish.worker.UnitOfWork;

public class Request implements UnitOfWork {

	URL url;
	
	public Request(String url) throws IOException {
		this.url = new URL(url);
	}

	@Override
	@Timed
	public String go(){
		try{
		InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream()); 
		int character;
		String result = "";
		while ((character = inputStreamReader.read()) != -1) {
			result += (char)character;
		}
		return result;
		}catch (IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
}
