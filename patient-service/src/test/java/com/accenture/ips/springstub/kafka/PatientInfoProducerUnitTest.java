package com.accenture.ips.springstub.kafka;

import com.accenture.ips.springstub.dto.FhirFindPatientRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

public class PatientInfoProducerUnitTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private PatientInfoProducer patientInfoProducer;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(patientInfoProducer,"patientInfoTopic", "MyTopic");
    }

    @Test
    public void sendMessage_shouldSendToKafka() {
        patientInfoProducer.sendMessage(new FhirFindPatientRequestDto());

        verify(kafkaTemplate).send(anyString(), anyString());
    }
}
