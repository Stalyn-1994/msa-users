package com.devsu.users.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Configuration
public class ApplicationConfiguration {

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }

}
