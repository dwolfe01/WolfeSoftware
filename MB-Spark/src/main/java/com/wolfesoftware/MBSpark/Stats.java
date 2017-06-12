package com.wolfesoftware.MBSpark;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import com.wolfesoftware.entity.LogMessage;
import com.wolfesoftware.entity.LogMessageFactory;

public class Stats {

	private static final long serialVersionUID = 1L;
	LogMessageFactory logMessageFactory = new LogMessageFactory("(\\S+\\s\\S+)\\s(.*)\\s(.*)",
			"yyyy-MM-dd HH:mm:ss", new int[]{2,1,3});
	//2017-06-08 16:52:44 0:0:0:0:0:0:0:1 /index

	public List<LogMessage> getLogs(InputStream is, String ip) throws IOException {
		List<LogMessage> logMessagesFromLogFile = null;
			try {
				logMessagesFromLogFile = logMessageFactory.getLogMessagesFromLogFileForIP(is, ip);
			} catch (ParseException e) {
			}
		return logMessagesFromLogFile;
	}
	

}
