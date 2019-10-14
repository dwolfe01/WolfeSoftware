package com.wolfesoftware.sailfish.http.worker.factory;

import com.wolfesoftware.sailfish.core.concurrency.WorkerFactory;
import com.wolfesoftware.sailfish.http.logfilereader.LogFileReader;
import com.wolfesoftware.sailfish.http.logfilereader.exceptions.BadLogFileException;
import com.wolfesoftware.sailfish.http.responsehandler.ResponseHandlerFactory;
import com.wolfesoftware.sailfish.http.runnable.httpuser.HttpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class HttpUserContinualWorkerFactoryFromLogFile extends WorkerFactory {

    private final ResponseHandlerFactory responseHandlerFactory;
    private final List<String> requests;
    static final org.slf4j.Logger Logger = LoggerFactory.getLogger(HttpUserWorkerFactoryFromLogFile.class);
    private int positionInRequests = 0;

    public HttpUserContinualWorkerFactoryFromLogFile(LogFileReader logFileReader, ResponseHandlerFactory responseHandlerFactory) throws BadLogFileException {
        this.responseHandlerFactory = responseHandlerFactory;
        requests = Collections.synchronizedList(logFileReader.getAsListOfUrls());
    }

    @Override
    public HttpUser getWorker() {
        final HttpUser user = new HttpUser(responseHandlerFactory);
        String url = "";
        try {
            for (int x = 0; x < 4; x++) {
                url = getNextRequest();
                if (!url.equals(""))
                    user.addGetRequest(url);
            }
        } catch (URISyntaxException e) {
            Logger.info("Problem with: " + url);
            e.printStackTrace();
        }
        return user;
    }

    private String getNextRequest() {
        if (requests.size() == positionInRequests){
            positionInRequests = 0;
        }
        return requests.get(positionInRequests++);
    }
}

