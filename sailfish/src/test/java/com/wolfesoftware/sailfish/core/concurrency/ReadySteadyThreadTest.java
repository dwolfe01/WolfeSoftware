package com.wolfesoftware.sailfish.core.concurrency;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;

public class ReadySteadyThreadTest {

	@Mock
	WorkerFactory workerFactory;
	@Mock
	HttpUser worker;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(workerFactory.getWorker()).thenReturn(worker);
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(false);
	}

	@Test
	public void shouldCreateManyThreads() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, true, true, true, true, true, true, true, true, false);
		new ReadySteadyThread(10, workerFactory).go();
		Mockito.verify(worker, Mockito.times(10)).run();
	}

	@Test
	public void shouldCreateThreadsCorrectlyWhenPoolSizeIsSmallerThanAmountOfWorkers() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, true, true, true, true, true, true, true, true, false);
		new ReadySteadyThread(2, workerFactory).go();
		verify(workerFactory, Mockito.times(10)).getWorker();
		verify(worker, Mockito.times(10)).run();
	}

	@Test
	public void shouldCreateThreadsCorrectlyWhenPoolSizeIsLargerThanAmountOfWorkers() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, false);
		new ReadySteadyThread(50, workerFactory).go();
		verify(workerFactory, Mockito.times(2)).getWorker();
		verify(worker, Mockito.times(2)).run();
	}

	@Test
	public void shouldMaintainListOfWorkersEvenIfOneThrowsAnUncheckedException() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, false);
		Mockito.doThrow(new RuntimeException()).when(worker).run();
		new ReadySteadyThread(1, workerFactory).go();
		verify(workerFactory, Mockito.times(2)).getWorker();
		verify(worker, Mockito.times(2)).run();
	}
}
