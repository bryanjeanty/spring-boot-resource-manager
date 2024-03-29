package com.resource.manager.resource.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager; 
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

            for (Object[] record : records) {
                typeArray.add((String) record[0]);
                typeIdArray.add((Integer) record[1]);
                keysArray.add((String) record[2]);
                keyValuesArray.add((String) record[3]);
                dataTypesArray.add((String) record[4]);
            }

            for (int i = 0; i < typeArray.size(); i++) {
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
        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id = '"
                + resourceId + "'";
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
        String selectQuery = "SELECT type, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id = '"
                + resourceId + "'";

        try {

            @SuppressWarnings("unchecked")
            List<Object[]> recordList = entityManager.createNativeQuery(selectQuery).getResultList();

            String updateQuery = "UPDATE record SET ";

            for (Object[] recordItem : recordList) {
                if (!(((String) recordItem[0]).equals(record.getType()))) {

                    updateQuery += " type = '" + record.getType() + "'";

                }
                if (!(((String) recordItem[1]).equals(record.getKeys()))) {
                	if (updateQuery.contains("type")) {
                		updateQuery += ",";
                	}

                    updateQuery += " keys = '" + record.getKeys() + "'";

                }
                if (!(((String) recordItem[2]).equals(record.getKeyValues()))) {
                	if (updateQuery.contains("key") || updateQuery.contains("type")) {
                		updateQuery += ",";
                	}

                    updateQuery += " key_values = '" + record.getKeyValues() + "'";

                }
                if (!(((String) recordItem[3]).equals(record.getDataTypes()))) {
                	if (updateQuery.contains("key_values") || updateQuery.contains("key") || updateQuery.contains("type")) {
                		updateQuery += ",";
                	}

                    updateQuery += " data_types = '" + record.getDataTypes() + "'";

                }

                record.setTypeId(resourceId);
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
    @SuppressWarnings({ "unchecked" })
    public Record deleteResourceById(int resourceId) {
        String selectRecordQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'resource' AND type_id = '"
                + resourceId + "'";
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
    public List<Record> findAllProjects(int version) {
        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record INNER JOIN project ON record.type_id = project.id WHERE record.type = 'project' AND project.version = '" + version + "'";

        List<String> typeArray = new ArrayList<String>();
        List<Integer> typeIdArray = new ArrayList<Integer>();
        List<String> keysArray = new ArrayList<String>();
        List<String> keyValuesArray = new ArrayList<String>();
        List<String> dataTypesArray = new ArrayList<String>();

        List<Record> projects = new ArrayList<Record>();

        try {

            @SuppressWarnings("unchecked")
            List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();

            for (Object[] record : records) {
                typeArray.add((String) record[0]);
                typeIdArray.add((Integer) record[1]);
                keysArray.add((String) record[2]);
                keyValuesArray.add((String) record[3]);
                dataTypesArray.add((String) record[4]);
            }

            for (int i = 0; i < typeArray.size(); i++) {
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
    public List<Record> findAllProjects(String filename) {
        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record INNER JOIN project ON record.type_id = project.id WHERE record.type = 'project' AND project.filename = '" + filename + "'";

        List<String> typeArray = new ArrayList<String>();
        List<Integer> typeIdArray = new ArrayList<Integer>();
        List<String> keysArray = new ArrayList<String>();
        List<String> keyValuesArray = new ArrayList<String>();
        List<String> dataTypesArray = new ArrayList<String>();

        List<Record> projects = new ArrayList<Record>();

        try {

            @SuppressWarnings("unchecked")
            List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();

            for (Object[] record : records) {
                typeArray.add((String) record[0]);
                typeIdArray.add((Integer) record[1]);
                keysArray.add((String) record[2]);
                keyValuesArray.add((String) record[3]);
                dataTypesArray.add((String) record[4]);
            }

            for (int i = 0; i < typeArray.size(); i++) {
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

            for (Object[] record : records) {
                typeArray.add((String) record[0]);
                typeIdArray.add((Integer) record[1]);
                keysArray.add((String) record[2]);
                keyValuesArray.add((String) record[3]);
                dataTypesArray.add((String) record[4]);
            }

            for (int i = 0; i < typeArray.size(); i++) {
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

        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'project' AND type_id = '"
                + projectId + "'" ;

        Record project = new Record();

        try {
            @SuppressWarnings("unchecked")
            List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();

            for (Object[] record : records) {
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
        String selectQuery = "SELECT type, keys, key_values, data_types FROM record WHERE type = 'project' AND type_id = '"
                + projectId + "'";

        try {
            @SuppressWarnings("unchecked")
            List<Object[]> recordList = entityManager.createNativeQuery(selectQuery).getResultList();

            String updateQuery = "UPDATE record SET ";

            for (Object[] recordItem : recordList) {
                if (!(((String) recordItem[0]).equals(record.getType()))) {
                    updateQuery += " type = '" + record.getType() + "'";
                }

                if (!(((String) recordItem[1]).equals(record.getKeys()))) {
                	if (updateQuery.contains("type")) {
                		updateQuery += ",";
                	}
                    updateQuery += " keys = '" + record.getKeys() + "'";
                }

                if (!(((String) recordItem[2]).equals(record.getKeyValues()))) {
                	if (updateQuery.contains("keys") || updateQuery.contains("type")) {
                		updateQuery += ",";
                	}
                    updateQuery += " key_values = '" + record.getKeyValues() + "'";
                }

                if (!(((String) recordItem[3]).equals(record.getDataTypes()))) {
                	if (updateQuery.contains("key_values") || updateQuery.contains("keys") || updateQuery.contains("type")) {
                		updateQuery += ",";
                	}
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
    @SuppressWarnings({ "unchecked" })
    public Record deleteProjectById(int projectId) {
        String selectRecordQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'project' AND type_id = '"
                + projectId + "'";
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

    @Override
    public List<Record> findAllFormulas() {
        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'formula'";

        List<String> typeArray = new ArrayList<String>();
        List<Integer> typeIdArray = new ArrayList<Integer>();
        List<String> keysArray = new ArrayList<String>();
        List<String> keyValuesArray = new ArrayList<String>();
        List<String> dataTypesArray = new ArrayList<String>();

        List<Record> formulas = new ArrayList<Record>();

        try {

            @SuppressWarnings("unchecked")
            List<Object[]> records = ((Query) entityManager.createNativeQuery(selectQuery)).getResultList();
			
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
				
				formulas.add(record);
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		return formulas;
    }

    @Override
    public Record findFormulaById(int formulaId) {
        String selectQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'formula' AND type_id = '"
                + formulaId + "'";
        Record formula = new Record();

        try {

            @SuppressWarnings("unchecked")
            List<Object[]> records = entityManager.createNativeQuery(selectQuery).getResultList();

            for (Object[] record : records) {
                formula.setType((String) record[0]);
                formula.setTypeId((Integer) record[1]);
                formula.setKeys((String) record[2]);
                formula.setKeyValues((String) record[3]);
                formula.setDataTypes((String) record[4]);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return formula;
    }

    @Override
    @Transactional
    public Record updateFormulaById(int formulaId, Record record) {
        String selectQuery = "SELECT type, keys, key_values, data_types FROM record WHERE type = 'formula' AND type_id = '"
                + formulaId + "'";

        try {

            @SuppressWarnings("unchecked")
            List<Object[]> recordList = entityManager.createNativeQuery(selectQuery).getResultList();

            String updateQuery = "UPDATE record SET ";

            for (Object[] recordItem : recordList) {
                if (!(((String) recordItem[0]).equals(record.getType()))) {

                    updateQuery += " type = '" + record.getType() + "'";

                }
                if (!(((String) recordItem[1]).equals(record.getKeys()))) {
                	if (updateQuery.contains("type")) {
                		updateQuery += ",";
                	}

                    updateQuery += " keys = '" + record.getKeys() + "'";

                }
                if (!(((String) recordItem[2]).equals(record.getKeyValues()))) {
                	if (updateQuery.contains("keys") || updateQuery.contains("type")) {
                		updateQuery += ",";
                	}

                    updateQuery += " key_values = '" + record.getKeyValues() + "'";

                }
                if (!(((String) recordItem[3]).equals(record.getDataTypes()))) {
                	if (updateQuery.contains("key_values") || updateQuery.contains("keys") || updateQuery.contains("type")) {
                		updateQuery += ",";
                	}
                  
                    updateQuery += " data_types = '" + record.getDataTypes() + "'";

                }

                record.setTypeId(formulaId);
                updateQuery += " WHERE type = 'formula' AND type_id = '" + formulaId + "'";
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
    @SuppressWarnings({ "unchecked" })
    public Record deleteFormulaById(int formulaId) {
        String selectRecordQuery = "SELECT type, type_id, keys, key_values, data_types FROM record WHERE type = 'formula' AND type_id = '"
                + formulaId + "'";
        String deleteRecordQuery = "DELETE FROM record WHERE type = 'formula' AND type_id = '" + formulaId + "'";
        String deleteResourceQuery = "DELETE FROM formula WHERE id = '" + formulaId + "'";
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
}

























