package com.github.redstylzz.backend.exception;

import java.util.NoSuchElementException;

public class CategoryDoesNotExistException extends NoSuchElementException {

    public CategoryDoesNotExistException(String s) {
        super(s);
    }
}
