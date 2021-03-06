package com.hardik.flenderson.storage;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.hardik.flenderson.storage.bean.AmazonS3Bean;
import com.hardik.flenderson.storage.configuration.AmazonS3Configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@EnableConfigurationProperties(AmazonS3Configuration.class)
public class StorageService {

	private final AmazonS3 amazonS3;

	private final AmazonS3Configuration amazonS3Configuration;

	public void save(String key, MultipartFile multipartFile) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		try {
			amazonS3.putObject(amazonS3Configuration.getStorage().getBucketName(), key, multipartFile.getInputStream(),
					null);
		} catch (SdkClientException | IOException e) {
			log.error("UNABLE TO STORE FILE IN S3 FOR " + key + " " + LocalDateTime.now());
		}
	}

	public void save(String key, InputStream file) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.available());
			amazonS3.putObject(amazonS3Configuration.getStorage().getBucketName(), key, file, null);
		} catch (SdkClientException | IOException e) {
			log.error("UNABLE TO STORE FILE IN S3 FOR " + key + " " + LocalDateTime.now());
		}
	}

	public S3Object getFile(String key) {
		return amazonS3.getObject(amazonS3Configuration.getStorage().getBucketName(), key);
	}

	public void deleteFile(String key) {
		amazonS3.deleteObject(amazonS3Configuration.getStorage().getBucketName(), key);
	}

}
