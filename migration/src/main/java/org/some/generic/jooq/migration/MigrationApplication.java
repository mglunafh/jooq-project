package org.some.generic.jooq.migration;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;


@Component
@EnableAutoConfiguration
@RequiredArgsConstructor
public class MigrationApplication implements CommandLineRunner {

  @Autowired
  private final DataSource dataSource;

  public static void main(String[] args) {

    System.out.println("Hello there, migration started");
    SpringApplication app = new SpringApplication(MigrationApplication.class);
    app.run(args);
  }

  @Override
  public void run(String... args) {
    Migrator migrator = new Migrator(dataSource);
    migrator.migrate();
  }
}
