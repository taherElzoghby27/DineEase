DELETE FROM hr.ROLE r WHERE r.role in ('USER', 'ADMIN');
insert into hr.ROLE r (r.role) values ('ADMIN');
insert into hr.ROLE r (r.role) values ('USER');