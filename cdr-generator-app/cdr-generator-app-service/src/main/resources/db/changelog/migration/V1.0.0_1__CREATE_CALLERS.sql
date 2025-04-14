CREATE TABLE callers
(
    id           bigserial                not null,
    msisdn varchar(255)             not null,

    constraint pk_callers_id primary key (id)
);

comment on table callers is 'Справочник абонентов';
comment on column callers.id is 'Внутренний идентификатор';
comment on column callers.msisdn is 'Номер абонента';

