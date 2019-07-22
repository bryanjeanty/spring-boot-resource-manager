package com.resource.manager.resource.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.resource.manager.resource.entity.Project;
import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.repository.ProjectRepository;
import com.resource.manager.resource.repository.RecordRepository;
import com.resource.manager.resource.exception.ProjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private RecordRepository recordRepository;
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectService(RecordRepository recordRepository, ProjectRepository projectRepository) {
        this.recordRepository = recordRepository;
        this.projectRepository = projectRepository;
    }

    @SuppressWarnings({"rawtypes"})
    public Map saveProjectRecord(Record record) {
        Project project = new Project();
        project.setVersionNumber(record.getVersion());
        Project newProject = projectRepository.save(project);

        record.setTypeId(newProject.getId());
        Record newRecord = recordRepository.save(record);

        return convertProjectToMap(newRecord);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List findAllProjects() {
        List<Record> projects = recordRepository.findAllProjects();

        List myArr = new ArrayList();

        for (Record project : projects) {
            myArr.add(convertProjectToMap(project));
        }
        return myArr;
    }

    @SuppressWarnings({"rawtypes"})
    public Map findProjectById(int projectId) {
        Record project = recordRepository.findProjectById(projectId);
        return convertProjectToMap(project);
    }

    @SuppressWarnings({"rawtypes"})
    public Map updateProjectById(int projectId, Record record) {
    	Project updatedProject = projectRepository.findById(projectId)
    											.orElseThrow(() -> new ProjectNotFoundException(projectId));
    	updatedProject.setVersionNumber(record.getVersion());
    	projectRepository.save(updatedProject);
    	
        Record updatedRecord = recordRepository.updateProjectById(updatedProject.getId(), record);
        return convertProjectToMap(updatedRecord);
    }

    @SuppressWarnings({"rawtypes"})
    public Map deleteProjectById(int projectId) {
        Record deletedProject = recordRepository.deleteProjectById(projectId);
        return convertProjectToMap(deletedProject);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map convertProjectToMap(Record project) {
    	
    	
        Map projectMap = new LinkedHashMap();

        List<String> keysList = new ArrayList<String>(Arrays.asList(project.getKeys().split(",")));
        List<String> valuesList = new ArrayList<String>(Arrays.asList(project.getKeyValues().split(",")));
        List<String> dataTypesList = new ArrayList<String>(Arrays.asList(project.getDataTypes().split(",")));

        projectMap.put("id", project.getTypeId());
        projectMap.put("type", project.getType());

        for (int i = 0; i < valuesList.size(); i++) {
            Map<String, String> myValuesMap = new LinkedHashMap<String, String>();

            myValuesMap.put("value", valuesList.get(i));
            myValuesMap.put("dataType", dataTypesList.get(i));

            projectMap.put(keysList.get(i), myValuesMap);
        }
        return projectMap;
    }
}