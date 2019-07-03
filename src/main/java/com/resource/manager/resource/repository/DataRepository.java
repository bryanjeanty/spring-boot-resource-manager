package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.DataTables;

import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends BaseRepository<DataTables, Integer>, DataRepositoryCustom {
}