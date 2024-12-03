package com.dopamingu.be.domain.global.error.exception;

import com.dopamingu.be.domain.common.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static GlobalResponse getGlobalResponse(ErrorCode errorCode) {
        final ErrorResponse errorResponse =
            ErrorResponse.of(
                errorCode.name(), errorCode.getErrorCode(), errorCode.getMessage());
        final GlobalResponse response =
            GlobalResponse.fail(errorCode.getStatus().value(), errorResponse);
        return response;
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(
        HttpRequestMethodNotSupportedException
            .class) // HttpRequestMethodNotSupportedException 예외를 잡아서 처리
    protected ResponseEntity<GlobalResponse> handle(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        final ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        final GlobalResponse response = getGlobalResponse(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    /**
     * CustomException 예외 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GlobalResponse> handleCustomException(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final GlobalResponse response = getGlobalResponse(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    /**
     * CustomException 정의되지 않은 Exception 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<GlobalResponse> handle(Exception e) {
        e.printStackTrace();
        log.error("Exception", e);
        final ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        final GlobalResponse response = getGlobalResponse(errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}
