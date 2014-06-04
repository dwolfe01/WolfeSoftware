package com.wolfesoftware.sailfish.concurrency.worker.factory;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import com.wolfesoftware.sailfish.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.request.DoNothingRequest;
import com.wolfesoftware.sailfish.request.LoginRequest;
import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.httpuser.HttpUser;

/*
 * This class creates a  login worker and also returns it as a runnable 
 * This reads from a list of usernames and passwords
 */
public class LoginWorkerFactory extends WorkerFactory {

	private static final String URL = "http://www.someurl.com"; // currently set
																// up for spring
																// security urls

	Iterator<Map.Entry<String, String>> iterator; // is this threadsafe?

	public Runnable getWorker() {
		HttpUser loginUser = new HttpUser();
		Request loginRequest = getNextLogin();
		if (null != loginRequest) {
			try {
				loginUser.establishSession(new Request(URL));
				loginUser.add(loginRequest);
				loginUser.add(new Request(URL));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			loginUser.add(new DoNothingRequest());
		}
		return this.getAsThread(loginUser);
	}

	public void setUsernamesAndPassword(LogFileReader logFileReader)
			throws BadLogFileException {
		Map<String, String> usernameAndPassword = Collections
				.synchronizedMap(logFileReader.getAsMapOfUserNamesPasswords());
		iterator = usernameAndPassword.entrySet().iterator();
	}

	private Request getNextLogin() {
		if (this.isThereAnyMoreWorkToDo()) {
			if (iterator.hasNext()) {
				Map.Entry<String, String> pair = (Map.Entry<String, String>) iterator
						.next();
				try {
					return new LoginRequest(URL + "j_spring_security_check",
							null, pair.getKey(), pair.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				this.setIsThereAnyMoreWorkToDo(false);
			}
		}
		return null;
	}
}
