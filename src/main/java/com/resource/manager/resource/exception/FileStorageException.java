package com.resource.manager.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileStorageException extends RuntimeException {
    private static final long serialVersionUID = 10L;

    private String message;

    public FileStorageException(String message) {
        super(message);
        this.message = message;
    }

    public String message() {
        return message;
    }
}