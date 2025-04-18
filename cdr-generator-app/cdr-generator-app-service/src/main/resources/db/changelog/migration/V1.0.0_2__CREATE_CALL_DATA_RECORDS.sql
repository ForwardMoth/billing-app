CREATE TABLE call_data_records
(
    id                  bigserial                   not null,
    incoming_caller_id  bigint                      not null,
    outcoming_caller_id bigint                      not null,
    start_call_time     timestamp without time zone not null,
    finish_call_time    timestamp without time zone not null,
    is_sent             boolean                     not null default false,

    constraint pk_call_data_records_id primary key (id),
    constraint fk_call_data_records_incoming_caller_id foreign key (incoming_caller_id) references callers (id),
    constraint fk_call_data_records_outcoming_caller_id foreign key (outcoming_caller_id) references callers (id)
);

comment on table call_data_records is 'Действия, совершенные абонентом за тарифицируемый период';
comment on column call_data_records.id is 'Внутренний идентификатор';
comment on column call_data_records.incoming_caller_id is 'Идентификатор входящего абонента';
comment on column call_data_records.outcoming_caller_id is 'Идентификатор исходящего абонента';
comment on column call_data_records.start_call_time is 'Дата и время начала звонка';
comment on column call_data_records.finish_call_time is 'Дата и время окончания звонка';
comment on column call_data_records.is_sent is 'Признак отправления записи в BRT';
