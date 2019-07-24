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
    public Map saveProjectRecord(int version, Record record) {
        Project project = new Project();
        project.setVersion(version);
        Project newProject = projectRepository.save(project);

        record.setTypeId(newProject.getId());
        Record newRecord = recordRepository.save(record);

        return convertProjectToMap(version, newRecord);
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List findAllProjects(int version) {
        List<Record> projects = recordRepository.findAllProjects(version);
        	
        List myArr = new ArrayList();

        for (Record project : projects) {
            myArr.add(convertProjectToMap(version, project));
        }
        return myArr;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List findAllProjects(String filename) {
        List<Record> projects = recordRepository.findAllProjects(filename);
        	
        List myArr = new ArrayList();

        for (Record project : projects) {
            myArr.add(convertProjectToMap(filename, project));
        }
        return myArr;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List findAllProjects() {    	
    	List<Project> projects = projectRepository.findAll();
        List<Record> projectRecords = recordRepository.findAllProjects();
        	
        List myArr = new ArrayList();

        for (int i = 0; i < projectRecords.size(); i++) {
        	if (projects.get(i).getVersion() == 0) {
        		myArr.add(convertProjectToMap(projects.get(i).getFilename(), projectRecords.get(i)));
        	} else {
        		myArr.add(convertProjectToMap(projects.get(i).getVersion(), projectRecords.get(i)));
        	}
        }
        return myArr;
    }

    @SuppressWarnings({"rawtypes"})
    public Map findProjectById(int projectId) {
    	Project foundProject = projectRepository
    								.findById(projectId)
    								.orElseThrow(() -> new ProjectNotFoundException(projectId));
    	
        Record foundRecord = recordRepository.findProjectById(projectId);
        return convertProjectToMap(foundProject.getVersion(), foundRecord);
    }

    @SuppressWarnings({"rawtypes"})
    public Map updateProjectById(int projectId, Record record) {
    	Project updatedProject = projectRepository
    									.findById(projectId)
    									.orElseThrow(() -> new ProjectNotFoundException(projectId));
    	projectRepository.save(updatedProject);
    	
        Record updatedRecord = recordRepository.updateProjectById(updatedProject.getId(), record);
        return convertProjectToMap(updatedProject.getVersion(), updatedRecord);
    }

    @SuppressWarnings({"rawtypes"})
    public Map deleteProjectById(int projectId) {
    	Project deletedProject = projectRepository
    									.findById(projectId)
    									.orElseThrow(() -> new ProjectNotFoundException(projectId));
    	
        Record deletedRecord = recordRepository.deleteProjectById(projectId);
        return convertProjectToMap(deletedProject.getVersion(), deletedRecord);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map convertProjectToMap(int version, Record project) {
    	
    	
        Map projectMap = new LinkedHashMap();

        List<String> keysList = new ArrayList<String>(Arrays.asList(project.getKeys().split(",")));
        List<String> valuesList = new ArrayList<String>(Arrays.asList(project.getKeyValues().split(",")));
        List<String> dataTypesList = new ArrayList<String>(Arrays.asList(project.getDataTypes().split(",")));

        projectMap.put("id", project.getTypeId());
        projectMap.put("version", version);
        projectMap.put("type", project.getType());

        for (int i = 0; i < valuesList.size(); i++) {
            Map<String, String> myValuesMap = new LinkedHashMap<String, String>();

            myValuesMap.put("value", valuesList.get(i));
            myValuesMap.put("dataType", dataTypesList.get(i));

            projectMap.put(keysList.get(i), myValuesMap);
        }
        return projectMap;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map convertProjectToMap(String filename, Record project) {
    	
    	
        Map projectMap = new LinkedHashMap();

        List<String> keysList = new ArrayList<String>(Arrays.asList(project.getKeys().split(",")));
        List<String> valuesList = new ArrayList<String>(Arrays.asList(project.getKeyValues().split(",")));
        List<String> dataTypesList = new ArrayList<String>(Arrays.asList(project.getDataTypes().split(",")));

        projectMap.put("id", project.getTypeId());
        projectMap.put("file", filename);
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