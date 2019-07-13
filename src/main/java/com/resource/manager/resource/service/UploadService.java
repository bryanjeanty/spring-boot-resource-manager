package com.resource.manager.resource.service;

import com.resource.manager.resource.entity.Record;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

    public List<Record> saveFileRecords(MultipartFile file);

    public List<Record> getFileRecords(String filename);

    public void deleteFileRecords(String filename);
}