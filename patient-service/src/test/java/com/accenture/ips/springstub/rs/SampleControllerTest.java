package com.accenture.ips.springstub.rs;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
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

import com.accenture.ips.springstub.config.SpringConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringConfig.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@ActiveProfiles("test")
public class SampleControllerTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SampleControllerTest.class);

	@LocalServerPort
	int port;

	@Value("${application.prop.request_mapping}")
	String controllerPath;

	@Value("${server.servlet.context-path}")
	String contextPath;

	@Test
	@Ignore
	public void testExpiredToken() throws URISyntaxException {
		String urlStr = String.format("http://localhost:%s%s%s/sample", port, contextPath, controllerPath);

		log.debug("urlStr->{}", urlStr);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		URI uri = new URI(urlStr);

		HttpStatus status = null;
		String responseBody = null;
		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			responseBody = response.getBody();
			status = response.getStatusCode();
		} catch (HttpStatusCodeException ex) {
			status = ex.getStatusCode();
		}

		log.debug("response->{} : {}", status, responseBody);
		assertEquals(HttpStatus.OK, status);
	}

}
