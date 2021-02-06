package com.robosoftin.evaluation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author nagesh
 * created on 06/02/21
 * inside the package - com.robosoftin.evaluation.config
 **/
@Configuration
@ConfigurationProperties(prefix = "project.evaluation")
@Data
public class AppSettingConfig {

	private String newsBaseUrl;
	private String newsApiKey;
}
