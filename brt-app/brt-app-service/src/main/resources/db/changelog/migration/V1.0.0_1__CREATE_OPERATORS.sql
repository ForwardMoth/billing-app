CREATE TABLE operators
(
    id      bigserial                not null,
    uuid    uuid                     not null,
    created timestamp with time zone not null default now(),
    updated timestamp with time zone not null default now(),
    actual  boolean                  not null default true,
    name    varchar(255)             not null,

    constraint pk_operators_id primary key (id),
    constraint uq_operators_uuid unique (uuid),
    constraint uq_operators_name unique (name)
);

create index idx_operators_actual on operators (actual);

comment on table operators is 'Справочник операторов';
comment on column operators.id is 'Внутренний идентификатор';
comment on column operators.uuid is 'Внешний идентификатор';
comment on column operators.created is 'Дата и время создания';
comment on column operators.updated is 'Дата и время создания';
comment on column operators.actual is 'Признак актуальности';
comment on column operators.name is 'Название оператора';

INSERT INTO operators (uuid, name)
VALUES ('2e40448a-65cf-4a37-9bde-e71966c4e55d', 'Ромашка'),
       ('3ae5232f-5c43-4d81-8712-62ae9204ab0a', 'Роза'),
       ('81dd3fa0-ef69-4fd1-8ce9-5ce321402cf3', 'Тюльпан'),
       ('30aed396-deb0-4811-ab08-c462c1b68136', 'Календула'),
       ('d9a44ba6-932a-440e-89b1-145daa9a4d15', 'Одуванчик'),
       ('406a103f-decc-43ff-bc41-dcd2058be82c', 'Георгин');
