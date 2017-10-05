package com.wolfesoftware.motherblocker;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.ByteArrayOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;


import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.OutputStreamAppender;

public class MotherBlockerTest {
	
	private static final Logger Logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger(MotherBlockerTest.class);
	private Thread playThread;
	private ByteArrayOutputStream baos;
	@Mock
	Runtime runtime;

	@Before
	public void init() {
		initMocks(this);
		createTestAppender(Logger);
		playThread = new Thread() {
			@Override
			public void run() {
				Logger.info("playThread");
			}
		};
	}
	
	@Test
	public void shouldExecuteEveryXMinutes() throws InterruptedException {
		new MotherBlocker().execute(playThread, 1);
		Thread.sleep(1100);
		assertEquals("playThread\nplayThread\n",baos.toString());
	}

	private void createTestAppender(Logger logger) {
		baos = new ByteArrayOutputStream();
		
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
	    encoder.setPattern("%msg%n");
	    encoder.setContext(ContextSelectorStaticBinder.getSingleton().getContextSelector().getDefaultLoggerContext());
	    encoder.start();
		
		OutputStreamAppender<ILoggingEvent> appender = new OutputStreamAppender<ILoggingEvent>();
	    appender.setContext(ContextSelectorStaticBinder.getSingleton().getContextSelector().getDefaultLoggerContext());
	    appender.setEncoder(encoder);
	    appender.setOutputStream(baos);
	    appender.start();
		
	    logger.addAppender(appender);
	}

}
