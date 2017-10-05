package com.wolfesoftware.motherblocker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class MotherBlocker {

	private static final Logger Logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MotherBlocker.class);

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	// default settings
	private String runEveryXSeconds = "30";

	// these should be moved to a properties file AND some sort of configuration
	// object
	private String locationOfWebServerLogFiles = null;
	private String locationOfWhiteList = null;
	private String locationToPutBanList = null;
	private String locationToPutStats = "target";
	private String logFilePattern = "(\\S+)\\s\\[(.*)\\]\\s(.*)";
	private String dateFormat = "dd/MM/yyyy:HH:mm:ss Z";
	private String logFileOrdering = "IP,DATE,REQUEST";

	protected MotherBlocker() {
		// make sure this class cannot be instantiated
	}

	public static void main(String[] args) {
		Logger.info(
				"Usage:  java -jar SuperFastSearch.jar locationOfWebServerLogFiles:/tmp, locationOfWhiteList:/whitelist.txt, locationToPutBanList:/tmp, locationToPutStats:/tmp,logFilePattern:(\\S+\\s\\S+)\\s(.*)\\s(.*), dateFormat:dd/MM/yyyy:HH:mm:ss Z, logFileOrdering:IP,DATE,REQUEST executeHowOftenInSeconds:30");
		MotherBlocker mb = new MotherBlocker();
		mb.go(args);
	}

	public void go(String[] args) {
		// INITIAL
		// verify location of log files
		// verify how often it should be run (X)
		// DO THIS EVERY X MINUTES
		locationOfWebServerLogFiles = args[0];
		locationOfWhiteList = args[1];
		locationToPutBanList = args[2];
		locationToPutStats = args[3];
		logFilePattern = args[4];
		dateFormat = args[5];
		logFileOrdering = args[6];
		runEveryXSeconds = args[7];
		Logger.info("Running MotherBlocker with these parameters");
		outputParams();
		Runnable worker = getWorker();
		execute(worker, Integer.parseInt(runEveryXSeconds));
		// get whitelist
		// read in log files
		// create stats page
		// publish stats page to apache
		// create ban list
	}

	private void outputParams() {
		Logger.info("locationOfWebServerLogFiles: " + locationOfWebServerLogFiles);
		Logger.info("locationOfWhiteList: " + locationOfWhiteList);
		Logger.info("locationToPutBanList: " + locationToPutBanList);
		Logger.info("locationToPutStats: " + locationToPutStats);
		Logger.info("logFilePattern: " + logFilePattern);
		Logger.info("dateFormat: " + dateFormat);
		Logger.info("logFileOrdering: " + logFileOrdering);
		Logger.info("runEveryXSeconds: " + runEveryXSeconds);
	}

	private Runnable getWorker() {
		return new MotherBlockerWorker(locationOfWebServerLogFiles, locationOfWhiteList, locationToPutBanList,
				locationToPutStats, logFilePattern, dateFormat, logFileOrdering);
	}

	protected void execute(Runnable worker, int runEveryXSeconds) {
		scheduler.scheduleAtFixedRate(worker, 0, runEveryXSeconds, TimeUnit.SECONDS);
	}

}
