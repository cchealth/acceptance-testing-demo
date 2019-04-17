package com.accenture.ips.springstub.service;

import com.accenture.ips.springstub.dto.PatientDataOutputDto;
import com.accenture.ips.springstub.dto.PatientRequestOutputDto;

public interface PatientService {

    PatientRequestOutputDto requestPatientData(String patientId);

    PatientDataOutputDto getPatientData(String requestId);

    void fetchExternalPatientData(String requestId, String patientId);
}
