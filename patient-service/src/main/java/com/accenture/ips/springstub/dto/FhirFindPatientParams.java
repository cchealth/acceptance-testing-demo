package com.accenture.ips.springstub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FhirFindPatientParams {

    @JsonProperty("patientId")
    private String patientId;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
