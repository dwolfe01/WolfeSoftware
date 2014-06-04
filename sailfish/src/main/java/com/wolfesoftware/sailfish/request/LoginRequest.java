package com.wolfesoftware.sailfish.request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.pmw.tinylog.Logger;

import com.wolfesoftware.sailfish.request.annotation.Timed;
import com.wolfesoftware.sailfish.worker.UnitOfWork;
import com.wolfesoftware.sailfish.worker.httpuser.CookieTin;

//should be called POST request and made generic
public class LoginRequest extends Request implements UnitOfWork<String> {

	private String userName;
	private String password;

	public LoginRequest(String url) throws IOException {
		super(url);
	}

	public LoginRequest(String url, CookieTin cookies, String userName,
			String password) throws IOException {
		this(url);
		this.cookieTin = cookies;
		this.userName = userName;
		this.password = password;
	}

	@Override
	@Timed
	public String go() {
		HttpURLConnection connection = null;
		try {
			long startTime = System.currentTimeMillis();
			connection = (HttpURLConnection) url.openConnection();
			setCookies(connection);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("REFERER", this.getReferer());
			connection.setDoOutput(true);
			connection.setDoInput(true);
			String content = "j_username=" + userName + "&j_password="
					+ password
					+ "&submit=Log+In&_spring_security_remember_me=on";
			DataOutputStream dos = new DataOutputStream(
					connection.getOutputStream());
			dos.writeBytes(content);

			String responseCode = connection.getResponseCode() + "";
			getCookies(connection);
			// Logger.info(readAndOutput(connection));
			Logger.info(doOutput(userName, startTime, responseCode));
			return responseCode;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			connection.disconnect();
		}
	}

	private String doOutput(String username, long startTime, String responseCode) {
		return super.doOutput(startTime, responseCode) + " for username: "
				+ username;
	}

}
