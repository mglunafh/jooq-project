package org.some.generic.jooq.api;

import static org.some.generic.jooq.generated.Tables.SOME_PEOPLE;
import static org.some.generic.jooq.generated.Tables.SOME_TABLE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
public class SampleSimpleApplication implements CommandLineRunner {

  @Autowired
  private HelloWorldService helloWorldService;

  @Override
  public void run(String... args) {

    System.out.println(this.helloWorldService.getHelloMessage());

    String url = String
        .format("jdbc:postgresql://%s:5432/%s", helloWorldService.getHost(), helloWorldService.getDbName());

    String username = helloWorldService.getDbUser();
    String password = helloWorldService.getDbPassword();

    try (Connection con = DriverManager.getConnection(url, username, password)) {
      System.out.println("Connection obtained");
      DSLContext context = DSL.using(con, SQLDialect.POSTGRES);

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

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleSimpleApplication.class, args);
  }
}
