# jooq-project

Project with PostgreSQL, Flyway, jOOQ and generic application, where migration, 
code generation and actual application are separated from one another.

## Contents
1. **Migration** submodule. It contains migrations, application that performs migrations on a database, and 
    so-called migrator capable of migrating any given (Postgres) data source to the most recent version.
2. **Codegen** submodule. It contains code generation tool. It performs migration of embedded PostgreSQL 
    database using **migration** submodule, and then runs jOOQ generator to produce classes that reflect 
    database structure.
3. **Api** submodule. It contains a simple web-application powered up by Spring Boot.

## Prerequisites
1. Docker and Docker Compose are used to set up a containerized Postgres database instance.
2. Environment variables `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASS` should be set prior the first run. 
    They are to be used by Docker Compose and the Postgres image to set up database with these credentials. 

## Usage
- Download the project, open its folder in terminal.
- Run `docker-compose up -d`. If everything is ok, i.e. creation of containers completed successfully, 
    you can perform migrations!
- You can run `org.some.generic.jooq.migration.MigrationApplication` from Maven submodule `migration` 
    to perform migrations.
- You can run `org.some.generic.jooq.codegen.CodeGeneration` from `codegen` submodule to produce some 
    nice jOOQ classes that are to be used by other projects. But wait, there is more: Maven POM file is 
    configured to compile and run this code-generation tool during the 'generate-source' phase, and it's 
    the generated sources that are actually compiled during 'compile' phase.
- In the end, you can run `org.some.generic.jooq.api.SimpleSampleApplication` and proceed to `127.0.0.1:8080/` 
    in your favourite web-browser.

## Useful commands
1. Break into the database container with name `some-pg`: `docker exec -it some-pg bash`.
2. Break into a database of container with name `some-pg`: `docker exec -it some-pg psql $DB_NAME -U $DB_USER`.
3. After you are done, you may want to shut docker container down. If so, run `docker-compose down`, 
    this will do the trick.
4. It may happen that container refuses to create database with your custom credentials, and 
    only (user 'postgres') and/or (database 'postgres') exist. One possible reason could be 
    docker-compose created volume and database with default parameters. 
    Helpful link: https://github.com/docker-library/postgres/issues/203. Helpful commands: `docker inspect` 
    and `docker volume prune` (<-- WATCH OUT FOR THE LATTER ONE!).
