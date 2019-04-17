package com.accenture.ips.springstub.service.impl;

import com.accenture.ips.springstub.dto.FhirFindPatientRequestDto;
import com.accenture.ips.springstub.dto.HttpResponseDto;
import com.accenture.ips.springstub.dto.PatientDataOutputDto;
import com.accenture.ips.springstub.exception.ApiErrorException;
import com.accenture.ips.springstub.kafka.PatientInfoProducer;
import com.accenture.ips.springstub.repository.PatientInfoInMemoryStore;
import com.accenture.ips.springstub.util.HttpClientHelper;
import com.accenture.ips.springstub.util.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import static org.junit.Assert.*;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;


public class PatientServiceImplUnitTest {
    private static final ObjectMapper OBJECT_MAPPER = JsonHelper.commonObjectMapper();

    @Mock
    private HttpClientHelper httpClientHelper;

    @Mock
    private PatientInfoProducer patientInfoProducer;

    @Mock
    private PatientInfoInMemoryStore patientInfoInMemoryStore;

    @Mock
    private Call call;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Captor
    private ArgumentCaptor<FhirFindPatientRequestDto> requestCaptor;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void requestPatientData_shouldProduceToKafka() {
        patientService.requestPatientData("patient123");

        verify(patientInfoProducer).sendMessage(requestCaptor.capture());

        FhirFindPatientRequestDto requestDto = requestCaptor.getValue();
        assertNotNull(requestDto.getRequestId());
        assertEquals("patient123", requestDto.getSearchParameters().getPatientId());
    }

    @Test
    public void getPatientData_shouldGetPatient_whenPresent() {
        when(patientInfoInMemoryStore.get(eq("requestId"))).thenReturn(OBJECT_MAPPER.createObjectNode());

        PatientDataOutputDto result = patientService.getPatientData("requestId");

        assertNotNull(result);
        assertNotNull(result.getData());
    }

    @Test
    public void getPatientData_shouldThrowError_whenNotPresent() {
        try {
            patientService.getPatientData("doesNotExist");
            fail("Expecting ApiErrorException");
        } catch (ApiErrorException e) {
            assertNotNull(e.getApiError());
            assertEquals("10000", e.getApiError().getErrorCode());
            assertEquals("404", e.getApiError().getHttpStatusCode());
        }
    }

    @Test
    public void fetchExternalPatientData_shouldSaveExternalData() throws Exception {
        when(httpClientHelper.get(anyString())).thenReturn(stubResponse(200));

        patientService.fetchExternalPatientData("requestId", "patientId");

        verify(httpClientHelper).get(endsWith("/patientId"));
        verify(patientInfoInMemoryStore).put(eq("requestId"), any(ObjectNode.class));
    }

    @Test
    public void fetchExternalPatientData_shouldNotSaveExternalData_whenExternalHasError() throws Exception {
        when(httpClientHelper.get(anyString())).thenReturn(stubResponse(404));

        patientService.fetchExternalPatientData("requestId", "doesNotExist");

        verify(httpClientHelper).get(endsWith("/doesNotExist"));
        verify(patientInfoInMemoryStore, never()).put(eq("requestId"), any(ObjectNode.class));
    }

    @Test
    public void fetchExternalPatientData_shouldThrowError_whenFieldsAreNull() throws Exception {
        try {
            patientService.fetchExternalPatientData(null, "doesNotExist");
            fail("exception expected");
        } catch (ApiErrorException e) {
            assertEquals("10002", e.getApiError().getErrorCode());
        }

        try {
            patientService.fetchExternalPatientData("requestId", null);
            fail("exception expected");
        } catch (ApiErrorException e) {
            assertEquals("10002", e.getApiError().getErrorCode());
        }
    }

    private HttpResponseDto stubResponse(int statusCode) {
        HttpResponseDto res = new HttpResponseDto();
        res.setHttpStatus(statusCode);
        res.setResponseBody("{}");

        return res;
    }

}
