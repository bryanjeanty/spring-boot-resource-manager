package com.resource.manager.resource.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.resource.manager.resource.entity.Record;

public class RecordRepositoryImpl implements RecordCustomMethods {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Record> findAllResources() {
		String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource'";

		List<String> typeArray = new ArrayList<String>();
		List<Integer> typeIdArray = new ArrayList<Integer>();
		List<String> keysArray = new ArrayList<String>();
		List<String> keyValuesArray = new ArrayList<String>();
		List<String> dataTypesArray = new ArrayList<String>();
		
		List<Record> resources = new ArrayList<Record>();
		
		try {
		
			@SuppressWarnings("unchecked")
			List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();
			
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
		String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id = '" + resourceId + "'";
		Record resource = new Record();
		
		try {
			
			@SuppressWarnings("unchecked")
			List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();
					
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
	@Transactional
	public Record updateResourceById(int resourceId, Record record) {		
		String selectQuery = "SELECT type, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id = '" + resourceId + "'";
		
		try {
			
			@SuppressWarnings("unchecked")
			List<Object[]> recordList = entityManager.createNativeQuery(selectQuery).getResultList();
			
			String updateQuery = "UPDATE record SET ";
			
			for (Object[] recordItem : recordList) {
				if (!(((String) recordItem[0]).equals(record.getType()))) {
					
					updateQuery += " type = '" + record.getType() + "',";
					
				}
				if (!(((String) recordItem[1]).equals(record.getKeys()))) {
					
					updateQuery += " keys = '" + record.getKeys() + "',";
					
				}
				if (!(((String) recordItem[2]).equals(record.getKeyValues()))) {
					
					updateQuery += " key_values = '" + record.getKeyValues() + "',";
					
				}
				if (!(((String) recordItem[3]).equals(record.getDataTypes()))) {
					
					updateQuery += " data_types = '" + record.getDataTypes() + "'";
					
				}
				
				updateQuery += " WHERE type = 'resource' AND type_id = '" + resourceId + "'";
			}
			
			try {
				
				entityManager.createNativeQuery(updateQuery).executeUpdate();
				
			} catch (Exception ex) {
			
				ex.printStackTrace();
				
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		return record;
	}
	
	@Override
	@Transactional
    @SuppressWarnings({"unchecked"})
	public Record deleteResourceById(int resourceId) {
		String selectRecordQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id = '" + resourceId + "'";
		String deleteRecordQuery = "DELETE FROM record WHERE type = 'resource' AND type_id = '" + resourceId + "'";
		String deleteResourceQuery = "DELETE FROM resource WHERE id = '" + resourceId + "'";
		Record record = new Record();
		
		try {
			
			List<Object[]> list = entityManager.createNativeQuery(selectRecordQuery).getResultList();
			
			for (Object[] item : list) {
				record.setType((String) item[0]);
				record.setTypeId((Integer) item[1]);
				record.setKeys((String) item[2]);
				record.setKeyValues((String) item[3]);
				record.setDataTypes((String) item[4]);
			}
			
			entityManager.createNativeQuery(deleteRecordQuery).executeUpdate();
			
			entityManager.createNativeQuery(deleteResourceQuery).executeUpdate();
				
		} catch (Exception ex) {
		
			ex.printStackTrace();
		
		}
		
		return record;
	}


    //////////////////////////////////////////////////////////////////////////////////
    // Methods pertaining to Projects page start here
    //////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Record> findAllProjects() {
        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'project'";

        List<String> typeArray = new ArrayList<String>();
		List<Integer> typeIdArray = new ArrayList<Integer>();
		List<String> keysArray = new ArrayList<String>();
		List<String> keyValuesArray = new ArrayList<String>();
		List<String> dataTypesArray = new ArrayList<String>();
		
        List<Record> projects = new ArrayList<Record>();
        
		try {
		
			@SuppressWarnings("unchecked")
			List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();
			
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
				
				projects.add(record);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return projects;
    }

    @Override
    public Record findProjectById(int projectId) {

        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'project' AND type_id = '" + projectId + "'";

        Record project = new Record();

        try {
            @SuppressWarnings("unchecked")
            List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();

            for (Object[] record : records){
                project.setType((String) record[0]);
				project.setTypeId((Integer) record[1]);
				project.setKeys((String) record[2]);
				project.setKeyValues((String) record[3]);
				project.setDataTypes((String) record[4]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }   
        return project;
    }

    @Override
    @Transactional
    public Record updateProjectById(int projectId, Record record) {
        String selectQuery = "SELECT type, keys, key_values, data_types FROM record WHERE type = 'project' AND type_id = '" + projectId + "'";
		
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> recordList = entityManager.createNativeQuery(selectQuery).getResultList();
			
			String updateQuery = "UPDATE record SET ";
			
			for (Object[] recordItem : recordList) {
				if (!(((String) recordItem[0]).equals(record.getType()))) {
                    updateQuery += " type = '" + record.getType() + "',";
                }
                
				if (!(((String) recordItem[1]).equals(record.getKeys()))) {
					updateQuery += " keys = '" + record.getKeys() + "',";
				}

                if (!(((String) recordItem[2]).equals(record.getKeyValues()))) {
					updateQuery += " key_values = '" + record.getKeyValues() + "',";
				}

                if (!(((String) recordItem[3]).equals(record.getDataTypes()))) {
					updateQuery += " data_types = '" + record.getDataTypes() + "'";
				}
				
				record.setTypeId(projectId);
				updateQuery += " WHERE type = 'project' AND type_id = '" + projectId + "'";
			}
			
			try {
				entityManager.createNativeQuery(updateQuery).executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();	
		}
		return record;
    }

    @Override
    @Transactional
    @SuppressWarnings({"unchecked"})
    public Record deleteProjectById(int projectId) {
        String selectRecordQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'project' AND type_id = '" + projectId + "'";
		String deleteRecordQuery = "DELETE FROM record WHERE type = 'project' AND type_id = '" + projectId + "'";
		String deleteProjectQuery = "DELETE FROM project WHERE id = '" + projectId + "'";
		Record record = new Record();
		
		try {
			
			List<Object[]> list = entityManager.createNativeQuery(selectRecordQuery).getResultList();
			
			for (Object[] item : list) {
				record.setType((String) item[0]);
				record.setTypeId((Integer) item[1]);
				record.setKeys((String) item[2]);
				record.setKeyValues((String) item[3]);
				record.setDataTypes((String) item[4]);
			}
			
			entityManager.createNativeQuery(deleteRecordQuery).executeUpdate();
			entityManager.createNativeQuery(deleteProjectQuery).executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return record;
    }
}


























