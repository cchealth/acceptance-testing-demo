package com.accenture.ips.springstub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class FhirFindPatientRequestDto {

    @JsonProperty("searchParameters")
    private FhirFindPatientParams searchParameters;

    @JsonProperty("requestId")
    private String requestId;

    public FhirFindPatientParams getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(FhirFindPatientParams searchParameters) {
        this.searchParameters = searchParameters;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
