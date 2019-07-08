package com.resource.manager.resource.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.entity.Resource;

public class RecordRepositoryImpl implements RecordCustomMethods {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Map<Resource, Record> findAllResources() {
		String selectQuery = "SELECT type, keys, key_values, data_type FROM record WHERE type = 'resource'";

		List<String> typeArray = new ArrayList<String>();
		List<String> keysArray = new ArrayList<String>();
		List<String> keyValuesArray = new ArrayList<String>();
		List<String> dataTypeArray = new ArrayList<String>();
		
		Map<Resource, Record> resourceRecords = new HashMap<Resource, Record>();
		
		try {
		
			@SuppressWarnings("unchecked")
			List<Object[]> resources = entityManager.createNativeQuery(selectQuery).getResultList();
			
			for(Object[] resource : resources) {
				typeArray.add((String) resource[0]);
				keysArray.add((String) resource[1]);
				keyValuesArray.add((String) resource[2]);
				dataTypeArray.add((String) resource[3]);
			}
			
			for(int i = 0; i < typeArray.size(); i++) {
				Record record = new Record();
				Resource resource = new Resource();
				
				resource.setRecordId(record.getId());
				
				record.setType(typeArray.get(i));
				record.setKeys(keysArray.get(i));
				record.setKeyValues(keyValuesArray.get(i));
				record.setDataType(dataTypeArray.get(i));
				
				resourceRecords.put(resource, record);
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		return resourceRecords;
	}
	
	@Override
	public Record findResourceById(long resourceId) {
		String selectRecordIdQuery = "SELECT recordId FROM resource WHERE id ='" + resourceId + "'";
		int recordId = 0;
		Record record = new Record();
		
		try {
			
			recordId = (int) entityManager.createNativeQuery(selectRecordIdQuery, Integer.class).getSingleResult();
			if (recordId > 0) {
				String selectResourceQuery = "SELECT type, keys, key_values, data_type FROM record WHERE type = 'resource' AND id='" + recordId + "'";
				
				try {
				
					@SuppressWarnings("unchecked")
					List<Object[]> recordList = entityManager.createNativeQuery(selectResourceQuery).getResultList();
					
					for (Object[] recordItem : recordList) {
						record.setType((String) recordItem[0]);
						record.setKeys((String) recordItem[1]);
						record.setKeyValues((String) recordItem[2]);
						record.setDataType((String) recordItem[3]);
					}
					
				} catch (Exception ex) {
				
					ex.printStackTrace();
					
				}
			}
		
		} catch (Exception ex) {
		
			ex.printStackTrace();
			
		}
		
		return record;
	}
	
	@Override
	public Record updateResourceById(long resourceId, Record record) {
		return null;
	}
	
	@Override
	public Record deleteResourceById(long resourceId) {
		return null;
	}
}





























