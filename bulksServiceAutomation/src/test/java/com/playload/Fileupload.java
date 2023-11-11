package com.playload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Fileupload {

	public String files(String filename) throws IOException {
		byte[] b = Files.readAllBytes(Paths.get(filename));
		String body = new String(b);
		return body;
	}

}
