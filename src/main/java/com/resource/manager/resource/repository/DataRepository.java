package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.Data;

import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends BaseRepository<Data, Integer>, DataRepositoryCustom {
}