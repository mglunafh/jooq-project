package org.some.generic.jooq.codegen;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import java.io.IOException;
import javax.sql.DataSource;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Logging;
import org.jooq.meta.jaxb.Target;
import org.postgresql.ds.PGSimpleDataSource;
import org.some.generic.jooq.migration.Migrator;


public class CodeGeneration {

  private static final String DB_DRIVER = "org.postgresql.Driver";
  private static final String DB_NAME = "postgres";
  private static final String USER = "postgres";
  private static final String FAKE_PASS = "postgres";
  private static final int PORT_NUMBER = 5434;
  private static final String URL = String.format("jdbc:postgresql://localhost:%d/%s", PORT_NUMBER, DB_NAME);

  public static void main(String[] args) throws Exception {

    DataSource dataSource = getPostres();
    migrateDatabase(dataSource);
    generateDatabaseClasses();
  }

  private static void generateDatabaseClasses() throws Exception {

    Configuration configuration = new Configuration()
        .withJdbc(new Jdbc()
            .withDriver(DB_DRIVER)
            .withUrl(URL)
            .withUser(USER)
            .withPassword(FAKE_PASS)
        )
        .withGenerator(new Generator()
            .withDatabase(new Database()
                .withName("org.jooq.meta.postgres.PostgresDatabase")
                .withInputSchema(Migrator.SCHEMA)
                .withIncludes(".*")
                .withExcludes("")
            )
            .withTarget(new Target()
                .withPackageName("org.some.generic.jooq.generated")
                .withDirectory("target/generated-sources/jooq")
            )
        )
        .withLogging(Logging.DEBUG);

    GenerationTool.generate(configuration);
  }

  private static void migrateDatabase(DataSource source) {
    Migrator migrator = new Migrator(source);
    migrator.migrate();
  }

  private static DataSource getRealDataSource() {
    PGSimpleDataSource source = new PGSimpleDataSource();
    source.setServerName(System.getenv("DB_HOST"));
    source.setDatabaseName(System.getenv("DB_NAME"));
    source.setUser(System.getenv("DB_USER"));
    source.setPassword(System.getenv("DB_PASS"));

    return source;
  }

  private static DataSource getPostres() throws IOException {

    EmbeddedPostgres start = EmbeddedPostgres.builder()
        .setPort(PORT_NUMBER)
        .start();
    DataSource dataSource = start.getPostgresDatabase();
    return dataSource;
  }
}
