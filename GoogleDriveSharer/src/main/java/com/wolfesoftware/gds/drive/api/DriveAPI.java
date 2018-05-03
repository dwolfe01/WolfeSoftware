package com.wolfesoftware.gds.drive.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.wolfesoftware.gds.configuration.Configuration;
import com.wolfesoftware.gds.drive.DriveFactory;

public class DriveAPI {
	
	public List<File> listFiles(int x) throws IOException {
		FileList result = DriveFactory.getDrive().files().list().setPageSize(x).setQ("'" + Configuration.get("folder.in.google.drive") + "' in parents and trashed = false").setOrderBy("modifiedTime desc").setFields("files/name, files/mimeType, files/id, files/parents").execute();
		List<File> files = result.getFiles();
		return files;
	}

	public void upload(java.io.File uploadFile) throws IOException {
		File fileMetadata = new File();
		fileMetadata.setName("photo.jpg");
		FileContent mediaContent = new FileContent("image/jpeg", uploadFile);
		DriveFactory.getDrive().files().create(fileMetadata, mediaContent)
		    .setFields("id")
		    .execute();
	}

}
