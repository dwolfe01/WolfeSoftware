package com.wolfesoftware.sailfish.concurrency;

import org.junit.Test;

import com.wolfesoftware.sailfish.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.concurrency.HttpSessionWorkerFactory;

public class ReadySteadyThreadTest {
	
	@Test
	public void shouldCreateManyThreads() throws Exception {
		new ReadySteadyThread(10, new HttpSessionWorkerFactory()).go();
	}

}



