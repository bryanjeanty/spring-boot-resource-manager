package com.resource.manager.resource.repository;

import java.util.List;

import com.resource.manager.resource.entity.Record;

public interface ProjectCustomMethods {

    public List<Record> findAllProjects(int version);
    
    public List<Record> findAllProjects(String file);
    
    public List<Record> findAllProjects();

    public Record findProjectById(int projectId);

    public Record updateProjectById(int projectId, Record record);

    public Record deleteProjectById(int projectId);
}