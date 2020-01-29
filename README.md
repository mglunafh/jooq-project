# jooq-project

Project with PostgresQL, Flyway, jOOQ and generic application, where migration, 
code generation and actual application are separated from one another

## Contents
1. Migration submodule. It contains migrations, application that performs migrations on a database, and 
    so-called migrator capable of migrating any given (Postgres) data source to the most recent version.
2. Codegen submodule (not invented yet).
3. Api submodule (not invented yet).

## Prerequisites
1. Docker and Docker Compose are used to set up a containerized Postgres database instance.
2. Environment variables `DB_HOST`, `DB_NAME`, `DB_USER`, `DB_PASS` should be set prior the first run. 
    They are to be used by Docker Compose and the Postgres image to set up database with these credentials. 

## Usage
0. Download the project, open its folder in terminal.
1. Run `docker-compose up -d`. If everything is ok, i.e. creation of containers completed successfully, 
    you can perform migrations!
2. Run `org.some.generic.jooq.migration.MigrationApplication` from Maven submodule `migration` 
    to perform migrations.
3. After you are done, you may want to shut docker container down. If so, run `docker-compose down`, 
    this will do the trick.

## Useful commands
1. To break into the database container with name `some-pg`: `docker exec -it some-pg bash`.
2. To break into a database of container with name `some-pg`: `docker exec -it some-pg psql $DB_NAME -U $DB_USER`.
3. It may happen that container refuses to create database with your custom credentials, and 
    only (user 'postgres') and/or (database 'postgres') exist. One possible reason could be 
    docker-compose created volume and database with default parameters. 
    Helpful link: https://github.com/docker-library/postgres/issues/203. Helpful commands: `docker inspect` 
    and `docker volume prune` (<-- WATCH OUT OF THE LATTER ONE!).
