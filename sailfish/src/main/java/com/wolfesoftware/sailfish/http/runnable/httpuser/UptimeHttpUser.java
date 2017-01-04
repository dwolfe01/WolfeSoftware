package com.wolfesoftware.sailfish.http.runnable.httpuser;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.sailfish.http.requests.AbstractRequest;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.uptime.UptimeHistory;

public class UptimeHttpUser extends HttpUser{

	private UptimeHistory uptimeHistory;
	static final Logger Logger = LoggerFactory.getLogger(UptimeHttpUser.class);
	
	public UptimeHttpUser(UptimeHistory uptimeHistory, ResponseHandlerFactory responseHanderFactory) {
		super(responseHanderFactory);
		this.uptimeHistory = uptimeHistory;
		this.setWaitTimeInMilliseconds(30000);
	}
	
	UptimeHttpUser(CloseableHttpClient httpClient, UptimeHistory uptimeHistory,ResponseHandlerFactory responseHanderFactory) {
		super(httpClient, responseHanderFactory);
		this.uptimeHistory = uptimeHistory;
		this.setWaitTimeInMilliseconds(30000);
	}
	
	public void run() {
		for (AbstractRequest request : requests) {
			long startTime = System.currentTimeMillis();
			makeRequest(request);
			Logger.info(request.getUri() + " " + (System.currentTimeMillis() - startTime));
		}
		Logger.info(uptimeHistory.prettyPrint());
		close();
		pause();
	}

	protected void makeRequest(AbstractRequest request) {
		long startTime = System.currentTimeMillis();
			try{
			request.makeRequest(httpClient, responseHandlerFactory);
			uptimeHistory.update(request.getUri().toString() + "", getTimeTaken(startTime));
			}catch(Exception e){
				uptimeHistory.update(request.getUri().toString() + " " + e.getClass().getSimpleName(), getTimeTaken(startTime));
			}
	}
}
