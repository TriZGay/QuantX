alter sequence t_menu_id_seq restart with 1;
insert into public.t_menu (id, name, parent_id, visible, create_time, modify_time)
values (1, '系统管理', -1, 1, now(), now());
insert into public.t_menu (id,name, parent_id, visible, create_time, modify_time)
values (2,'菜单管理', 1, 1, now(), now());
insert into public.t_menu (id,name, parent_id, visible, create_time, modify_time)
values (3,'角色管理', 1, 1, now(), now());