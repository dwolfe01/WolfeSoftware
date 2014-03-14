package com.wolfesoftware.sailfish.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.concurrency.worker.factory.WorkerFactory;

public class ReadySteadyThreadTest {

	@Mock
	WorkerFactory workerFactory;
	@Mock
	Thread worker;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(workerFactory.getWorker()).thenReturn(worker);
	}

	@Test
	public void shouldCreateManyThreads() throws Exception {
		new ReadySteadyThread(10, workerFactory).go();
		Mockito.verify(workerFactory, Mockito.times(10)).getWorker();
		Mockito.verify(worker, Mockito.times(10)).run();
	}

}
