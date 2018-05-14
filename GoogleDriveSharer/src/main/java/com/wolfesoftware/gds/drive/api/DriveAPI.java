package com.wolfesoftware.gds.drive.api;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.wolfesoftware.gds.drive.DriveFactory;

public class DriveAPI {
	
	public List<File> listFiles(int x, String folder) throws IOException {
		FileList result = DriveFactory.getDrive().files().list().setPageSize(x).setQ("'" + folder + "' in parents and trashed = false").setOrderBy("modifiedTime desc").setFields("files/name, files/mimeType, files/id, files/parents").execute();
		List<File> files = result.getFiles();
		return files;
	}

	public String upload(byte[] uploadData, String folder, String name) throws IOException {
		File fileMetadata = new File();
		fileMetadata.setName(name);
		fileMetadata.setParents(Collections.singletonList(folder));
		ByteArrayContent byteArrayContent = new ByteArrayContent("image/jpeg", uploadData);
		File file = DriveFactory.getDrive().files().create(fileMetadata, byteArrayContent)
		    .setFields("id")
		    .execute();
		return file.getId();
	}

	public void delete(String id) throws IOException {
		DriveFactory.getDrive().files().delete(id).execute();
	}

}
