package com.osetrova.project.exception;

public class FileNotFoundInDatabaseException extends RuntimeException {

    public FileNotFoundInDatabaseException(String message) {
        super(message);
    }
}
