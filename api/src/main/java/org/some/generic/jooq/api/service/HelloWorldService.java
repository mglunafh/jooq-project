package org.some.generic.jooq.api.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class HelloWorldService {

  @Value("${name:World}")
  private String name;

  @Value("${DB_HOST}")
  private String host;

  @Value("${DB_NAME}")
  private String dbName;

  @Value("${DB_USER}")
  private String dbUser;

  @Value("${DB_PASS}")
  private String dbPassword;

  public String getHelloMessage() {
    return "Hello " + this.name;
  }

}
