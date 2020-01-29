update SOME_PEOPLE
  set lastname = ''
  where lastname IS NULL;

alter table SOME_PEOPLE
  alter column LASTNAME set not null;
