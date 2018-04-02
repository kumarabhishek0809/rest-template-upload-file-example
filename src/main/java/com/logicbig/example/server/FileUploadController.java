package com.logicbig.example.server;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("user-file") MultipartFile multipartFile) throws IOException {
		String name = multipartFile.getOriginalFilename();
		System.out.println("File name: " + name);
		// todo save to a file via multipartFile.getInputStream()
		byte[] bytes = multipartFile.getBytes();
		System.out.println("File uploaded content:\n" + new String(bytes));
		return "file uploaded";
	}

	@RequestMapping(value = "/filesUpload", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestParam("files") MultipartFile[] files) {
		try {
			System.out.println("Reached.");
			for (MultipartFile multipartFile : files) {
				byte[] bytes = multipartFile.getBytes();
				System.out.println("File uploaded content:\n" + new String(bytes));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>("MultipleFiles",HttpStatus.OK);
	}
}