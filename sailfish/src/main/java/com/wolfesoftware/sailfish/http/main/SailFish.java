package com.wolfesoftware.sailfish.http.main;

import java.io.File;
import java.io.IOException;

import com.wolfesoftware.sailfish.http.worker.factory.HttpUserContinualWorkerFactoryFromLogFile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory.ResponseHandlers;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.http.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;

public class SailFish {

    private static final Logger Logger = LoggerFactory.getLogger(SailFish.class);


    public static void main(String[] args) throws
            Exception {
        String fileName = args[0];
        int threadCount = Integer.parseInt(args[1]);
        Logger.info(("Running SailFish with file: " + fileName
                + " thread count: " + threadCount));
        SailFish sailfish = new SailFish();
        File logFile = new File(fileName);
        Logger.info(logFile.getAbsolutePath());
        ReadySteadyThread readySteadyThread = sailfish.getReadySteadyThread(logFile, threadCount);
        readySteadyThread.go();
    }

    protected ReadySteadyThread getReadySteadyThread(File logFile, int threadCount) throws BadLogFileException, JsonParseException, JsonMappingException, IOException {
        WorkerFactory factory = null;
        if (logFile.getAbsolutePath().endsWith("json")) {
            ResponseHandlerFactory responseHandlerFactory = new ResponseHandlerFactory(ResponseHandlers.OUTPUTSTREAM);
            factory = new HttpUserWorkerFactoryFromJSONFile(FileUtils.readFileToString(logFile), responseHandlerFactory);
        }
        if (logFile.getAbsolutePath().endsWith("uptime")) {
            Logger.info(FileUtils.readFileToString(logFile));
            LogFileReader logFileReader = new LogFileReader(logFile);
            factory = new UptimeHttpUserWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory());
        }
        if (logFile.getAbsolutePath().endsWith("log")) {
            LogFileReader logFileReader = new LogFileReader(logFile);
            factory = new HttpUserWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory(ResponseHandlers.OUTPUTSTREAM));
        }
        if (logFile.getAbsolutePath().endsWith("continual")) {
            LogFileReader logFileReader = new LogFileReader(logFile);
            factory = new HttpUserContinualWorkerFactoryFromLogFile(logFileReader, new ResponseHandlerFactory(ResponseHandlers.OUTPUTSTREAM));
        }
           return new ReadySteadyThread(threadCount, factory);
    }
}