package com.github.redstylzz.backend.exception;

public class SeriesDoesNotExistException extends RuntimeException {
    public SeriesDoesNotExistException(String message) {
        super(message);
    }

    public SeriesDoesNotExistException() {
        super("Series does not exist");
    }
}
