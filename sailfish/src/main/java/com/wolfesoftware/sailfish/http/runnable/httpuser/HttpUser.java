package com.wolfesoftware.sailfish.http.runnable.httpuser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.StatusLine;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.sailfish.http.requests.AbstractRequest;
import com.wolfesoftware.sailfish.http.requests.GetRequest;
import com.wolfesoftware.sailfish.http.requests.PostRequest;
/*
 * A fluent API for creating a user http session, this creates a session and runs through a list of requests in order observing any wait times
 */
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;

public class HttpUser implements Runnable {

	protected String id;

	protected String clientIP = "";
	protected CloseableHttpClient httpClient;
	protected List<AbstractRequest> requests = new ArrayList<AbstractRequest>();
	private long waitTime = 0;
	static final Logger Logger = LoggerFactory.getLogger(HttpUser.class);
	private ResponseHandlerFactory responseHandlerFactory;

	private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	} };

	
	public HttpUser() {
		this(new ResponseHandlerFactory());
	}

	public HttpUser(ResponseHandlerFactory responseHandlerFactory) {
		this.responseHandlerFactory = responseHandlerFactory;
		SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
		SSLConnectionSocketFactory sslConnectionSocketFactory;
		SSLContext sslContext;

		try {
			sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
			sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, TRUST_ALL_CERTS, new SecureRandom());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		RequestConfig config = RequestConfig.custom().setConnectTimeout(100000).setConnectionRequestTimeout(100000)
				.setSocketTimeout(100000).setRedirectsEnabled(true).setCookieSpec(CookieSpecs.DEFAULT).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setDefaultCookieStore(new BasicCookieStore())
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext)
				.setSSLSocketFactory(sslConnectionSocketFactory).build();
	}

	// purely for test purposes
	public HttpUser(CloseableHttpClient httpClient, ResponseHandlerFactory responseHandlerFactory) {
		this.httpClient = httpClient;
		this.responseHandlerFactory = responseHandlerFactory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void run() {
		for (AbstractRequest request : requests) {
			makeRequest(request);
			pause();
		}
		close();
	}

	protected void makeRequest(AbstractRequest request) {
		long startTime = System.currentTimeMillis();
		try {
			StatusLine statusLine = request.makeRequest(httpClient);
			doOutput(startTime, request, statusLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HttpUser addGetRequest(GetRequest getrequest) throws URISyntaxException {
		requests.add(getrequest);
		return this;
	}
	
	public HttpUser addGetRequest(String uri) throws URISyntaxException {
		requests.add(new GetRequest(uri, responseHandlerFactory));
		return this;
	}

	public HttpUser setWaitTimeInMilliseconds(long sleepTime) {
		this.waitTime = sleepTime;
		return this;
	}

	public List<AbstractRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<String[]> uris) throws URISyntaxException {
		for (String[] uri : uris) {
			if (uri.length == 1) {
				this.addGetRequest(new GetRequest(uri[0], responseHandlerFactory));
			} else {
				PostRequest pr = new PostRequest(uri[0], responseHandlerFactory);
				for (int x = 2; x < uri.length; x++) {
					String[] nameValue = uri[x].split(":");
					pr.addNameValuePostPair(nameValue[0], nameValue[1]);
				}
				this.addPostRequest(pr);
			}
		}
	}

	public AbstractRequest getRequest(int index) {
		return requests.get(index);
	}

	protected void pause() {
		if (waitTime > 0) {
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doOutput(long startTime, AbstractRequest request, StatusLine statusLine) {
		Logger.info(statusLine + " " + request + " took " + getTimeTaken(startTime));
	}

	protected long getTimeTaken(long startTime) {
		return System.currentTimeMillis() - startTime;
	}

	public HttpUser addPostRequest(PostRequest request) {
		requests.add(request);
		return this;
	}

	protected void close() {
		try {
			this.httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ResponseHandlerFactory getResponseHandlerFactory() {
		return responseHandlerFactory;
	}
	
	public void setResponseHandlerFactory(ResponseHandlerFactory responseHandlerFactory) {
		this.responseHandlerFactory = responseHandlerFactory;
	}

}
