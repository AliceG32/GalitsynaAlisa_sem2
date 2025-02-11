package com.mts.work.repository.exception;

public class EntityNotFound extends Exception {
    public EntityNotFound(String errorMessage) {
        super(errorMessage);
    }
}