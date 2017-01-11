package com.wolfesoftware.sailfish.http.runnable.httpuser;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpClientFactory {
	
	private BasicCookieStore cookieStore = new BasicCookieStore();
	
	public BasicCookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(BasicCookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public CloseableHttpClient getHttpClient() {
		TrustManager[] trust_all_certs = new TrustManager[1];
		trust_all_certs[0] = this.getTrustManager();
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
			sslContext.init(null, trust_all_certs, new SecureRandom());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		RequestConfig config = RequestConfig.custom().setConnectTimeout(100000).setConnectionRequestTimeout(100000)
				.setSocketTimeout(100000).setRedirectsEnabled(true).setCookieSpec(CookieSpecs.DEFAULT).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setDefaultCookieStore(cookieStore)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslContext)
				.setSSLSocketFactory(sslConnectionSocketFactory).build();
		return httpClient;
	}
	
	private TrustManager getTrustManager() {
		return new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		};
	}

}
