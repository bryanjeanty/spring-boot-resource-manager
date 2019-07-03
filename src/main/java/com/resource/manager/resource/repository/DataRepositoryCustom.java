package com.resource.manager.resource.repository;

import java.util.List;
import java.util.HashMap;

import com.resource.manager.resource.entity.Data;

public interface DataRepositoryCustom {
    public HashMap<Boolean, Integer> doesTableExist(Data data);

    public int createTable(HashMap<Boolean, Integer> myMap, String[] columns);

    public int createTable(HashMap<Boolean, Integer> myMap);

    public List<Data> getDataFromDatabase(String query);

    public int putDataIntoDatabase(String query);

    public Data createRecordByVersionNumber(int versionNumber, Data data);

    public List<Data> getAllDataByVersionNumber(int versionNumber);

    public List<Data> getDataByVersionNumberAndId(int versionNumber, int dataId);

    public Data updateRecordByVersionNumberAndId(int versionNumber, int dateId, Data data);

    public int destroyByVersionNumber(int versionNumber, int dataId);
}