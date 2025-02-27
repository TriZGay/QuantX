CREATE TABLE public.t_acc_sub
(
    id           bigserial not null,
    acc_id       varchar   null,
    card_num     varchar   null,
    uni_card_num varchar   null
);

create unique index table_acc_sub_id_unique on public.t_acc_sub (id);
alter table public.t_acc_sub
    add constraint table_acc_sub_pk primary key (id);
