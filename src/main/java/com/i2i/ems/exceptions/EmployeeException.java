package com.i2i.ems.exceptions;

import java.io.Serial;

public class EmployeeException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public EmployeeException(String message) {
        super(message);
    }
    public EmployeeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}