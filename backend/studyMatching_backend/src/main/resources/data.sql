insert into role_hierarchy (role_hierarchy_id, role_name, parent_id) values (1,'ROLE_ADMIN',null);
insert into role_hierarchy (role_hierarchy_id, role_name, parent_id) values (2,'ROLE_MANAGER','1');
insert into role_hierarchy (role_hierarchy_id, role_name, parent_id) values (3,'ROLE_DBA','1');
insert into role_hierarchy (role_hierarchy_id, role_name, parent_id) values (4,'ROLE_USER','2');
insert into role_hierarchy (role_hierarchy_id, role_name, parent_id) values (5,'ROLE_USER','3');

insert into tag (tag_id, title) value (1, 'Java');
insert into tag (tag_id, title) value (2, 'Spring');
insert into tag (tag_id, title) value (3, 'JavaScript' );
insert into tag (tag_id, title) value (4, 'CSS');
insert into tag (tag_id, title) value (5, 'Vue.js');
