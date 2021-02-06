package com.robosoftin.evaluation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Author nagesh
 * inside the package - com.robosoftin.evaluation.constants
 **/
@Configuration
public class GenericConfiguaration {

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
