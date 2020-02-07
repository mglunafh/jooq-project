package org.some.generic.jooq.api;

import org.some.generic.jooq.api.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

  @Autowired
  private HelloWorldService service;

  @GetMapping("/")
  public String index() {
    return service.getBody();
  }
}
