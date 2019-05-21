package com.accenture.ips.springstub.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiError {

    private static final Map<ECode, String[]> errorMap = new HashMap<>();
    static {
        errorMap.put(ECode.NO_PATIENT_FOUND, new String[]{"10000", "404", "No patient found with requestId"});
        errorMap.put(ECode.CANNOT_PARSE_REQUEST, new String[]{"10001", "500", "Cannot parse patient search request"});
        errorMap.put(ECode.PATIENT_AND_REQUEST_ID_REQUIRED, new String[]{"10002", "400", "Both patientId and requestId are required"});
        errorMap.put(ECode.UNEXPECTED_ERROR, new String[]{"90000", "500", "Unexpected Error"});
        errorMap.put(ECode.INVALID_INPUT, new String[]{"90001", "400", "Invalid input"});
    }

    public static ApiError of(ECode errorCode) {
        String[] val = errorMap.get(errorCode);
        return new ApiError(val[0], val[1], val[2]);
    }

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("httpStatusCode")
    private String httpStatusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private List<String> details = new ArrayList<>();

    public ApiError() {
    }

    public ApiError(String errorCode, String httpStatusCode, String message) {
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public ApiError addDetail(String detail) {
        this.details.add(detail);
        return this;
    }
}
