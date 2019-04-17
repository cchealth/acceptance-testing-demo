package com.accenture.ips.springstub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.prop")
public class ApplicationProperties {

	private String sampleProp;

	public String getSampleProp() {
		return sampleProp;
	}

	public void setSampleProp(String sampleProp) {
		this.sampleProp = sampleProp;
	}

}
