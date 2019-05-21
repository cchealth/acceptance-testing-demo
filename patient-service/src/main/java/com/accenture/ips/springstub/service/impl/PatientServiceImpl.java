package com.accenture.ips.springstub.service.impl;

import com.accenture.ips.springstub.dto.FhirFindPatientParams;
import com.accenture.ips.springstub.dto.FhirFindPatientRequestDto;
import com.accenture.ips.springstub.dto.HttpResponseDto;
import com.accenture.ips.springstub.dto.PatientDataOutputDto;
import com.accenture.ips.springstub.dto.PatientRequestOutputDto;
import com.accenture.ips.springstub.exception.ApiError;
import com.accenture.ips.springstub.exception.ApiErrorException;
import com.accenture.ips.springstub.exception.ECode;
import com.accenture.ips.springstub.kafka.PatientInfoProducer;
import com.accenture.ips.springstub.repository.PatientInfoInMemoryStore;
import com.accenture.ips.springstub.service.PatientService;
import com.accenture.ips.springstub.util.HttpClientHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private HttpClientHelper httpClientHelper;

    @Autowired
    private PatientInfoProducer patientInfoProducer;

    @Autowired
    private PatientInfoInMemoryStore patientInfoInMemoryStore;

    @Value("${fhir.server.root.url}")
    private String fhirRootUrl;

    @Override
    public PatientRequestOutputDto requestPatientData(String patientId) {
        UUID generatedId = UUID.randomUUID();

        PatientRequestOutputDto dto = new PatientRequestOutputDto();
        dto.setRequestId(generatedId.toString());

        FhirFindPatientRequestDto kafkaRequest = new FhirFindPatientRequestDto();
        FhirFindPatientParams params = new FhirFindPatientParams();
        params.setPatientId(patientId);
        kafkaRequest.setRequestId(generatedId.toString());
        kafkaRequest.setSearchParameters(params);
        patientInfoProducer.sendMessage(kafkaRequest);

        return dto;
    }

    @Override
    public PatientDataOutputDto getPatientData(String requestId) {
        ObjectNode data = patientInfoInMemoryStore.get(requestId);
        if (data == null) {
            throw new ApiErrorException(ApiError.of(ECode.NO_PATIENT_FOUND));
        }

        PatientDataOutputDto dto = new PatientDataOutputDto();
        dto.setData(data);

        return dto;
    }

    @Override
    public void fetchExternalPatientData(String requestId, String patientId) {
        if (requestId == null || patientId == null) {
            throw new ApiErrorException(ApiError.of(ECode.PATIENT_AND_REQUEST_ID_REQUIRED));
        }
        try {
            ObjectNode fhirData = getFhirPatientData(requestId, patientId);
            if (fhirData != null) {
                LOGGER.info("Putting data into InMemory store, key={}", requestId);
                patientInfoInMemoryStore.put(requestId, fhirData);
            }
        } catch (IOException e) {
            LOGGER.error("IOException while trying to get FHIR patient data", e);
            throw new ApiErrorException(ApiError.of(ECode.UNEXPECTED_ERROR));
        }
    }

    private ObjectNode getFhirPatientData(String requestId, String patientId) throws IOException {
        String url = fhirRootUrl + "/Patient/" + patientId;
        HttpResponseDto res = httpClientHelper.get(url);
        if (res.getHttpStatus() == 200) {
            return (ObjectNode) OBJECT_MAPPER.readTree(res.getResponseBody());
        } else {
            if (res.getHttpStatus() == 404) {
                LOGGER.warn("Patient not found in external FHIR server, requestId={}", requestId);
            } else {
                LOGGER.error("Error from FHIR server: {}", res.getResponseBody());
            }
            return null;
        }
    }

}
