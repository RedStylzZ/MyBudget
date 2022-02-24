package com.github.redstylzz.backend.exception;

public class SeriesAlreadyExistException extends RuntimeException {
    public SeriesAlreadyExistException(String message) {
        super(message);
    }

    public SeriesAlreadyExistException() {
        super("Series already existent");
    }
}
