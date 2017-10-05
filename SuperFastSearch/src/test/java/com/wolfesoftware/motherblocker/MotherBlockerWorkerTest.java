package com.wolfesoftware.motherblocker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class MotherBlockerWorkerTest {
	String locationOfWebServerLogFiles = null;
	String locationOfWhiteList = null;
	String locationToPutBanList = null;
	String locationToPutStats = "target";
	String logFilePattern = "(\\S+)\\s\\[(.*)\\]\\s(.*)";
	String dateFormat = "dd/MM/yyyy:HH:mm:ss Z";
	String logFileOrdering = "IP,DATE,REQUEST";
	
	@Before
	public void init(){
		URL logFile = this.getClass().getClassLoader().getResource("apache_medium.log");
		locationOfWebServerLogFiles = logFile.getPath();
	}
	
	@Test
	public void shouldCreateStatsPageFromLogFile() throws IOException, ParseException {
		MotherBlockerWorker motherBlockerWorker = new MotherBlockerWorker(locationOfWebServerLogFiles, locationOfWhiteList, locationToPutBanList, locationToPutStats,logFilePattern, dateFormat, logFileOrdering);
		motherBlockerWorker.createStatsPage();
		String content = new String(Files.readAllBytes(Paths.get("target/stats.txt")));
		assertTrue(content.length()>1);
	}

}
