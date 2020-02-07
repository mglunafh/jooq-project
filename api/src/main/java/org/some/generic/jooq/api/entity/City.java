package org.some.generic.jooq.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class City {

  private final Long id;
  private final String name;
}
