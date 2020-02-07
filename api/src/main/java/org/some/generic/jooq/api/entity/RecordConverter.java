package org.some.generic.jooq.api.entity;

import static org.some.generic.jooq.generated.Tables.SOME_TABLE;
import static org.some.generic.jooq.generated.tables.SomePeople.SOME_PEOPLE;

import java.util.function.Function;
import org.jooq.Record;

public class RecordConverter {

  public static Function<Record, Person> TO_PERSON = record ->
      new Person(record.get(SOME_PEOPLE.ID), record.get(SOME_PEOPLE.FIRSTNAME), record.get(SOME_PEOPLE.LASTNAME));

  public static Function<Record, Some> TO_SOME = record ->
      new Some(record.get(SOME_TABLE.ID), record.get(SOME_TABLE.NAME));
}
