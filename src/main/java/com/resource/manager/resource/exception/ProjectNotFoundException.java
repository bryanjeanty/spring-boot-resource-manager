package com.resource.manager.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8L;
	
	private int projectId;
	
	public ProjectNotFoundException(int projectId) {
		super(String.format("Project with id [%d] was not found!", projectId));
		this.projectId = projectId;
	}
	
	public int getProjectId() {
		return projectId;
	}
}