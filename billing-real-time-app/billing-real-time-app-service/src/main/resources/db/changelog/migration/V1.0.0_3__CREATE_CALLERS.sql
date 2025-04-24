CREATE TABLE callers
(
    id             bigserial                not null,
    uuid           uuid                     not null,
    created        timestamp with time zone not null default now(),
    updated        timestamp with time zone not null default now(),
    actual         boolean                  not null default true,
    msisdn         varchar(255)             not null,
    operator_id    bigint                   not null,
    person_name_id bigint,

    constraint pk_callers_id primary key (id),
    constraint callers_uuid unique (uuid),
    constraint fk_callers_operator_id foreign key (operator_id) references operators (id),
    constraint fk_callers_person_name_id foreign key (person_name_id) references person_names (id)
);

create index idx_callers_actual on callers (actual);
create index idx_callers_operator_id on callers (operator_id);
create index idx_callers_person_name_id on callers (person_name_id) WHERE person_name_id IS NOT NULL;

comment on table callers is 'Справочник абонентов';
comment on column callers.id is 'Внутренний идентификатор';
comment on column callers.uuid is 'Внешний идентификатор';
comment on column callers.created is 'Дата и время создания';
comment on column callers.updated is 'Дата и время создания';
comment on column callers.actual is 'Признак актуальности';
comment on column callers.msisdn is 'Номер телефона абонента';
comment on column callers.operator_id is 'Идентификатор из справочника операторов';
comment on column callers.person_name_id is 'Идентификатор из справочника имен';

INSERT INTO callers (uuid, msisdn, operator_id, person_name_id)
VALUES ('50892b70-636f-4894-aa90-610eeb14acc1', '79994567890', 1, 1),
       ('cdc4bfd5-8613-42b0-b092-7d7392fc0241', '79991234567', 1, 2),
       ('6f8036d9-4252-4612-9b00-70571a27712c', '79993456123', 1, 3),
       ('f89a3398-165b-471d-97f0-f7fc498f0f62', '79997896543', 1, 4);