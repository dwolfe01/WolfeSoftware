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
		Mockito.when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(false);
	}

	@Test
	public void shouldCreateManyThreads() throws Exception {
		Mockito.when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true,
				true, true, true, true, true, true, true, true, true, false);
		new ReadySteadyThread(10, workerFactory).go();
		Mockito.verify(worker, Mockito.times(10)).run();
	}

	@Test
	public void shouldCreateThreadsCorrectlyWhenPoolSizeIsSmallerThanAmountOfWorkers()
			throws Exception {
		Mockito.when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true,
				true, true, true, true, true, true, true, true, true, false);
		new ReadySteadyThread(2, workerFactory).go();
		Mockito.verify(workerFactory, Mockito.times(10)).getWorker();
		Mockito.verify(worker, Mockito.times(10)).run();
	}

	@Test
	public void shouldCreateThreadsCorrectlyWhenPoolSizeIsLargerThanAmountOfWorkers()
			throws Exception {
		Mockito.when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true,
				true, false);
		new ReadySteadyThread(50, workerFactory).go();
		Mockito.verify(workerFactory, Mockito.times(2)).getWorker();
		Mockito.verify(worker, Mockito.times(2)).run();
	}
}
