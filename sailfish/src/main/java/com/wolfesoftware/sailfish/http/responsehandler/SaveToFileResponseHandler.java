package com.wolfesoftware.sailfish.http.responsehandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

public class SaveToFileResponseHandler implements ResponseHandler<StatusLine> {

	private File directory;
	private static long counter;

	public SaveToFileResponseHandler(File file) {
		this.directory = file;
	}
	

	@Override
	public StatusLine handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		String filename = "file" + counter++ + ".html";
		System.out.println("writing" + filename);
		FileOutputStream outputStream = new FileOutputStream(new File(directory,filename));//TODO:be nice if we could use better file names here.
		OutputStreamResponseHandler outputStreamResponseHandler = new OutputStreamResponseHandler(outputStream);
		outputStreamResponseHandler.handleResponse(response);
		outputStream.flush();
		outputStream.close();
		return response.getStatusLine();
	}

}
