CREATE TABLE call_data_records
(
    id                  bigserial                not null,
    uuid                uuid                     not null,
    created             timestamp with time zone not null default now(),
    updated             timestamp with time zone not null default now(),
    actual              boolean                  not null default true,
    incoming_caller_id  bigint                   not null,
    outcoming_caller_id bigint                   not null,
    start_call_time     timestamp without time zone not null,
    finish_call_time    timestamp without time zone not null,

    constraint pk_call_data_records_id primary key (id),
    constraint uq_call_data_records_uuid unique (uuid),
    constraint fk_call_data_records_incoming_caller_id foreign key (incoming_caller_id) references callers (id),
    constraint fk_call_data_records_outcoming_caller_id foreign key (outcoming_caller_id) references callers (id)
);

create index idx_call_data_records_actual on call_data_records (actual);
create index idx_call_data_records_incoming_caller_id on call_data_records (incoming_caller_id);
create index idx_call_data_records_outcoming_caller_id on call_data_records (outcoming_caller_id);

comment on table call_data_records is 'Список звонков';
comment on column call_data_records.id is 'Внутренний идентификатор';
comment on column call_data_records.uuid is 'Внешний идентификатор';
comment on column call_data_records.created is 'Дата и время создания';
comment on column call_data_records.updated is 'Дата и время создания';
comment on column call_data_records.actual is 'Признак актуальности';
comment on column call_data_records.incoming_caller_id is 'Идентификатор входящего абонента';
comment on column call_data_records.outcoming_caller_id is 'Идентификатор исходящего абонента';
comment on column call_data_records.start_call_time is 'Дата и время начала звонка';
comment on column call_data_records.finish_call_time is 'Дата и время окончания звонка';