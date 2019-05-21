package com.accenture.ips.springstub.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> bindException(BindException ex, WebRequest request) {
        ApiError error = ApiError.of(ECode.INVALID_INPUT);

        BindingResult bindingResult = ex.getBindingResult();
        for (FieldError err : bindingResult.getFieldErrors()) {
            error.addDetail(err.getDefaultMessage());
        }

        try {
            LOGGER.error("BindException error: {}", OBJECT_MAPPER.writeValueAsString(error));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error processing JSON, {}", e.getMessage(), e);
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> methodArgumentTypeMismatchException(MissingServletRequestParameterException ex, WebRequest request) {

        ApiError error = ApiError.of(ECode.INVALID_INPUT)
                .addDetail(ex.getMessage());

        try {
            LOGGER.error("BindException error: {}", OBJECT_MAPPER.writeValueAsString(error));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error processing JSON, {}", e.getMessage(), e);
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiError> apiErrorException(ApiErrorException ex, WebRequest request) {
        ApiError error = ex.getApiError();

        try {
            LOGGER.error("APIErrorException error: {}", OBJECT_MAPPER.writeValueAsString(error));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error processing JSON, {}", e.getMessage(), e);
        }

        return new ResponseEntity<>(error, HttpStatus.resolve(Integer.parseInt(error.getHttpStatusCode())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exception(Exception ex, WebRequest request) {
        ApiError error = ApiError.of(ECode.UNEXPECTED_ERROR);

        try {
            LOGGER.error("Exception error: {}", OBJECT_MAPPER.writeValueAsString(error));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error processing JSON, {}", e.getMessage(), e);
        }

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
