package com.wolfesoftware.sailfish.worker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class WorkerTest {

	@Mock
	public UnitOfWork<String> work;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCallGoMethodOnUnitWork() throws Exception {
		new Worker(work).go();
		Mockito.verify(work, Mockito.times(1)).go();
	}

	@Test
	public void shouldCallGoMethodOnEachUnitWork() throws Exception {
		new Worker(work, work, work).go();
		Mockito.verify(work, Mockito.times(3)).go();
	}

}
