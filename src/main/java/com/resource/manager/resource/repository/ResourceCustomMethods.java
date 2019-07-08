package com.resource.manager.resource.repository;

import java.util.Map;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.entity.Resource;

public interface ResourceCustomMethods {
	public Map<Resource, Record> findAllResources();
	
	public Record findResourceById(long resourceId);
	
	public Record updateResourceById(long resourceId, Record record);
	
	public Record deleteResourceById(long resourceId);
}