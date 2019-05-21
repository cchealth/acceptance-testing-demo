package com.accenture.ips.springstub.rs;

import com.accenture.ips.springstub.config.SpringConfig;
import com.accenture.ips.springstub.dto.PatientDataOutputDto;
import com.accenture.ips.springstub.dto.PatientRequestOutputDto;
import com.accenture.ips.springstub.repository.PatientInfoInMemoryStore;
import com.accenture.ips.springstub.util.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringConfig.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@ActiveProfiles("test")
public class PatientControllerIntegrationTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PatientControllerIntegrationTest.class);

	private static final ObjectMapper OBJECT_MAPPER = JsonHelper.commonObjectMapper();

	@LocalServerPort
	int port;

	@Autowired
	private PatientInfoInMemoryStore patientInfoInMemoryStore;

	@Test
	public void getPatientData_shouldReturnData() throws URISyntaxException {
		String urlStr = String.format("http://localhost:%s/patient-data?requestId=key1", port);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		URI uri = new URI(urlStr);

		ObjectNode json = OBJECT_MAPPER.createObjectNode();
		patientInfoInMemoryStore.put("key1", json.put("foo", "bar"));

		HttpStatus status = null;
		PatientDataOutputDto dto = new PatientDataOutputDto();
		try {
			ResponseEntity<PatientDataOutputDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PatientDataOutputDto.class);
			status = response.getStatusCode();
			dto = response.getBody();
		} catch (HttpStatusCodeException ex) {
			status = ex.getStatusCode();
			LOGGER.error(ex.getResponseBodyAsString());
			fail("Expecting 200 response");
		}

		assertEquals(HttpStatus.OK, status);
		assertEquals("bar", dto.getData().path("foo").asText());
	}

	@Test
	public void getPatientData_should400_whenMissingQuerystring() throws URISyntaxException {
		String urlStr = String.format("http://localhost:%s/patient-data", port);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		URI uri = new URI(urlStr);

		HttpStatus status = null;
		try {
			ResponseEntity<PatientDataOutputDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PatientDataOutputDto.class);
			fail("Expecting 400");
		} catch (HttpStatusCodeException ex) {
			status = ex.getStatusCode();
			assertEquals(HttpStatus.BAD_REQUEST, status);
			LOGGER.info(ex.getResponseBodyAsString());
		}
	}

	@Test
	public void getPatientRequest_shouldReturnData() throws URISyntaxException {
		String urlStr = String.format("http://localhost:%s/patient-request?patientId=abc", port);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		URI uri = new URI(urlStr);

		HttpStatus status = null;
		PatientRequestOutputDto dto = new PatientRequestOutputDto();
		try {
			ResponseEntity<PatientRequestOutputDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PatientRequestOutputDto.class);
			status = response.getStatusCode();
			dto = response.getBody();
		} catch (HttpStatusCodeException ex) {
			status = ex.getStatusCode();
			LOGGER.error(ex.getResponseBodyAsString());
			fail("Expecting 200 response");
		}

		assertEquals(HttpStatus.OK, status);
		assertNotNull(dto.getRequestId());
	}

	@Test
	public void getPatientRequest_should400_whenMissingQuerystring() throws URISyntaxException {
		String urlStr = String.format("http://localhost:%s/patient-request", port);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		URI uri = new URI(urlStr);

		HttpStatus status = null;
		PatientRequestOutputDto dto = new PatientRequestOutputDto();
		try {
			ResponseEntity<PatientRequestOutputDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, PatientRequestOutputDto.class);
			fail("Expecting 400 response");
		} catch (HttpStatusCodeException ex) {
			status = ex.getStatusCode();
			LOGGER.info(ex.getResponseBodyAsString());
			assertEquals(HttpStatus.BAD_REQUEST, status);
		}
	}

}
