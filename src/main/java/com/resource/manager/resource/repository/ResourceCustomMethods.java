package com.resource.manager.resource.repository;

import java.util.List;

import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.entity.Resource;

public interface ResourceCustomMethods {
	public List<Record> findAllResources();
	
	public Record findResourceById(int resourceId);
	
	public Record updateResourceById(int resourceId, Record record);
	
	public Record deleteResourceById(int resourceId);
}