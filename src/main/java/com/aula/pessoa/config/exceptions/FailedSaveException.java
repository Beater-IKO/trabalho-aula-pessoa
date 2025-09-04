package com.aula.pessoa.config.exceptions;

public class FailedSaveException extends RuntimeException {
    public FailedSaveException(String message) {
        super(message);
    }
}
