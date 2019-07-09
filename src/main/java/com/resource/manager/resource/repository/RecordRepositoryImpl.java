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
	public List<Record> findAllResources() {
		String selectRecordsQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource'";

		List<String> typeArray = new ArrayList<String>();
		List<Integer> typeIdArray = new ArrayList<Integer>();
		List<String> keysArray = new ArrayList<String>();
		List<String> keyValuesArray = new ArrayList<String>();
		List<String> dataTypesArray = new ArrayList<String>();
		
		List<Record> resources = new ArrayList<Record>();
		
		try {
		
			@SuppressWarnings("unchecked")
			List<Object[]> records = entityManager.createNativeQuery(selectRecordsQuery).getResultList();
			
			for(Object[] record : records) {
				typeArray.add((String) record[0]);
				typeIdArray.add((Integer) record[1]);
				keysArray.add((String) record[2]);
				keyValuesArray.add((String) record[3]);
				dataTypesArray.add((String) record[4]);
			}
			
			for(int i = 0; i < typeArray.size(); i++) {
				Record record = new Record();
				
				record.setType(typeArray.get(i));
				record.setTypeId(typeIdArray.get(i));
				record.setKeys(keysArray.get(i));
				record.setKeyValues(keyValuesArray.get(i));
				record.setDataTypes(dataTypesArray.get(i));
				
				resources.add(record);
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		return resources;
	}
	
	@Override
	public Record findResourceById(int resourceId) {
		String selectResourceQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id='" + resourceId + "'";
		Record resource = new Record();
		
		try {
			
			@SuppressWarnings("unchecked")
			List<Object[]> records = entityManager.createNativeQuery(selectResourceQuery).getResultList();
					
			for (Object[] record : records) {
				resource.setType((String) record[0]);
				resource.setTypeId((Integer) record[1]);
				resource.setKeys((String) record[2]);
				resource.setKeyValues((String) record[3]);
				resource.setDataTypes((String) record[4]);
			}
					
		} catch (Exception ex) {
				
			ex.printStackTrace();
				
		}
		
		return resource;
	}
	
	@Override
	public Record updateResourceById(int resourceId, Record record) {
		return null;
	}
	
	@Override
	public Record deleteResourceById(int resourceId) {
		return null;
	}
}





























