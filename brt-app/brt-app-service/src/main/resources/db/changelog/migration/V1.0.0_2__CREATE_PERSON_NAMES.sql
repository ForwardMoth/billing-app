CREATE TABLE person_names
(
    id         bigserial                not null,
    uuid       uuid                     not null,
    created    timestamp with time zone not null default now(),
    updated    timestamp with time zone not null default now(),
    actual     boolean                  not null default true,
    surname    varchar(255)             not null,
    name       varchar(255)             not null,
    patronymic varchar(255)             not null,

    constraint pk_person_names_id primary key (id),
    constraint uq_person_names_uuid unique (uuid),
    constraint uq_person_names_fio unique (surname, name, patronymic)
);

create index idx_person_names_actual on person_names (actual);

comment on table person_names is 'Справочник данных имен (ФИО) абонентов';
comment on column person_names.id is 'Внутренний идентификатор';
comment on column person_names.uuid is 'Внешний идентификатор';
comment on column person_names.created is 'Дата и время создания';
comment on column person_names.updated is 'Дата и время создания';
comment on column person_names.actual is 'Признак актуальности';
comment on column person_names.surname is 'Фамилия абонента';
comment on column person_names.name is 'Имя абонента';
comment on column person_names.patronymic is 'Отчество абонента';

INSERT INTO person_names (uuid, surname, name, patronymic)
VALUES ('1485b0b1-3ef1-49b8-a936-271a89633add','Иванов', 'Иван', 'Иванович'),
       ('c00b1eda-6c7d-4b4d-84b1-4b1f4936631e','Петров', 'Петр', 'Петрович'),
       ('c159bf87-991b-464b-87b0-0c42f97c6da0','Александров', 'Александр', 'Александрович'),
       ('4d726d69-8620-4408-9a62-31e3f0f320de','Михайлов', 'Михаил', 'Михайлович');