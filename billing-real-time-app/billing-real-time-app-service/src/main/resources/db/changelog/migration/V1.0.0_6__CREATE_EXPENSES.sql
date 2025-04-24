CREATE TABLE expenses
(
    id            bigserial                not null,
    uuid          uuid                     not null,
    created       timestamp with time zone not null default now(),
    updated       timestamp with time zone not null default now(),
    actual        boolean                  not null default true,
    total_minutes bigint                   not null default 0,
    caller_id     bigint                   not null,

    constraint pk_expenses_id primary key (id),
    constraint uq_expenses_uuid unique (uuid),
    constraint fk_expenses_caller_id foreign key (caller_id) references callers (id)
);

create index idx_expenses_actual on expenses (actual);
create index idx_expenses_caller_id on expenses (caller_id);

comment on table expenses is 'Список звонков';
comment on column expenses.id is 'Внутренний идентификатор';
comment on column expenses.uuid is 'Внешний идентификатор';
comment on column expenses.created is 'Дата и время создания';
comment on column expenses.updated is 'Дата и время создания';
comment on column expenses.actual is 'Признак актуальности';
comment on column expenses.total_minutes is 'Суммарное количество неоплаченных минут';
comment on column expenses.caller_id is 'Дата и время окончания звонка';