package com.resource.manager.resource.service;

import java.util.List;
import java.util.Map;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.repository.ProjectRepository;
import com.resource.manager.resource.repository.RecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final RecordRepository recordRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(RecordRepository recordRepository, ProjectRepository projectRepository) {
        this.recordRepository = recordRepository;
        this.projectRepository = projectRepository;
    }

    @SuppressWarnings({"rawtypes"})
    public Map saveResourceRecord(Record record) {
        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public List findAllProject() {
        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public Map findProjectById(int projectId) {
        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public Map updateProjectById(int projectId, Record record) {
        return null;
    }

    @SuppressWarnings({"rawtypes"})
    public Map deleteProjectById(int projectId) {
        return null;
    }
}