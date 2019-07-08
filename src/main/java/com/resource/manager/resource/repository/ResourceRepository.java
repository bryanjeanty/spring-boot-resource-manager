package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.Resource;

import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends BaseRepository<Resource, Long> {}