DELETE FROM hr.ROLE r WHERE r.CODE in ('USER', 'ADMIN');
insert into hr.ROLE r (r.CODE) values ('ADMIN');
insert into hr.ROLE r (r.CODE) values ('USER');