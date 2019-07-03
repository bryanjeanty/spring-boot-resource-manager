package com.resource.manager.resource.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.resource.manager.resource.entity.Data;
import com.resource.manager.resource.entity.DataTables;
import com.resource.manager.resource.repository.DataRepository;

import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    private final DataRepository dataRepository;

    public ResourceService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public HashMap<Integer, Boolean> doesTableExist(Data data) {
        return dataRepository.doesTableExist(data);
    }

    public int createTable(HashMap<Integer, Boolean> myMap, String[] columnNames) {
        return dataRepository.createTable(myMap, columnNames);
    }

    public int createTable(HashMap<Integer, Boolean> myMap) {
        return dataRepository.createTable(myMap);
    }

    public Data createRecordByVersionNumber(int versionNumber, Data data) {
        return dataRepository.createRecordByVersionNumber(versionNumber, data);
    }

    public List<Data> getAllDataByVersionNumber(int versionNumber) {
        return dataRepository.getAllDataByVersionNumber(versionNumber);
    }

    public List<Data> getDataByVersionNumberAndId(int versionNumber, int dataId) {
        return dataRepository.getDataByVersionNumberAndId(versionNumber, dataId);
    }

    public Data updateRecordByVersionNumberAndId(int versionNumber, int dateId, Data data) {
        return dataRepository.updateRecordByVersionNumberAndId(versionNumber, dateId, data);
    }

    public int destroyByVersionNumberAndId(int versionNumber, int dataId) {
        return dataRepository.destroyByVersionNumber(versionNumber, dataId);
    }

    public DataTables save(DataTables dataTables) {
        return dataRepository.save(dataTables);
    }

    public List<DataTables> findAll() {
        return dataRepository.findAll();
    }

    public Optional<DataTables> findById(int dataTablesId) {
        return dataRepository.findById(dataTablesId);
    }

    public void delete(DataTables dataTables) {
        dataRepository.delete(dataTables);
    }
}