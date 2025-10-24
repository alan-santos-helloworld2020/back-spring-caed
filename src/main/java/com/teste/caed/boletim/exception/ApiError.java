package com.teste.caed.boletim.exception;

import java.time.OffsetDateTime;
import java.util.List;

public class ApiError {
    public String path;
    public int status;
    public String error;
    public String message;
    public OffsetDateTime timestamp = OffsetDateTime.now();
    public List<FieldError> fieldErrors;

    public static class FieldError {
        public String field;
        public String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}