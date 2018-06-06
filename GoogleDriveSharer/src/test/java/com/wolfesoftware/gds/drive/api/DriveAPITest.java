package com.wolfesoftware.gds.drive.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import com.google.api.services.drive.model.File;
import com.wolfesoftware.gds.configuration.Configuration;

public class DriveAPITest {
	
	DriveAPI driveAPI = new DriveAPI();
	
	@Test
	//TODO: assumes more than 10 files are present
	public void shouldGet10FilesByMostRecent() throws IOException {
		List<File> listFiles = driveAPI.listFiles(10, Configuration.get("folder.in.google.drive"));
		assertEquals(listFiles.size(), 10);
		assertNotNull(listFiles.get(0).getCreatedTime());
	}

	@Test
	public void shouldUploadAndDeleteFile() throws IOException {
		assertTestFolderSize(0);
		java.io.File file = new java.io.File(this.getClass().getClassLoader().getResource("images/download.jpeg").getFile());
		String id = driveAPI.upload(Files.readAllBytes(file.toPath()), Configuration.get("test.folder.in.google.drive"), file.getName());
		assertTestFolderSize(1);
		driveAPI.delete(id);
		assertTestFolderSize(0);
	}
	
	@Test
	public void shouldSetDescription() throws IOException {
		assertTestFolderSize(0);
		java.io.File file = new java.io.File(this.getClass().getClassLoader().getResource("images/download.jpeg").getFile());
		String id = driveAPI.upload(Files.readAllBytes(file.toPath()), Configuration.get("test.folder.in.google.drive"), file.getName());
		String description = "this is my test description 2";
		driveAPI.setDescription(id, description);
		File driveFile = driveAPI.getFile(id);
		assertEquals(description, driveFile.getDescription());
		driveAPI.delete(id);
		assertTestFolderSize(0);
	}

	private void assertTestFolderSize(int size) throws IOException {
		List<File> listFiles = driveAPI.listFiles(10, Configuration.get("test.folder.in.google.drive"));
		assertEquals(size,listFiles.size());
	}

}
