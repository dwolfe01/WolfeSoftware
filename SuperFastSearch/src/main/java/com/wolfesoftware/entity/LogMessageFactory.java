package com.wolfesoftware.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Predicate;

public class LogMessageFactory {
	
	String pattern = "(\\S+)\\s\\[(.*)\\]\\s(.*)";
	String dateFormat = "dd/MM/yyyy:HH:mm:ss Z";
	private int[] ordering = {1, 2, 3};
	
	//ordering is IP / DATE / REQUEST
	public LogMessageFactory(String pattern, String dateFormat, int[] ordering){
		this.pattern = pattern;
		this.dateFormat = dateFormat;
		this.ordering  = ordering;
	}

	public LogMessageFactory() {
	}

	public LogMessage getLogMessage(String log) throws ParseException {
		SimpleDateFormat sdfInput = new SimpleDateFormat(dateFormat);
		LogMessage logFileEntity = null;
		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(log);
		if (m.matches()) {
			logFileEntity = new LogMessage();
			Date timestamp = sdfInput.parse(m.group(ordering[1]));
			logFileEntity.withIP(m.group(ordering[0])).withDate(timestamp).withRequest(m.group(ordering[2]));
		}
		return logFileEntity;
	}

	public List<LogMessage> getLogMessagesFromLogFile(InputStream logFileInputStream)
			throws ParseException, IOException {
		return loopAndFilter(logFileInputStream, logMessage -> true);
	}

	public List<LogMessage> getLogMessagesFromLogFileForIP(InputStream logFileInputStream, final String IP)
			throws ParseException, IOException {
		return loopAndFilter(logFileInputStream, logMessage -> logMessage.getIP().equals(IP));
	}

	public List<LogMessage> getLogMessagesFromLogFileForAnyGivenLambda(InputStream logFileInputStream, Predicate<LogMessage> predicate)
			throws ParseException, IOException {
		return loopAndFilter(logFileInputStream, predicate);
	}
	
	public void prettyPrint(List<LogMessage> logMessages){
		Iterator<LogMessage> iterator = logMessages.iterator();
		while (iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}

	private List<LogMessage> loopAndFilter(InputStream logFileInputStream, Predicate<LogMessage> predicate)
			throws IOException, ParseException {
		ArrayList<LogMessage> logs = new ArrayList<LogMessage>();
		InputStreamReader isr = new InputStreamReader(logFileInputStream);
		String logFileMessage;
		try (BufferedReader br = new BufferedReader(isr)) {
			while ((logFileMessage = br.readLine()) != null) {
				LogMessage logMessage = this.getLogMessage(logFileMessage);
				if (predicate.test(logMessage)) {
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
