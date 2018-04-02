package com.logicbig.example.client;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UploadMultipleFilesClient {

	// mvn tomcat7:run-war

	public static void main(String[] args) throws IOException {

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		for (int i = 0; i < 5; i++) {
			map.add("" + i, getUserFileResource());
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9090/upload/filesUpload", HttpMethod.POST,
				requestEntity, String.class);
		System.out.println("response status: " + response.getStatusCode());
		System.out.println("response body: " + response.getBody());

	}

	public static Resource getUserFileResource() throws IOException {
		// todo replace tempFile with a real file
		Path tempFile = Files.createTempFile("upload-test-file", ".txt");
		Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
		System.out.println("uploading: " + tempFile);
		File file = tempFile.toFile();
		// to upload in-memory bytes use ByteArrayResource instead
		return new FileSystemResource(file);
	}
}