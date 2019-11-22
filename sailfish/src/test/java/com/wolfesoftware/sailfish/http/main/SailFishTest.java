package com.wolfesoftware.sailfish.http.main;

import com.wolfesoftware.sailfish.core.concurrency.ReadySteadyThread;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserContinualWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromJSONFile;
import com.wolfesoftware.sailfish.http.worker.factory.HttpUserWorkerFactoryFromLogFile;
import com.wolfesoftware.sailfish.http.worker.factory.UptimeHttpUserWorkerFactoryFromLogFile;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SailFishTest {

    SailFish objectUnderTest = new SailFish();

    @Test
    public void shouldSetUpFromJSON() throws IOException, BadLogFileException, URISyntaxException {
        URI uri = this.getClass().getResource("/com/wolfesoftware/sailfish/json/httpuser/httpuser.json").toURI();
        ReadySteadyThread readySteadyThread = objectUnderTest.getReadySteadyThread(new File(uri), 10);
        assertTrue(readySteadyThread.getWorkerFactory() instanceof HttpUserWorkerFactoryFromJSONFile);
    }

    @Test
    public void shouldSetUpForContinual() throws IOException, BadLogFileException, URISyntaxException {
        URI uri = this.getClass().getResource("/urls.continual").toURI();
        ReadySteadyThread readySteadyThread = objectUnderTest.getReadySteadyThread(new File(uri), 10);
        assertTrue(readySteadyThread.getWorkerFactory() instanceof HttpUserContinualWorkerFactoryFromLogFile);
    }

    @Test
    public void shouldSetUpForUptime() throws IOException, BadLogFileException, URISyntaxException {
        URI uri = this.getClass().getResource("/urls.uptime").toURI();
        ReadySteadyThread readySteadyThread = objectUnderTest.getReadySteadyThread(new File(uri), 10);
        assertTrue(readySteadyThread.getWorkerFactory() instanceof UptimeHttpUserWorkerFactoryFromLogFile);
    }

    @Test
    public void shouldSetUpForLog() throws IOException, BadLogFileException, URISyntaxException {
        URI uri = this.getClass().getResource("/urls.log").toURI();
        ReadySteadyThread readySteadyThread = objectUnderTest.getReadySteadyThread(new File(uri), 10);
        assertTrue(readySteadyThread.getWorkerFactory() instanceof HttpUserWorkerFactoryFromLogFile);
    }

}