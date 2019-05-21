package com.accenture.ips.springstub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.accenture.ips.springstub.config.ApplicationProperties;
import com.accenture.ips.springstub.config.SpringConfig;

@EnableAutoConfiguration
@Import(SpringConfig.class)
//@EnableConfigurationProperties(ApplicationProperties.class)
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
