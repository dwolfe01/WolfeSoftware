package com.wolfesoftware.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wolfesoftware.stats.LogFileStats;

/* This produces a whole list of log messages */
public class LogMessageFactory {

	public LogMessage getLogMessage(String log) throws ParseException {
		SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss Z");
		LogMessage logFileEntity = null;
		// raw message
		// String pattern =
		// "(\\S+)\\s-\\s-\\s\\[(.*)\\]\\s\"GET\\s([^\"]+)\\s.*\".*";
		String pattern = "(\\S+)\\s\\[(.*)\\]\\s(.*)";
		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(log);
		if (m.matches()) {
			logFileEntity = new LogMessage();
			Date timestamp = sdfInput.parse(m.group(2));
			logFileEntity.withIP(m.group(1)).withDate(timestamp).withRequest(m.group(3));
		}
		return logFileEntity;
	}

	public List<LogMessage> getLogMessagesFromLogFile(InputStream logFileInputStream)
			throws ParseException, IOException {
		return loopAndFilter(logFileInputStream, new LogMessageMatcher() {
			@Override
			public boolean doesLogMessageMatchCriteria(LogMessage logMessage) {
				return true;
			}
		});
	}

	public List<LogMessage> getLogMessagesFromLogFileForIP(InputStream logFileInputStream, final String IP)
			throws ParseException, IOException {
		return loopAndFilter(logFileInputStream, new LogMessageMatcher() {
			@Override
			public boolean doesLogMessageMatchCriteria(LogMessage logMessage) {
				return logMessage.getIP().equals(IP);
			}
		});
	}
	
	public void prettyPrint(List<LogMessage> logMessages){
		Iterator<LogMessage> iterator = logMessages.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}

	// prob be better in JAVA 8
	private List<LogMessage> loopAndFilter(InputStream logFileInputStream, LogMessageMatcher logMessageMatcher)
			throws IOException, ParseException {
		ArrayList<LogMessage> logs = new ArrayList<LogMessage>();
		InputStreamReader isr = new InputStreamReader(logFileInputStream);
		String logFileMessage;
		try (BufferedReader br = new BufferedReader(isr)) {
			while ((logFileMessage = br.readLine()) != null) {
				LogMessage logMessage = this.getLogMessage(logFileMessage);
				if (logMessageMatcher.doesLogMessageMatchCriteria(logMessage)) {
					logs.add(logMessage);
				}
			}
		}
		isr.close();
		return logs;
	}

}

interface LogMessageMatcher {
	public boolean doesLogMessageMatchCriteria(LogMessage logMessage);
}
