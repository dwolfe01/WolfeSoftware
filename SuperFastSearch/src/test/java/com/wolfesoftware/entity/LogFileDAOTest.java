package com.wolfesoftware.entity;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.wolfesoftware.entity.LogMessageDAO;
import com.wolfesoftware.entity.LogMessageFactory;
import com.wolfesoftware.entity.LogMessageFactory.FIELD;
import com.wolfesoftware.entity.LogMessage;

public class LogFileDAOTest {

	LogMessageDAO logMessageDAO = new LogMessageDAO();
	Pattern regex = Pattern.compile("(.*)");

	@Before
	public void loadTestDatabaseWithContentLoaderEvents() throws IOException, ParseException {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("apache_short.log");
		Paths.get("classpath://apache_short.log");
		List<FIELD> ordering = Arrays.asList(LogMessageFactory.FIELD.IP, LogMessageFactory.FIELD.DATE, LogMessageFactory.FIELD.REQUEST);
		LogMessageFactory logMessageFactory = new LogMessageFactory("(\\S+)\\s\\[(.*)\\]\\s(.*)", "dd/MM/yyyy:HH:mm:ss Z", ordering);
		List<LogMessage> logFileEntitiesFromLogFile = logMessageFactory.getLogMessagesFromLogFile(resourceAsStream);
		for (LogMessage logMessage:logFileEntitiesFromLogFile){
			logMessageDAO.save(logMessage);
		}
	}

	@Test
	public void shouldBeTwoEventsInTheDatabase() throws ParseException {
		Long numberOfLogMessages = logMessageDAO.getCountOfLogFileEntities();
		assertEquals(new Long(10),numberOfLogMessages);
	}

}
