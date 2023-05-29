CREATE TABLE public.t_sub
(
    id              bigserial   not null,
    security_market int         null,
    security_code   varchar(20) null,
    sub_type        varchar(50) null,
    used_quota      int         null,
    is_own_conn     smallint    null
);

create unique index table_sub_id_unique on public.t_sub (id);
alter table public.t_sub
    add constraint table_sub_pk primary key (id);
