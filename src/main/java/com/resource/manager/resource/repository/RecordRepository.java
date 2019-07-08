package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.Record;

import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends BaseRepository<Record, Long>, RecordCustomMethods {
}