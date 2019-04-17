package com.accenture.ips.springstub.kafka;

import com.accenture.ips.springstub.dto.FhirFindPatientParams;
import com.accenture.ips.springstub.dto.FhirFindPatientRequestDto;
import com.accenture.ips.springstub.exception.ApiErrorException;
import com.accenture.ips.springstub.service.PatientService;
import com.accenture.ips.springstub.util.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PatientInfoConsumerUnitTest {
    private static final ObjectMapper OBJECT_MAPPER = JsonHelper.commonObjectMapper();

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientInfoConsumer patientInfoConsumer;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void consume_shouldProcessValidString() throws Exception {
        patientInfoConsumer.consume(fhirFindPatientRequestDtoAsString());

        verify(patientService).fetchExternalPatientData(eq("requestId"), eq("patientId"));
    }

    @Test
    public void consume_shouldThrowError_whenBadJson() throws Exception {
        try {
            patientInfoConsumer.consume("notJson");
            fail("Expecting exception");
        } catch (ApiErrorException e) {
            assertNotNull(e.getApiError());
            assertEquals("10001", e.getApiError().getErrorCode());
            assertEquals("500", e.getApiError().getHttpStatusCode());
        }
    }

    private String fhirFindPatientRequestDtoAsString() throws JsonProcessingException {
        FhirFindPatientRequestDto dto = new FhirFindPatientRequestDto();
        dto.setRequestId("requestId");
        FhirFindPatientParams params = new FhirFindPatientParams();
        params.setPatientId("patientId");
        dto.setSearchParameters(params);

        return OBJECT_MAPPER.writeValueAsString(dto);
    }
}
