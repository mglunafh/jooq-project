package org.some.generic.jooq.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Person {

  private final Long id;
  private final String firstName;
  private final String lastName;
}
