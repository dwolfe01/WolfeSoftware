package com.wolfesoftware.sailfish.concurrency;

import java.io.IOException;

import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.Worker;

public class HttpSessionWorkerFactory implements WorkerFactory {
	/*
	 * This class creates a worker and also returns returns a thread
	 */
	public Runnable getThread(){
		try {
			//create a worker
			final Worker httpSession = new Worker(new Request("http://localhost"));
			//return a Thread
			return new Thread(){
				@Override
				public void run(){
					httpSession.go();
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
