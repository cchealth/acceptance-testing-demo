package com.accenture.ips.springstub.rs;

import com.accenture.ips.springstub.dto.PatientDataOutputDto;
import com.accenture.ips.springstub.dto.PatientRequestOutputDto;
import com.accenture.ips.springstub.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.ips.springstub.config.ApplicationProperties;

import java.util.UUID;

@RestController
//@RequestMapping("${application.prop.request_mapping}")
public class PatientController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	ApplicationProperties appProps;

	@Autowired
	private PatientService patientService;

	@RequestMapping(value = "/patient-request", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PatientRequestOutputDto getPatientRequest(
			@RequestParam(value = "patientId", required = true) String patientId) {

		LOGGER.debug("/patient-request endpoint called");

		return patientService.requestPatientData(patientId);
	}

	@RequestMapping(value = "/patient-data", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PatientDataOutputDto getPatientData(
			@RequestParam(value = "requestId", required = true) String requestId) {

		LOGGER.debug("/patient-data endpoint called");

		return patientService.getPatientData(requestId);
	}

}
