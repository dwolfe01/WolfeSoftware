package com.wolfesoftware.sailfish.runnable.httpuser;

import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.wolfesoftware.sailfish.requests.AbstractRequest;
import com.wolfesoftware.sailfish.uptime.UptimeHistory;

public class UptimeHttpUser extends HttpUser{

	private UptimeHistory uptimeHistory;
	
	
	public UptimeHttpUser(UptimeHistory uptimeHistory) {
		super();
		this.uptimeHistory = uptimeHistory;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000)
				.setSocketTimeout(10000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		setWaitTimeInMilliseconds(1);
	}

	UptimeHttpUser(CloseableHttpClient httpClient, UptimeHistory uptimeHistory) {
		super(httpClient);
		this.uptimeHistory = uptimeHistory;
	}
	
	public void run() {
		for (AbstractRequest request : requests) {
			long startTime = System.currentTimeMillis();
			makeRequest(request);
			System.out.println(request.getUri() + " completed " + (System.currentTimeMillis() - startTime));
			pause();
		}
		close();
	}

	protected void makeRequest(AbstractRequest request) {
		long startTime = System.currentTimeMillis();
			try{
			request.makeRequest(httpClient);
			uptimeHistory.update(request.getUri().toString() + "", getTimeTaken(startTime));
			}catch(Exception e){
				uptimeHistory.update(request.getUri().toString() + " " + e.getClass().getSimpleName(), getTimeTaken(startTime));
			}
	}
		

}
