package com.resource.manager.resource.controller;

import java.util.Map;
import java.util.List;

import javax.validation.Valid;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map createResourceRecord(@Valid @RequestBody Record record) {
    	return resourceService.saveResourceRecord(record);
    }

    @GetMapping
    @SuppressWarnings({"rawtypes"})
    public List getAllResources() {
        return resourceService.findAllResources();
    }

    @GetMapping("/{id}")
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map getResourceById(@PathVariable("id") int resourceId) {
        return resourceService.findResourceById(resourceId);
    }
    
    @PutMapping("/{id}")
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map updateResourceById(@PathVariable("id") int resourceId, @Valid @RequestBody Record record) {
    	return resourceService.updateResourceById(resourceId, record);
    }
    
    @DeleteMapping("/{id}")
    @SuppressWarnings({"rawtypes"})
    public @ResponseBody Map deleteResourceById(@PathVariable("id") int resourceId) {
    	return resourceService.deleteResourceById(resourceId);
    }
}