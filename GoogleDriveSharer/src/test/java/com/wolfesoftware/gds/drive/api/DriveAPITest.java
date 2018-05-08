package com.wolfesoftware.gds.drive.api;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.api.services.drive.model.File;

public class DriveAPITest {
	
	@Test
	public void shouldGet10FilesByMostRecent() throws IOException {
		DriveAPI driveAPI = new DriveAPI();
		List<File> listFiles = driveAPI.listFiles(10);
		for (File f:listFiles) {
			System.out.println(f.toPrettyString());
		}
		assertEquals(listFiles.size(), 10);
	}

	@Test
	@Ignore
	public void shouldUploadFile() throws IOException {
		DriveAPI driveAPI = new DriveAPI();
		java.io.File file = new java.io.File(this.getClass().getClassLoader().getResource("images/download.jpeg").getFile());
		driveAPI.upload(file);
	}

}
