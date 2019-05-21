package com.accenture.ips.springstub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PatientDataOutputDto {

    @JsonProperty("data")
    private ObjectNode data;

    public ObjectNode getData() {
        return data;
    }

    public void setData(ObjectNode data) {
        this.data = data;
    }
}
