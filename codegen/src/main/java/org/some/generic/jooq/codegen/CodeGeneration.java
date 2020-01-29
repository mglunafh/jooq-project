package org.some.generic.jooq.codegen;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import java.io.IOException;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.some.generic.jooq.migration.Migrator;


public class CodeGeneration {

  public static void main(String[] args) {

    migrateDatabase();
  }

  private static void migrateDatabase() {
    PGSimpleDataSource source = new PGSimpleDataSource();
    source.setServerName(System.getenv("DB_HOST"));
    source.setDatabaseName(System.getenv("DB_NAME"));
    source.setUser(System.getenv("DB_USER"));
    source.setPassword(System.getenv("DB_PASS"));

    Migrator migrator = new Migrator(source);
    migrator.migrate();
  }

  private static DataSource getPostres() throws IOException {

    EmbeddedPostgres start = EmbeddedPostgres.start();
    DataSource dataSource = start.getPostgresDatabase();
    return dataSource;
  }
}
