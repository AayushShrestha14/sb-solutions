INSERT INTO role (id, created_at,  last_modified_at,  role_name, status) VALUES (1, '2019-04-04 12:52:44', '2019-04-04 12:53:13', 'admin', 1);

INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (1,  'BRANCH', 'fa fa-dashboard', '/home/branch', 10, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (2,  'Loan Configuration', 'fa fa-edit', '/home/config', 30, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (3,  'Role and Permission', 'fa fa-edit', '/home/role', 20, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (4, 'VALUATOR', 'fa fa-airplane', '/home/valuator', 25, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (5,  'Sector', 'fa fa-airplane', '/home/sector', 50, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (6, 'Users', 'fa fa-user', '/home/user', 1, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (7, 'Approval Limit', 'fa fa-user', '/home/approvalLimit', 2, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (8, 'Nepse Company', 'fa fa-user', '/home/nepse', 21, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (9,  'Segment', 'fa fa-user', '/home/segment', 22, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (10,  'Sub Segment', 'fa fa-user', '/home/sub-segment', 23, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (11, 'Company', 'fa fa-user', '/home/company', 24, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (12,  'SubSector', 'fa fa-user', '/home/subSector', 51, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (13,  'Document', 'fa fa-user', '/home/document', 55, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (14,  'Ui', 'fa fa-user', '/home/ui', 60, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (15,  'Template', 'fa fa-user', '/home/template', 61, 1);
INSERT INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (16,  'Loan', 'fa fa-user', '/home/loan', 62, 1);



-- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT******************************
INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (1, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  1, 1);

 INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (2, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  2, 1);

 INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (3, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  3, 1);

 INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (4, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  4, 1);

 INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (5, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  5, 1);

 INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (6, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  6, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (7, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  7, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (8, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  8, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (9, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  9, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (10, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  10, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (11, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  11, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (12, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  12, 1);

  INSERT INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (13, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  13, 1);




