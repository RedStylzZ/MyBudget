package com.github.redstylzz.backend.exception;

import java.util.NoSuchElementException;

public class PaymentDoesNotExistException extends NoSuchElementException {

    public PaymentDoesNotExistException(String s) {
        super(s);
    }

    public PaymentDoesNotExistException() {
        super();
    }
}
