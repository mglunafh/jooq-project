package org.some.generic.jooq.migration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;


public class Migrator {

  private static final String PROP_PREFIX = "flyway.sql-migration-prefix";
  private static final String PROP_LOCATIONS = "flyway.locations";
  private static final String PROP_SCHEMA = "flyway.schemas";
  private static final String PROP_TABLE = "flyway.table";

  private final Flyway flyway;

  public Migrator(DataSource dataSource) {

    Properties prop = getValidatedProperties();
    this.flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations(prop.getProperty(PROP_LOCATIONS))
        .sqlMigrationPrefix(prop.getProperty(PROP_PREFIX))
        .schemas(prop.getProperty(PROP_SCHEMA))
        .table(prop.getProperty(PROP_TABLE))
        .load();
  }

  private static Properties getValidatedProperties() {
    try (InputStream stream = Migrator.class.getClassLoader().getResourceAsStream("application.properties")) {
      Properties prop = new Properties();
      prop.load(stream);

      String[] necessary = {PROP_LOCATIONS, PROP_PREFIX, PROP_SCHEMA, PROP_TABLE};
      StringBuilder sb = new StringBuilder("The following properties are missing in application.properties: ");
      boolean missingProperties = false;
      for (String property : necessary) {
        if (!prop.containsKey(property)) {
          missingProperties = true;
          sb.append("'").append(property).append("' ");
        }
      }
      if (missingProperties) {
        throw new RuntimeException(sb.toString());
      }
      return prop;

    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void migrate() {
    flyway.migrate();
  }
}
