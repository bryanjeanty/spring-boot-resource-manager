package com.resource.manager.resource.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.resource.manager.resource.entity.Data;

public class DataRepositoryImpl implements DataRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public HashMap<Integer, Boolean> doesTableExist(Data data) {
        int refTabVerNum = data.getVersionNumber();
        String selectQuery = "SELECT ReferenceTableVersionNumber FROM dataTables WHERE ReferenceTableVersionNumber = '?'";
        HashMap<Integer, Boolean> myMap = new HashMap<Integer, Boolean>(1);

        myMap.put(refTabVerNum, true);

        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(selectQuery);
            int returnVal = (int) query.setParameter(1, refTabVerNum).getSingleResult();

            if (refTabVerNum != returnVal) {
                myMap.replace(refTabVerNum, false);
            }

            entityManager.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }

        return myMap;
    }

    @Override
    public int createTable(HashMap<Integer, Boolean> myMap, String[] columns) {
        int refTabVerNum = 0;

        for (HashMap.Entry<Integer, Boolean> entry : myMap.entrySet()) {
            if (entry.getValue() == true) {
                return entry.getKey();
            } else {
                refTabVerNum = entry.getKey() + 1;
            }
        }

        String createQuery = "CREATE TABLE data-" + refTabVerNum + "(Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,"
                + "Name VARCHAR(150)," + "Code INT," + "VersionNumber INT NOT NULL,";

        for (int i = 0; i < columns.length; i++) {
            createQuery += "\n" + columns[i] + "VARCHAR";
            if (i != (columns.length - 1)) {
                createQuery += ",";
            } else {
                createQuery += ");";
            }
        }

        try {

            entityManager.getTransaction().begin();

            entityManager.createNativeQuery(createQuery).executeUpdate();

            entityManager.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }
        return refTabVerNum;
    }

    @Override
    public List<Data> getDataFromDatabase(String query) {
        List<Data> dataList = new ArrayList<Data>();
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery(query, Data.class);
            List<?> results = q.getResultList();
            for (Object data : results) {
                dataList.add((Data) data);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }
        return dataList;
    }

    @Override
    public Data putDataIntoDatabase(String query) {
        Data myData = new Data();
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery(query, Data.class);
            myData = (Data) q.getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }

        return myData;
    }

    @Override
    public Data createRecordByVersionNumber(int versionNumber, Data data) {
        String insertQuery = "INSERT INTO data-" + versionNumber + " (Name, Code, VersionNumber";
        if (!(data.getColumnNames().equals(null) || data.getColumnNames().equals(""))) {
            String[] columnNamesArray = data.getColumnNames().split(", ");
            String[] columnValuesArray = data.getColumnValues().split(", ");

            for (int i = 0; i < columnNamesArray.length; i++) {
                insertQuery += ", " + columnNamesArray[i];
            }
            insertQuery += ") VALUES (" + data.getName() + ", " + data.getCode() + ", " + versionNumber;

            for (int i = 0; i < columnValuesArray.length; i++) {
                insertQuery += ", " + columnValuesArray[i];
            }
            insertQuery += ");";
        } else {
            insertQuery += ") VALUES (" + data.getName() + ", " + data.getCode() + ", " + versionNumber + ");";
        }

        return putDataIntoDatabase(insertQuery);
    }

    @Override
    public List<Data> getAllDataByVersionNumber(int versionNumber) {
        String selectQuery = "SELECT * FROM data-" + versionNumber;
        return getDataFromDatabase(selectQuery);
    }

    @Override
    public List<Data> getDataByVersionNumberAndId(int versionNumber, int dataId) {
        String selectQuery = "SELECT * FROM data-" + versionNumber + " WHERE Id='" + dataId + "'";
        return getDataFromDatabase(selectQuery);
    }

    @Override
    public Data updateRecordByVersionNumberAndId(int versionNumber, int dateId, Data data) {
        int dataId = dateId;
        String updateQuery = "UPDATE data-" + versionNumber + " SET Name ='" + data.getName() + "', Code='"
                + data.getCode() + "', VersionNumber='" + versionNumber + "'";
        if (!(data.getColumnNames().equals(null) || data.getColumnNames().equals(""))) {
            String[] columnNamesArray = data.getColumnNames().split(", ");
            String[] columnValuesArray = data.getColumnValues().split(", ");

            for (int i = 0; i < columnNamesArray.length; i++) {
                updateQuery += ", " + columnNamesArray[i] + "='" + columnValuesArray[i] + "'";
            }
        }
        updateQuery += "WHERE Id=" + dataId;
        return putDataIntoDatabase(updateQuery);
    }

    @Override
    public int destroyByVersionNumber(int versionNumber, int dataId) {
        String deleteQuery = "DELETE FROM data-" + versionNumber + "WHERE Id='" + dataId + "'";
        int rowsAffected = 0;
        try {
            entityManager.getTransaction().begin();
            rowsAffected = entityManager.createNativeQuery(deleteQuery).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }

        return rowsAffected;
    }
}