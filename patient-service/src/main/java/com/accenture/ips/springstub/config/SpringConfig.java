package com.accenture.ips.springstub.config;

import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@ComponentScan(basePackageClasses = { com.accenture.ips.springstub.rs.PackageScanDummy.class })
@ComponentScan({"com.accenture.ips.springstub"})
@EnableSwagger2
@Configuration
//@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
