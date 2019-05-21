package com.accenture.ips.springstub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PatientRequestOutputDto {

    @JsonProperty("requestId")
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
