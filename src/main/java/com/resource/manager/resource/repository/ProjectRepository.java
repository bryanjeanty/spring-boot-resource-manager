package com.resource.manager.resource.repository;

import com.resource.manager.resource.entity.Project;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends BaseRepository<Project, Integer> {

}