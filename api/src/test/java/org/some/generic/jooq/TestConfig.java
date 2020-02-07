package org.some.generic.jooq;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import javax.sql.DataSource;
import org.some.generic.jooq.migration.Migrator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@TestConfiguration
public class TestConfig {

    @Bean
    public DataSource dataSource() throws Exception {
      System.getProperties().setProperty("org.jooq.no-logo", "true");
      DataSource dataSource = EmbeddedPostgres
          .builder()
          .setPgBinaryResolver(
              (system, machineHardware) ->
                  new ClassPathResource(String.format("postgresql-%s-%s.txz", system, machineHardware)).getInputStream()
          )
          .start()
          .getTemplateDatabase();

      Migrator migrator = new Migrator(dataSource);
      migrator.migrate();
      return dataSource;
    }
}
