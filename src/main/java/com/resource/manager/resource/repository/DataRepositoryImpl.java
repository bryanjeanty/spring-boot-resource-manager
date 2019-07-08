/*package com.resource.manager.resource.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

import org.springframework.transaction.annotation.Transactional;

import com.resource.manager.resource.entity.Data;

public class DataRepositoryImpl implements DataRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public HashMap<Boolean, Integer> doesTableExist(Data data) {
        int derTabVerNum = data.getVersionNumber();
        String selectQuery = "SELECT DerivedTableVersionNumber FROM dataTables WHERE DerivedTableVersionNumber = '" + derTabVerNum + "'";
        HashMap<Boolean, Integer> myMap = new HashMap<Boolean, Integer>();

        try {
            List<?> results = entityManager.createNativeQuery(selectQuery).getResultList();

            if (!(results.isEmpty())) {
                myMap.put(true, derTabVerNum);
            } else {
		myMap.put(false, derTabVerNum);
	    }

        } catch (NoResultException ex) {
            System.out.println("Creating new table....");
        } finally {
            entityManager.close();
        }

        return myMap;
    }

    @Override
    @Transactional
    public int createTable(HashMap<Boolean, Integer> myMap, String[] columns) {
	if (myMap.get(false) == null) {
		return myMap.get(true);
	}

	int derTabVerNum = myMap.get(false);

        String createQuery = "CREATE TABLE data" + derTabVerNum + " (Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,"
                + " Name VARCHAR(150)," + " Code INT," + " VersionNumber INT NOT NULL,";

        for (int i = 0; i < columns.length; i++) {
            createQuery += " " + columns[i] + " VARCHAR(MAX)";
            if (i != (columns.length - 1)) {
                createQuery += ",";
            } else {
                createQuery += ");";
            }
        }

        try {
            entityManager.createNativeQuery(createQuery).executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
        return derTabVerNum - 1;
    }

    @Override
    @Transactional
    public int createTable(HashMap<Boolean, Integer> myMap) {
        if (myMap.get(false) == null) {
            return myMap.get(true);
        }

        int derTabVerNum = myMap.get(false);

        String createQuery = "CREATE TABLE data" + derTabVerNum + " (Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,"
                + " Name VARCHAR(150)," + " Code INT," + " VersionNumber INT NOT NULL);";

        try {
            entityManager.createNativeQuery(createQuery).executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
        return derTabVerNum - 1;
    }

    @Override
    @Transactional
    public List<Data> getDataFromDatabase(String query) {
        List<Data> dataList = new ArrayList<Data>();
        try {
            Query q = entityManager.createNativeQuery(query, Data.class);
            List<?> results = q.getResultList();
            for (Object data : results) {
                dataList.add((Data) data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }
        return dataList;
    }

    @Override
    @Transactional
    public Data createRecordByVersionNumber(int versionNumber, Data data) {
        String insertQuery = "INSERT INTO data" + versionNumber + " (Name, Code, VersionNumber";
        if (!(data.getColumnValues().equals(null) || data.getColumnValues().equals(""))) {
            String[] columnNamesArray = data.getColumnNames().split(", ");
            String[] columnValuesArray = data.getColumnValues().split(", ");

            for (int i = 0; i < columnNamesArray.length; i++) {
                insertQuery += ", " + columnNamesArray[i];
            }
            insertQuery += ") VALUES ('" + data.getName() + "', '" + data.getCode() + "', '" + versionNumber + "'";

            for (int i = 0; i < columnValuesArray.length; i++) {
                insertQuery += ", '" + columnValuesArray[i] + "'";
            }
            insertQuery += ");";
        } else {
            insertQuery += ") VALUES ('" + data.getName() + "', '" + data.getCode() + "', '" + versionNumber + "');";
        }

        try {
            entityManager.createNativeQuery(insertQuery).executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }

        return data;
    }

    @Override
    public List<Data> getAllDataByVersionNumber(int versionNumber) {
        String selectQuery = "SELECT * FROM data" + versionNumber;
        return getDataFromDatabase(selectQuery);
    }

    @Override
    public List<Data> getDataByVersionNumberAndId(int versionNumber, int dataId) {
        String selectQuery = "SELECT * FROM data" + versionNumber + " WHERE Id='" + dataId + "'";
        return getDataFromDatabase(selectQuery);
    }

    @Override
    @Transactional
    public Data updateRecordByVersionNumberAndId(int versionNumber, int dateId, Data data) {
        int dataId = dateId;
        String updateQuery = "UPDATE data" + versionNumber + " SET Name ='" + data.getName() + "', Code='"
                + data.getCode() + "', VersionNumber='" + versionNumber + "'";
        if (!(data.getColumnNames().equals(null) || data.getColumnNames().equals(""))) {
            String[] columnNamesArray = data.getColumnNames().split(", ");
            String[] columnValuesArray = data.getColumnValues().split(", ");

            for (int i = 0; i < columnNamesArray.length; i++) {
                updateQuery += ", " + columnNamesArray[i] + "='" + columnValuesArray[i] + "'";
            }
        }
        updateQuery += "WHERE Id='" + dataId + "'";

        try {
            entityManager.createNativeQuery(updateQuery).executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }

        return data;
    }

    @Override
    @Transactional
    public int destroyByVersionNumber(int versionNumber, int dataId) {
        String deleteQuery = "DELETE FROM data" + versionNumber + "WHERE Id='" + dataId + "'";
        int rowsAffected = 0;
        try {
            rowsAffected = entityManager.createNativeQuery(deleteQuery).executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            entityManager.close();
        }

        return rowsAffected;
    }
}
*/