package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.io.OutputStream;

import com.wolfesoftware.sailfish.requests.Request;

public class JustOutputHttpUser extends HttpUser {

	OutputStream out;

	public JustOutputHttpUser() {
		this.id = "JustOutputUser";
		this.out = System.out;
	}

	public JustOutputHttpUser(OutputStream out) {
		this.id = "JustOutputUser";
		this.out = out;
	}

	@Override
	protected void makeRequest(Request request) {
		try {
			out.write(request.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
