package com.wolfesoftware.sailfish.runnable.httpuser;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.wolfesoftware.sailfish.uptime.UptimeHistory;


public class UptimeHttpUserFactory{
	
	private UptimeHistory uptimeHistory = new UptimeHistory();
	
	public UptimeHttpUser getHttpUser(){
		RequestConfig config = RequestConfig.custom()
				  .setConnectTimeout(10000)
				  .setConnectionRequestTimeout(10000)
				  .setSocketTimeout(10000).build();
				CloseableHttpClient httpClient = 
				  HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		UptimeHttpUser uptimeHttpUser = new UptimeHttpUser(httpClient,uptimeHistory);
		uptimeHttpUser.setWaitTimeInMilliseconds(1500);
		return uptimeHttpUser;
	}

}
