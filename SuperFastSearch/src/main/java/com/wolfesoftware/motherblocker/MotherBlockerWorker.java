package com.wolfesoftware.motherblocker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.slf4j.LoggerFactory;

import com.wolfesoftware.stats.LogFileStats;

import ch.qos.logback.classic.Logger;

public class MotherBlockerWorker implements Runnable {
	
	private static final Logger Logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger(MotherBlockerWorker.class);

	private String locationOfWebServerLogFiles;
	private String locationOfWhiteList;
	private String locationToPutBanList;
	private String locationToPutStats;
	private String dateFormat;
	private String logFileOrdering;
	private String logFilePattern;

	public MotherBlockerWorker(String locationOfWebServerLogFiles, String locationOfWhiteList, String locationToPutBanList, String locationToPutStats,String logFilePattern, String dateFormat, String logFileOrdering) {
		this.locationOfWebServerLogFiles = locationOfWebServerLogFiles;
		this.locationOfWhiteList = locationOfWhiteList;
		this.locationToPutBanList = locationToPutBanList;
		this.locationToPutStats = locationToPutStats;
		this.logFilePattern = logFilePattern;
		this.dateFormat = dateFormat;
		this.logFileOrdering = logFileOrdering;
	}

	@Override
	public void run(){
		Logger.info("MotherBlocker starting...");
		try {
			Logger.info("Creating stats page");
			this.createStatsPage();
		} catch (Exception e) {
			Logger.info(e.getStackTrace().toString());
		}
		Logger.info("MotherBlocker done.");
	}

	public void createStatsPage() throws IOException, ParseException {
		FileInputStream fileInputStream = new FileInputStream(new File(locationOfWebServerLogFiles));
		LogFileStats logFileStats = new LogFileStats(fileInputStream, logFilePattern, dateFormat, logFileOrdering);
		fileInputStream.close();
		String stats = logFileStats.prettyPrint();
		Files.write(Paths.get(locationToPutStats + "/stats.txt" ), stats.getBytes());
	}

}
