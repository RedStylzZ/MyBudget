package com.github.redstylzz.backend.exception;

public class DepositAlreadyExistException extends RuntimeException {
    public DepositAlreadyExistException(String message) {
        super(message);
    }

    public DepositAlreadyExistException() {
        super();
    }
}
