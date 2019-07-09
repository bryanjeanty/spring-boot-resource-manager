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
		Resource newResource = resourceRepository.save(resource);
		
		record.setTypeId(newResource.getId());
		Record newRecord = recordRepository.save(record);
			
		Map myMap = new LinkedHashMap();
		List<String> keysList = new ArrayList<String>(Arrays.asList(newRecord.getKeys().split(", ")));
		List<String> valuesList = new ArrayList<String>(Arrays.asList(newRecord.getKeyValues().split(", ")));
		List<String> dataTypesList = new ArrayList<String>(Arrays.asList(newRecord.getDataTypes().split(", ")));
		
		myMap.put("id", newRecord.getTypeId());
		myMap.put("type", newRecord.getType());
		
		for (int i = 0; i < valuesList.size(); i++) {
			Map<String, String> myValuesMap = new LinkedHashMap<String, String>();
			
			myValuesMap.put("value", valuesList.get(i));
			myValuesMap.put("dataType", dataTypesList.get(i));
			
			myMap.put(keysList.get(i), myValuesMap);
		}
			
		return myMap;
	}
	
	public List findAllResources() {
		List<Record> resources = recordRepository.findAllResources();
		List myArr = new ArrayList();
		
		for(Record resource : resources) {
			Map myMap = new LinkedHashMap();
			List<String> keysList = new ArrayList<String>(Arrays.asList(resource.getKeys().split(", ")));
			List<String> valuesList = new ArrayList<String>(Arrays.asList(resource.getKeyValues().split(", ")));
			List<String> dataTypesList = new ArrayList<String>(Arrays.asList(resource.getDataTypes().split(", ")));
			
			myMap.put("id", resource.getTypeId());
            myMap.put("type", resource.getType());
			
			for (int i = 0; i < valuesList.size(); i++) {
				Map<String, String> myValuesMap = new LinkedHashMap<String, String>();
				
				myValuesMap.put("values", valuesList.get(i));
				myValuesMap.put("dataType", dataTypesList.get(i));
				
				myMap.put(keysList.get(i), myValuesMap);
			}

            myArr.add(myMap);
		}
		
		return myArr;
	}
	
	public Map findResourceById(int resourceId) {
		Record resource = recordRepository.findResourceById(resourceId);
		
		Map myMap = new LinkedHashMap();
		List<String> keysList = new ArrayList<String>(Arrays.asList(resource.getKeys().split(", ")));
		List<String> valuesList = new ArrayList<String>(Arrays.asList(resource.getKeyValues().split(", ")));
		List<String> dataTypesList = new ArrayList<String>(Arrays.asList(resource.getDataTypes().split(", ")));
		
		myMap.put("id", resource.getTypeId());
		myMap.put("type", resource.getType());
		
		for (int i = 0; i < valuesList.size(); i++) {
			Map<String, String> myValuesMap = new LinkedHashMap<String, String>();
			
			myValuesMap.put("value", valuesList.get(i));
			myValuesMap.put("dataType", dataTypesList.get(i));
			
			myMap.put(keysList.get(i), myValuesMap);
		}
		
		return myMap;
	}
}







