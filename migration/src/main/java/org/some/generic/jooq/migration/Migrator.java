package org.some.generic.jooq.migration;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;


public class Migrator {

  private static final String PREFIX = "some-";
  private static final String LOCATIONS = "classpath:db/migration";
  private static final String SCHEMA = "some_schema";
  private static final String TABLE = "schema_version";

  private final Flyway flyway;

  public Migrator(DataSource dataSource) {

    this.flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations(LOCATIONS)
        .sqlMigrationPrefix(PREFIX)
        .schemas(SCHEMA)
        .table(TABLE)
        .load();
  }

  public void migrate() {
    flyway.migrate();
  }
}
