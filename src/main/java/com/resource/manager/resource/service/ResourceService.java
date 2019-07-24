package com.resource.manager.resource.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.LinkedHashMap;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.entity.Resource;
import com.resource.manager.resource.repository.RecordRepository;
import com.resource.manager.resource.repository.ResourceRepository;
import com.resource.manager.resource.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResourceService {
	
	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	public ResourceService(RecordRepository recordRepository, ResourceRepository resourceRepository) {
		this.recordRepository = recordRepository;
		this.resourceRepository = resourceRepository;
	}

    @SuppressWarnings({"rawtypes"})
	public Map saveResourceRecord(Record record) {
		Resource resource = new Resource();
		Resource newResource = resourceRepository.save(resource);
		
		record.setTypeId(newResource.getId());
		Record newRecord = recordRepository.save(record);
						
		return convertResourceToMap(newRecord);
	}
	
    @SuppressWarnings({"rawtypes", "unchecked"})
	public List findAllResources() {
		List<Record> resources = recordRepository.findAllResources();
		List myArr = new ArrayList();
		
		for(Record resource : resources) {
            myArr.add(convertResourceToMap(resource));
		}
		
		return myArr;
	}
	
    @SuppressWarnings({"rawtypes"})
	public Map findResourceById(int resourceId) {
		Record resource = recordRepository.findResourceById(resourceId);
		return convertResourceToMap(resource);
    }

    @SuppressWarnings({"rawtypes"})
    public Map updateResourceById(int resourceId, Record record) {
    	Resource updatedResource = resourceRepository
    									.findById(resourceId)
    									.orElseThrow(() -> new ResourceNotFoundException(resourceId));
    	resourceRepository.save(updatedResource);
    	
        Record updatedRecord = recordRepository.updateResourceById(updatedResource.getId(), record);
        return convertResourceToMap(updatedRecord);
    }

    @SuppressWarnings({"rawtypes"})
    public Map deleteResourceById(int resourceId) {
        Record deletedResource = recordRepository.deleteResourceById(resourceId);
        return convertResourceToMap(deletedResource);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map convertResourceToMap(Record resource) {
        Map resourceMap = new LinkedHashMap();

        List<String> keysList = new ArrayList<String>(Arrays.asList(resource.getKeys().split(",")));
        List<String> valuesList = new ArrayList<String>(Arrays.asList(resource.getKeyValues().split(",")));
        List<String> dataTypesList = new ArrayList<String>(Arrays.asList(resource.getDataTypes().split(",")));

        resourceMap.put("id", resource.getTypeId());
        resourceMap.put("type", resource.getType());

        for (int i = 0; i < valuesList.size(); i++) {
            Map<String, String> myValuesMap = new LinkedHashMap<String, String>();

            myValuesMap.put("value", valuesList.get(i));
            myValuesMap.put("dataType", dataTypesList.get(i));

            resourceMap.put(keysList.get(i), myValuesMap);
        }
        return resourceMap;
    }
}