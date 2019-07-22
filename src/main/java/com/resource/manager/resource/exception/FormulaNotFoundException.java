package com.resource.manager.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FormulaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 10L;
	
	private int formulaId;
	
	public FormulaNotFoundException(int formulaId) {
		super(String.format("Formula with id [%d] was not found!", formulaId));
		this.formulaId = formulaId;
	}
	
	public int getFormulaId() {
		return formulaId;
	}
}