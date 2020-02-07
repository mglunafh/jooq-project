package org.some.generic.jooq.api.service;

import static org.some.generic.jooq.generated.tables.SomePeople.SOME_PEOPLE;
import static org.some.generic.jooq.generated.tables.SomeTable.SOME_TABLE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.some.generic.jooq.api.entity.RecordConverter;
import org.some.generic.jooq.api.utils.TemplateProcessor;
import org.some.generic.jooq.api.entity.Person;
import org.some.generic.jooq.api.entity.Some;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private TemplateProcessor processor;

  @Autowired
  private DSLContext context;

  public String getHelloMessage() {
    return "Hello " + this.name;
  }

  public String getBody() {
    Map<String, Object> params = new HashMap<>();

    List<Some> someTableContents = getSomeTableContents();
    params.put("some_list", someTableContents);

    List<Person> people = getPeople();
    params.put("people", people);

    return processor.createEmailBody("mail/contents", params);
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
