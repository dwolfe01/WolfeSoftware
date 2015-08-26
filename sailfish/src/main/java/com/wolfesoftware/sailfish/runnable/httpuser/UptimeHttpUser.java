package com.wolfesoftware.sailfish.runnable.httpuser;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;

import com.wolfesoftware.sailfish.requests.Request;
import com.wolfesoftware.sailfish.uptime.UptimeHistory;

public class UptimeHttpUser extends HttpUser{

	private UptimeHistory uptimeHistory;
	
	public UptimeHttpUser(UptimeHistory uptimeHistory) {
		super();
		this.uptimeHistory = uptimeHistory;
	}

	public UptimeHttpUser(HttpClient httpClient, UptimeHistory uptimeHistory) {
		super(httpClient);
		this.uptimeHistory = uptimeHistory;
	}
	
	protected void makeRequest(Request request) {
		long startTime = System.currentTimeMillis();
			try{
			StatusLine statusLine = request.makeRequest(httpClient);
			uptimeHistory.update(statusLine.getStatusCode() + "", getTimeTaken(startTime));
			}catch(Exception e){
				uptimeHistory.update(e.getClass().getSimpleName(), getTimeTaken(startTime));
			}
	}
		

}
