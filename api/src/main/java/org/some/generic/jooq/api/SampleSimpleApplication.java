package org.some.generic.jooq.api;

import static org.some.generic.jooq.generated.Tables.SOME_PEOPLE;
import static org.some.generic.jooq.generated.Tables.SOME_TABLE;

import javax.sql.DataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.some.generic.jooq.api.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleSimpleApplication implements CommandLineRunner {

  @Autowired
  private HelloWorldService helloWorldService;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) {
    SpringApplication.run(SampleSimpleApplication.class, args);
  }

  @Override
  public void run(String... args) {

    System.out.println(this.helloWorldService.getHelloMessage());
    DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES);
    workWithDatabase(context);
  }

  private void workWithDatabase(DSLContext context) {
    Result<Record> fetch = context.select()
        .from(SOME_TABLE)
        .fetch();

    for (Record r : fetch) {
      System.out.printf("%d, %s%n", r.getValue(SOME_TABLE.ID), r.getValue(SOME_TABLE.NAME));
    }

    context.select()
        .from(SOME_PEOPLE)
        .fetchStream()
        .forEach(record ->
            System.out.printf("%d: %s %s%n",
                record.get(SOME_PEOPLE.ID),
                record.get(SOME_PEOPLE.FIRSTNAME),
                record.get(SOME_PEOPLE.LASTNAME)));
  }
}
