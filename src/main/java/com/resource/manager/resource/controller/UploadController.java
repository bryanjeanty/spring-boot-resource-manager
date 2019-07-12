package com.resource.manager.resource.controller;

import java.util.List;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.service.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth/uploads")
@CrossOrigin(origins = "http://localhost:4200")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	public UploadController(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	@PostMapping
	public @ResponseBody ResponseEntity<List<Record>> uploadFile(@RequestParam("file") MultipartFile file) {
		List<Record> fileRecordsList = uploadService.saveFileRecords(file);
		return new ResponseEntity<List<Record>>(fileRecordsList, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/{filename}") public @ResponseBody List getAllFileRecords() {
	 * return null; }
	 * 
	 * @DeleteMapping("/{filename}") public ResponseEntity<String>
	 * deleteRecordsByFilename() { return null; }
	 */
}