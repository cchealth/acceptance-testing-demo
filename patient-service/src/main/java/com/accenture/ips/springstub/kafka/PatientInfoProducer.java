package com.accenture.ips.springstub.kafka;

import com.accenture.ips.springstub.dto.FhirFindPatientRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientInfoProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientInfoProducer.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.patient.info.topic:PATIENT_INFO_QUEUE}")
    private String patientInfoTopic;

    public void sendMessage(FhirFindPatientRequestDto message) {
        try {
            LOGGER.info("Sending message to kafka: {}", message.getRequestId());
            kafkaTemplate.send(patientInfoTopic, OBJECT_MAPPER.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while trying to produce message to kafka", e);
        }
    }
}
