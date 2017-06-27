package com.wolfesoftware.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Predicate;

public class LogMessageFactory {
	public enum FIELD {
		IP, DATE,REQUEST
	};
	String pattern = "(\\S+)\\s\\[(.*)\\]\\s(.*)";
	String dateFormat = "dd/MM/yyyy:HH:mm:ss Z";
	private int IP_index_in_logfile = 1;
	private int DATE_index_in_logfile = 2;
	private int REQUEST_index_in_logfile = 3;
	
	//ordering is IP / DATE / REQUEST
	public LogMessageFactory(String pattern, String dateFormat, List<FIELD> ordering){
		this.pattern = pattern;
		this.dateFormat = dateFormat;
		this.IP_index_in_logfile = ordering.indexOf(FIELD.IP) + 1;
		this.DATE_index_in_logfile = ordering.indexOf(FIELD.DATE) + 1;
		this.REQUEST_index_in_logfile = ordering.indexOf(FIELD.REQUEST) + 1;
	}

	public LogMessageFactory() {
	}

	public LogMessage getLogMessage(String log) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		LogMessage logFileEntity = null;
		Pattern r = Pattern.compile(pattern);

		Matcher m = r.matcher(log);
		if (m.matches()) {
			logFileEntity = new LogMessage();
			LocalDateTime timestamp = LocalDateTime.parse(m.group(DATE_index_in_logfile), formatter);
			logFileEntity.withIP(m.group(IP_index_in_logfile)).withDate(timestamp).withRequest(m.group(REQUEST_index_in_logfile));
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
