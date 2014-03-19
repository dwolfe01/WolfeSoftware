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
		new ReadySteadyThread(10, workerFactory).go();
		Mockito.verify(workerFactory, Mockito.times(10)).getWorker();
		Mockito.verify(worker, Mockito.times(10)).run();
	}

	@Test
	public void shouldCreateThreadsTwiceIfTheWorkerFactoryHasMoreWorkToDoAfterTheFirstSetOFThreadsHaveCompleted()
			throws Exception {
		Mockito.when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true,
				false);
		new ReadySteadyThread(10, workerFactory).go();
		Mockito.verify(workerFactory, Mockito.times(20)).getWorker();
		Mockito.verify(workerFactory, Mockito.times(2))
				.isThereAnyMoreWorkToDo();
		Mockito.verify(worker, Mockito.times(20)).run();
	}

	@Test
	public void shouldCreateThreadsThriceIfTheWorkerFactoryHasMoreWorkToDoAfterTheFirstSetOFThreadsHaveCompleted()
			throws Exception {
		Mockito.when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true,
				true, false);
		new ReadySteadyThread(5, workerFactory).go();
		Mockito.verify(workerFactory, Mockito.times(15)).getWorker();
		Mockito.verify(workerFactory, Mockito.times(3))
				.isThereAnyMoreWorkToDo();
		Mockito.verify(worker, Mockito.times(15)).run();
	}
}
