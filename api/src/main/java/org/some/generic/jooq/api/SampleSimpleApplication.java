package org.some.generic.jooq.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleSimpleApplication {

  public static void main(String[] args) {
    System.getProperties().setProperty("org.jooq.no-logo", "true");
    SpringApplication.run(SampleSimpleApplication.class, args);
  }
}
