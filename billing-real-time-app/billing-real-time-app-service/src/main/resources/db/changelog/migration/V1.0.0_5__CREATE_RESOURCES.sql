CREATE TABLE resources
(
    id              bigserial                not null,
    uuid            uuid                     not null,
    created         timestamp with time zone not null default now(),
    updated         timestamp with time zone not null default now(),
    actual          boolean                  not null default true,
    balance         numeric                  not null default 100,
    is_forbid_calls boolean                  not null default false,
    minutes         bigint,
    caller_id       bigint                   not null,

    constraint pk_resources_id primary key (id),
    constraint uq_resources_uuid unique (uuid),
    constraint fk_resources_caller_id foreign key (caller_id) references callers (id)
);

create index idx_resources_actual on resources (actual);
create index idx_resources_caller_id on resources (caller_id);

comment on table resources is 'Список звонков';
comment on column resources.id is 'Внутренний идентификатор';
comment on column resources.uuid is 'Внешний идентификатор';
comment on column resources.created is 'Дата и время создания';
comment on column resources.updated is 'Дата и время создания';
comment on column resources.actual is 'Признак актуальности';
comment on column resources.balance is 'Идентификатор входящего абонента';
comment on column resources.is_forbid_calls is 'Идентификатор исходящего абонента';
comment on column resources.minutes is 'Дата и время начала звонка';
comment on column resources.caller_id is 'Дата и время окончания звонка';

INSERT INTO resources (uuid, caller_id)
VALUES ('2dd81401-6a32-464e-8df3-a867a18214c6', (SELECT id
                                                 FROM callers
                                                 WHERE uuid = '50892b70-636f-4894-aa90-610eeb14acc1')),
       ('ac15743b-2723-40ed-b646-7db95c30cbb0', (SELECT id
                                                 FROM callers
                                                 WHERE uuid = 'cdc4bfd5-8613-42b0-b092-7d7392fc0241')),

       ('c635b77f-c486-489a-8a0e-9ee3d3e17666', (SELECT id
                                                 FROM callers
                                                 WHERE uuid = '6f8036d9-4252-4612-9b00-70571a27712c')),

       ('f732b79b-da43-4c67-84e5-c61b4f369a1a', (SELECT id
                                                 FROM callers
                                                 WHERE uuid = 'f89a3398-165b-471d-97f0-f7fc498f0f62'));
