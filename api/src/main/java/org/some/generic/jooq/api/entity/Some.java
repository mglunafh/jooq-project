package org.some.generic.jooq.api.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Some {

  private final Long id;
  private final String name;
}
