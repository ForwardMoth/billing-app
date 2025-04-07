CREATE TABLE callers
(
    id           bigserial                not null,
    created      timestamp with time zone not null default now(),
    updated      timestamp with time zone not null default now(),
    msisdn varchar(255)             not null,

    constraint pk_callers_id primary key (id)
);

comment on table callers is 'Справочник абонентов';
comment on column callers.id is 'Внутренний идентификатор';
comment on column callers.created is 'Дата и время создания';
comment on column callers.updated is 'Дата и время создания';
comment on column callers.msisdn is 'Номер абонента';

