package com.resource.manager.resource.controller;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import javax.validation.Valid;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map createProjectRecord(@RequestParam String version, @Valid @RequestBody Record record) {
    	Map<String, String> response = null;
    	
    	if (version != null) {
    		
    		response = projectService.saveProjectRecord(Integer.parseInt(version), record);
    		response.put("message", "Success! Project created");
    		
    	} else {
    		
    		response = new LinkedHashMap<String, String>();
    		response.put("message", "Error! Please provide a version number");
    	}
    	
        return response;
    }

    @GetMapping
    @SuppressWarnings({"rawtypes"})
    public List getAllProjects(@RequestParam(required = false) String version, @RequestParam(required = false) String filename) {
    	List myList = null;
    	
    	if (version != null && filename == null) {
    		
    		myList = projectService.findAllProjects(Integer.parseInt(version));
    	} else if (version == null && filename != null) {
    		
    		myList = projectService.findAllProjects(filename);
    	} else {
    		
    		myList = projectService.findAllProjects();
    	}
        return myList;
    }

    @GetMapping("/{id}")
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map getProjectById(@PathVariable("id") int projectId) {
        return projectService.findProjectById(projectId);
    }

    @PutMapping("/{id}")
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map updateProjectById(@PathVariable("id") int projectId, @Valid @RequestBody Record record) {
        return projectService.updateProjectById(projectId, record);
    }

    @DeleteMapping("/{id}")
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map deleteProjectById(@PathVariable("id") int projectId) {
        return projectService.deleteProjectById(projectId);
    }
}