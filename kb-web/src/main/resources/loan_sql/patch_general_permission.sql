

INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (1,  'Branch', 'fas fa-university', '/home/admin/branch', 10, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (3,  'Role and Permission', 'fas fa-lock', '/home/admin/role', 19, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (6, 'Users', 'fa fa-user', '/home/admin/user', 2, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (13,  'Document', 'fa fa-file', '/home/admin/document', 55, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (100,  'Role Hierarchy', 'fas fa-sitemap', '/home/admin/roleHierarchy', 20, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (17,  'Dashboard', 'fas fa-home', '/home/admin/dashboard', 1, 1);
-- INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (105,  'Email Configuration', 'fas fa-cogs', '/home/admin/email-config', 55, 1);




-- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT****************************** --
-- BRANCH --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (1, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  1, 1);



-- Role And Permission --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (3, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  3, 1);


-- Users --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (6, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  6, 1);

-- Document --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (13, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  13, 1);

-- Role Hierarchy --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (100, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  100, 1);


-- Dashboard --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (17, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  17, 1);

 -- Email Config --
--  INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
--  VALUES (55, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  105, 1);

 -- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT****************************** --

 -- ************************BRANCH*******************************************
INSERT IGNORE INTO url_api (id,api_url,type) values (1,'/v1/branch','ADD BRANCH');
INSERT IGNORE INTO url_api (id,api_url,type) values (2,'/v1/branch','EDIT BRANCH');
INSERT IGNORE INTO url_api (id,api_url,type) values (3,'/v1/branch/csv','DOWNLOAD CSV');
INSERT IGNORE INTO url_api (id,api_url,type) values (4,'/v1/branch/get','VIEW BRANCH');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (1,1);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (1,2);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (1,3);
INSERT IGNORE INTO  permission_api_list(permission_id, api_list_id) values (1,4);


-- ************************BRANCH*******************************************



-- ************************USER*******************************************
INSERT IGNORE INTO url_api (id,api_url,type) values (12,'/v1/user','ADD USER');
INSERT IGNORE INTO url_api (id,api_url,type) values (30,'/v1/user/csv','DOWNLOAD CSV');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (6,12);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (6,30);


-- ************************USER*******************************************



-- ************************DASHBOARD******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (41, '/v1/user/get/statusCount', 'USER COUNT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (42, '/v1/branch/get/statusCount', 'BRANCH COUNT');

INSERT IGNORE INTO url_api (id, api_url, type)
values (45,'/v1/notification', 'NOTIFICATION');




INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 41);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 42);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 45);

-- ************************DASHBOARD******************************************



-- ************************DEFAULT ADMIN*******************************************

-- ************************BRANCH MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights values (1,1);
INSERT IGNORE INTO role_permission_rights_api_rights values (1,2);
INSERT IGNORE INTO role_permission_rights_api_rights values (1,3);
INSERT IGNORE INTO role_permission_rights_api_rights values (1,4);
-- ************************BRANCH MAP*******************************************

-- ************************COUNTVIEW*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 41);
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 42);

INSERT IGNORE INTO role_permission_rights_api_rights values (17, 45);

-- ************************COUNTVIEW*******************************************

-- ************************DEFAULT ADMIN*******************************************
















