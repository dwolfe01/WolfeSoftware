package com.wolfesoftware.sailfish.http.runnable.httpuser;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.wolfesoftware.sailfish.http.requests.AbstractRequest;
import com.wolfesoftware.sailfish.http.uptime.UptimeHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UptimeHttpUser extends HttpUser{

	private UptimeHistory uptimeHistory;
	static final Logger Logger = LoggerFactory.getLogger(UptimeHttpUser.class);
	
	public UptimeHttpUser(UptimeHistory uptimeHistory) {
		super();
		this.uptimeHistory = uptimeHistory;
		RequestConfig config = RequestConfig.custom().setConnectTimeout(100000).setConnectionRequestTimeout(100000)
				.setSocketTimeout(100000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		setWaitTimeInMilliseconds(30000);
	}
	
	UptimeHttpUser(CloseableHttpClient httpClient, UptimeHistory uptimeHistory) {
		super(httpClient);
		this.uptimeHistory = uptimeHistory;
	}
	
	public void run() {
		for (AbstractRequest request : requests) {
			long startTime = System.currentTimeMillis();
			makeRequest(request);
			Logger.info(request.getUri() + " " + (System.currentTimeMillis() - startTime));
		}
		close();
		pause();
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
