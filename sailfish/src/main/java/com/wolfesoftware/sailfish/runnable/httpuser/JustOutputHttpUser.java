package com.wolfesoftware.sailfish.runnable.httpuser;

import java.io.IOException;
import java.io.OutputStream;

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

	protected void makeRequest(String request) {
		try {
			out.write(request.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
