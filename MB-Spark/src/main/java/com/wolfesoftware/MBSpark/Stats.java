package com.wolfesoftware.MBSpark;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolfesoftware.entity.LogMessage;
import com.wolfesoftware.entity.LogMessageFactory;
import com.wolfesoftware.entity.LogMessageFactory.FIELD;

public class Stats {

	private static final long serialVersionUID = 1L;
	List<FIELD> ordering = Arrays.asList(LogMessageFactory.FIELD.DATE, LogMessageFactory.FIELD.IP, LogMessageFactory.FIELD.REQUEST);
	LogMessageFactory logMessageFactory = new LogMessageFactory("(\\S+\\s\\S+)\\s(.*)\\s(.*)",
			"yyyy-MM-dd HH:mm:ss", ordering);
	private static final Logger LOGGER = LoggerFactory.getLogger(Stats.class);
	
	public List<LogMessage> getLogs(InputStream is, String ip) throws IOException {
		List<LogMessage> logMessagesFromLogFile = null;
			try {
				logMessagesFromLogFile = logMessageFactory.getLogMessagesFromLogFileForAnyGivenLambda(is, (logMessage) -> {
					return shouldShowTheseRequests(ip, logMessage);
				});
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		return logMessagesFromLogFile;
	}

	private boolean shouldShowTheseRequests(String ip, LogMessage logMessage) {
		LocalDateTime now = LocalDateTime.now();
		long minutes = Duration.between(logMessage.getDate(), now).toMinutes();
		System.out.println("Number of minutes: " + minutes);
		return logMessage.getIP().equals(ip) && minutes < 5;
	}
	

}
