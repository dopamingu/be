package com.dopamingu.be.domain.global.error.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String errorClassName;
    private final String errorCode;
    private final String message;

    private List<ValidationError> validErrors;

    public static ErrorResponse of(String errorClassName, String errorCode, String message) {
        return new ErrorResponse(errorClassName, errorCode, message);
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(validErrors)) {
            validErrors = new ArrayList<>();
        }
        validErrors.add(new ValidationError(field, message));
    }

    @Data
    @RequiredArgsConstructor
    private static class ValidationError {

        private final String field;
        private final String message;
    }
}
