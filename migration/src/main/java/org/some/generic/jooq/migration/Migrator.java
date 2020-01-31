package org.some.generic.jooq.migration;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;


public class Migrator {

  public static final String PREFIX = "some-";
  public static final String LOCATIONS = "classpath:db/migration";
  public static final String SCHEMA = "some_schema";
  public static final String TABLE = "schema_version";

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
