package com.github.redstylzz.backend.exception;

import java.util.NoSuchElementException;

public class DepositDoesNotExistException extends NoSuchElementException {

    public DepositDoesNotExistException(String s) {
        super(s);
    }

    public DepositDoesNotExistException() {
        super();
    }
}
