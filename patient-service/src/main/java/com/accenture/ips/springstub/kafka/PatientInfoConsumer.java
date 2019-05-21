package com.accenture.ips.springstub.kafka;

import com.accenture.ips.springstub.dto.FhirFindPatientRequestDto;
import com.accenture.ips.springstub.exception.ApiError;
import com.accenture.ips.springstub.exception.ApiErrorException;
import com.accenture.ips.springstub.exception.ECode;
import com.accenture.ips.springstub.service.PatientService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PatientInfoConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientInfoConsumer.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private PatientService patientService;

    @KafkaListener(topics = "${kafka.patient.info.topic:PATIENT_INFO_QUEUE}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) throws IOException {
        LOGGER.info("Got message from Kafka.");

        try {
            FhirFindPatientRequestDto request = OBJECT_MAPPER.readValue(message, FhirFindPatientRequestDto.class);
            LOGGER.info("Read value from Kakfa: {}", request.getRequestId());

            patientService.fetchExternalPatientData(request.getRequestId(),
                    request.getSearchParameters().getPatientId());
        } catch (JsonParseException e) {
            throw new ApiErrorException(ApiError.of(ECode.CANNOT_PARSE_REQUEST), e);
        }
    }
}
