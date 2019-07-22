package com.resource.manager.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 9L;
	
	private int resourceId;
	
	public ResourceNotFoundException(int resourceId) {
		super(String.format("Resource with id [%d] was not found!", resourceId));
		this.resourceId = resourceId;
	}
	
	public int getResourceId() {
		return resourceId;
	}
}