package com.resource.manager.resource.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.resource.manager.resource.entity.Data;
import com.resource.manager.resource.entity.DataTables;
import com.resource.manager.resource.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public @ResponseBody ResponseEntity<Data> createRecord(@Valid @RequestBody Data data) {
        // if version number from request body is 0, then ask user to provide a version
        // number
        if (data.getVersionNumber() == 0) {
            data.setMessage("Please provide a version number!");
            return ResponseEntity.badRequest().body(data);
        }

        // save the version number in a variable
        int versionNumber = data.getVersionNumber();

        // if column name is empty...
        if (data.getColumnNames().equals("")) {
            // update message
            data.setMessage(String.format("Version number: %d provided. No column names provided. ", versionNumber));
            // set version number to value returned from function (same number if table
            // exist; different number if new table)
            versionNumber = resourceService.createTable(resourceService.doesTableExist(data));
            return ResponseEntity.badRequest().body(data);
        } else {
            data.setMessage(String.format("Version number: %d, and column names: %s provided. ", versionNumber,
                    data.getColumnNames()));
            String[] columnNames = data.getColumnNames().split(", ");
            versionNumber = resourceService.createTable(resourceService.doesTableExist(data), columnNames);
        }

        return ResponseEntity.ok().body(data);

        // Data newResource = resourceService.createRecordByVersionNumber(versionNumber,
        // data);
        // if (data.getVersionNumber() != newResource.getVersionNumber()) {
        // DataTables dt = new DataTables();
        // dt.setRefTableVersionNum(data.getVersionNumber());
        // dt.setDerTableVersionNum(newResource.getVersionNumber());
        // resourceService.save(dt);
        // }

        // String successMessage = data.getMessage();
        // successMessage += "New Record successfully inserted into table: data" +
        // data.getVersionNumber();
        // newResource.setMessage(successMessage);
        // return ResponseEntity.ok().body(newResource);
    }

    @GetMapping
    public List<Data> getAllRecords(@Valid @RequestBody Data data) {
        return resourceService.getAllDataByVersionNumber(data.getVersionNumber());
    }

    @GetMapping("/{id}")
    public List<Data> getRecordById(@PathVariable("id") int dataId, @Valid @RequestBody Data data) {
        return resourceService.getDataByVersionNumberAndId(data.getVersionNumber(), dataId);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<HashMap<Data, String>> updateRecordById(@PathVariable("id") int dataId,
            @Valid @RequestBody Data data) {
        HashMap<Data, String> response = new HashMap<Data, String>();
        if (data.getVersionNumber() == 0) {
            response.put(data, "Please provide a version number");
            return ResponseEntity.badRequest().body(response);
        }
        Data updatedResource = resourceService.updateRecordByVersionNumberAndId(data.getVersionNumber(), dataId, data);
        if (data.getVersionNumber() != updatedResource.getVersionNumber()) {
            DataTables dt = new DataTables();
            dt.setRefTableVersionNum(data.getVersionNumber());
            dt.setDerTableVersionNum(updatedResource.getVersionNumber());
            resourceService.save(dt);
        }
        response.put(updatedResource, "Successfully updated resource");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteRecordById(@PathVariable("id") int dataId,
            @Valid @RequestBody Data data) {
        if (data.getVersionNumber() == 0) {
            return new ResponseEntity<>("Please provide a version number", HttpStatus.BAD_REQUEST);
        }
        int rowsAffected = resourceService.destroyByVersionNumberAndId(data.getVersionNumber(), dataId);
        return new ResponseEntity<>(String.format("Success: %d rows deleted", rowsAffected), HttpStatus.NO_CONTENT);
    }
}