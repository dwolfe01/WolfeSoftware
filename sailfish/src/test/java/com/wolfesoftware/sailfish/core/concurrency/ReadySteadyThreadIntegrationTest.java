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

public class ReadySteadyThreadIntegrationTest {

	@Mock
	WorkerFactory workerFactory;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(workerFactory.getWorker()).thenReturn(
				new Runnable(){

					@Override
					public void run() {
						
						try {
							Thread.sleep((long) (Math.random()*3000));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
				);
	}

	@Test
	public void shouldCreateManyThreads() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, true, true, true, true, true, true, true, true, false);
		new ReadySteadyThread(10, workerFactory).go();
	}

	@Test
	public void shouldCreateThreadsCorrectlyWhenPoolSizeIsSmallerThanAmountOfWorkers() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, true, true, true, true, true, true, true, true, false);
		new ReadySteadyThread(7, workerFactory).go();
		verify(workerFactory, Mockito.times(10)).getWorker();
	}

	@Test
	public void shouldCreateThreadsCorrectlyWhenPoolSizeIsLargerThanAmountOfWorkers() throws Exception {
		when(workerFactory.isThereAnyMoreWorkToDo()).thenReturn(true, true, false);
		new ReadySteadyThread(50, workerFactory).go();
		verify(workerFactory, Mockito.times(2)).getWorker();
	}

}
