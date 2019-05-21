package com.accenture.ips.springstub.exception;

public class ApiErrorException extends RuntimeException {

    private ApiError apiError;

    public ApiErrorException(ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiErrorException(ApiError apiError, Throwable cause) {
        super(apiError.getMessage(), cause);
        this.apiError = apiError;
    }

    public String getMessage() {
        return apiError.getMessage();
    }

    public ApiError getApiError() {
        return apiError;
    }
}
