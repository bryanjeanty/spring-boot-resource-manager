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
	
	public Map saveResourceRecord(Record record) {
		Resource resource = new Resource();
		resource.setRecordId(record.getId());
		resourceRepository.save(resource);
			
		recordRepository.save(record);
			
		Map myMap = new LinkedHashMap();
		List<String> keysList = new ArrayList<String>(Arrays.asList(record.getKeys().split(", ")));
		List<String> valuesList = new ArrayList<String>(Arrays.asList(record.getKeyValues().split(", ")));
			
		myMap.put("id", resource.getId());
		myMap.put("type", record.getType());
			
		for (int i = 0; i < keysList.size(); i++) {
			myMap.put(keysList.get(i), valuesList.get(i));
		}
			
		myMap.put("dataType", record.getDataType());
			
		return myMap;
	}
	
	public List findAllResources() {
		Map<Resource, Record> resourceRecords = recordRepository.findAllResources();
		List myArr = new ArrayList();
		
		for(Map.Entry<Resource, Record> entry : resourceRecords.entrySet()) {
			Map myMap = new LinkedHashMap();
			List<String> keysList = new ArrayList<String>(Arrays.asList(entry.getValue().getKeys().split(", ")));
			List<String> valuesList = new ArrayList<String>(Arrays.asList(entry.getValue().getKeyValues().split(", ")));
			
			myMap.put("id", entry.getKey().getId());
            myMap.put("type", entry.getValue().getType());
            for (int i = 0; i < keysList.size(); i++) {
                myMap.put(keysList.get(i), valuesList.get(i));
            }
            myMap.put("dataType", entry.getValue().getDataType());

            myArr.add(myMap);
		}
		
		return myArr;
	}
	
	public Map findResourceById(long resourceId) {
		Record resourceRecord = recordRepository.findResourceById(resourceId);
		
		Map myMap = new LinkedHashMap();
		List<String> keysList = new ArrayList<String>(Arrays.asList(resourceRecord.getKeys().split(", ")));
		List<String> valuesList = new ArrayList<String>(Arrays.asList(resourceRecord.getKeyValues().split(", ")));
		
		myMap.put("id", resourceId);
		myMap.put("type", resourceRecord.getType());
		
		for (int i = 0; i < keysList.size(); i++) {
			myMap.put(keysList.get(i), valuesList.get(i));
        }
        
        myMap.put("dataType", resourceRecord.getDataType());
        
		return myMap;
	}
}







