alter table SOME_PLACE
  add column OWNER_ID bigint not null,
  add foreign key(OWNER_ID) references SOME_PEOPLE(id);
