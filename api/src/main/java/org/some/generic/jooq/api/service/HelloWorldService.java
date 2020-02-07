package org.some.generic.jooq.api.service;

import static org.some.generic.jooq.generated.Tables.CITY;
import static org.some.generic.jooq.generated.Tables.SOME_PEOPLE;
import static org.some.generic.jooq.generated.Tables.SOME_TABLE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.some.generic.jooq.api.entity.City;
import org.some.generic.jooq.api.entity.Person;
import org.some.generic.jooq.api.entity.RecordConverter;
import org.some.generic.jooq.api.entity.Some;
import org.some.generic.jooq.api.utils.TemplateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class HelloWorldService {

  @Autowired
  private TemplateProcessor processor;

  @Autowired
  private DSLContext context;

  public String getBody() {
    Map<String, Object> params = new HashMap<>();

    List<Some> someTableContents = getSomeTableContents();
    params.put("some_list", someTableContents);

    List<Person> people = getPeople();
    params.put("people", people);

    List<City> cities = getCities();
    params.put("cities", cities);

    return processor.createEmailBody("mail/contents", params);
  }

  private List<City> getCities() {
    return context.select()
        .from(CITY)
        .fetchStream()
        .map(RecordConverter.TO_CITY)
        .collect(Collectors.toList());
  }

  private List<Some> getSomeTableContents() {
    Result<Record> fetch = context.select()
        .from(SOME_TABLE)
        .fetch();

    List<Some> list = new ArrayList<>();
    for (Record rec : fetch) {
      list.add(RecordConverter.TO_SOME.apply(rec));
    }
    return list;
  }

  private List<Person> getPeople() {
    return context.select()
        .from(SOME_PEOPLE)
        .fetchStream()
        .map(RecordConverter.TO_PERSON)
        .collect(Collectors.toList());
  }
}
