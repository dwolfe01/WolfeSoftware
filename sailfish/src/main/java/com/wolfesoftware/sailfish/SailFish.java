package com.wolfesoftware.sailfish;

import java.io.IOException;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.worker.factory.SimpleWorkerFactory;
import com.wolfesoftware.sailfish.request.Request;
import com.wolfesoftware.sailfish.worker.UnitOfWork;

public class SailFish {

	public static void main(String[] args) throws IOException {
		UnitOfWork<String> unitOfWork = new Request(
				"http://www.onlinelibrary.wiley.com");
		SimpleWorkerFactory workerFactory = new SimpleWorkerFactory(unitOfWork);
		new ReadySteadyThread(19, workerFactory).go();
	}

}
