package com.hardik.flenderson.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.Base64;
import com.hardik.flenderson.storage.StorageService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.TRACE })
public class FileController {

	private final StorageService storageService;

	@PutMapping("v1/file")
	public String retreiveBinaryFileFromKey(@RequestBody(required = true) final String key)
			throws IOException {
		return Base64.encodeAsString(storageService.getFile(key).getObjectContent().readAllBytes());
	}

}
