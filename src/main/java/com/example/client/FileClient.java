package com.example.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.FileMeta;

@FeignClient(name = "file-service", url = "http://file-service:8083/api/files")
public interface FileClient {

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<FileMeta> uploadFile(@RequestPart("file") MultipartFile file,
			@RequestPart("articleId") int articleId);

	@GetMapping("/{articleId}")
	List<FileMeta> getFilesByArticleId(@PathVariable("articleId") int articleId);
}