package com.github.redstylzz.backend.exception;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
    public CategoryAlreadyExistException() {
        super("A category with this name already exists");
    }
}
