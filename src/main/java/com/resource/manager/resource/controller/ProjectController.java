package com.resource.manager.resource.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/v1/auth/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map createProjectRecord(@Valid @RequestBody Record record) {
        return projectService.saveProjectRecord(record);
    }

    @GetMapping
    @SuppressWarnings({"rawtypes"})
    public List getAllProjects() {
        return projectService.findAllProjects();
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