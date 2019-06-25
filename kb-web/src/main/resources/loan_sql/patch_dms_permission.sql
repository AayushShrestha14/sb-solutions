

INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (1,  'Branch', 'fa fa-bank', '/home/admin/branch', 10, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (2,  'Loan Configuration', 'fa fa-gear', '/home/admin/config', 30, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (3,  'Role and Permission', 'fa fa-exchange', '/home/admin/role', 19, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (4, 'Valuator', 'fas fa-money-check', '/home/admin/valuator', 25, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (6, 'Users', 'fa fa-user', '/home/admin/user', 2, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (8, 'Nepse Company', 'fas fa-money-bill-wave', '/home/admin/nepse', 21, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (11, 'Company', 'fas fa-desktop', '/home/admin/company', 24, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (13,  'Document', 'fa fa-file', '/home/admin/document', 55, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (100,  'Role Hierarchy', 'fas fa-sitemap', '/home/admin/roleHierarchy', 20, 1);
INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (17,  'Dashboard', 'fa fa-dashboard', '/home/admin/dashboard', 1, 1);




-- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT****************************** --
-- BRANCH --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (1, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  1, 1);

-- Loan Configuration --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (2, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  2, 1);

-- Role And Permission --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (3, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  3, 1);

-- valuator --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (4, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  4, 1);

-- Users --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (6, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  6, 1);

-- Nepse Company --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (8, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  8, 1);

 -- Company --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (11, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  11, 1);

-- Document --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (13, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  13, 1);

-- Role Hierarchy --
INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (100, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  100, 1);


-- Dashboard --
 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (17, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  17, 1);

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



-- ************************COMPANY*******************************************
INSERT IGNORE INTO url_api (id,api_url,type) values (7,'/v1/company','ADD COMPANY');
INSERT IGNORE INTO url_api (id,api_url,type) values (8,'/v1/company/get','VIEW COMPANY');
INSERT IGNORE INTO url_api (id,api_url,type) values (9,'/v1/company/get/statusCount','VIEW STATUS');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (11,7);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (11,8);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (11,9);

-- ************************COMPANY*******************************************

-- ************************NEPSE*******************************************
INSERT IGNORE INTO url_api (id,api_url,type) values (10,'/v1/nepseCompany','ADD NEPSE');
INSERT IGNORE INTO url_api (id,api_url,type) values (11,'/v1/nepseCompany/get','VIEW NEPSE');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (8,10);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (8,11);

-- ************************NEPSE*******************************************


-- ************************USER*******************************************
INSERT IGNORE INTO url_api (id,api_url,type) values (12,'/v1/user','ADD USER');
INSERT IGNORE INTO url_api (id,api_url,type) values (30,'/v1/user/csv','DOWNLOAD CSV');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (6,12);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (6,30);


-- ************************USER*******************************************



-- ************************VALUATOR*******************************************
INSERT IGNORE INTO url_api (id,api_url,type) values (26,'/v1/valuator','ADD VALUATOR');
INSERT IGNORE INTO url_api (id,api_url,type) values (27,'/v1/valuator','EDIT VALUATOR');
INSERT IGNORE INTO url_api (id,api_url,type) values (28,'/v1/valuator/get','VIEW VALUATOR');
INSERT IGNORE INTO url_api (id,api_url,type) values (29,'/v1/valuator/csv','DOWNLOAD CSV');


INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (4,26);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (4,27);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (4,28);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id) values (4,29);



-- ************************VALUATOR*******************************************

-- ************************DASHBOARD******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (40, '/v1/config/getAll', 'LOAN CATEGORY');
INSERT IGNORE INTO url_api (id, api_url, type)
values (41, '/v1/user/get/statusCount', 'USER COUNT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (42, '/v1/branch/get/statusCount', 'BRANCH COUNT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (43, '/v1/sector/get/statusCount', 'SECTOR COUNT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (44, '/v1/segment/get/statusCount', 'SEGMENT COUNT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (45,'/v1/notification', 'NOTIFICATION');
INSERT IGNORE INTO url_api(id, api_url, type)
values (46,'/v1/pending','PENDING');



INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 40);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 41);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 42);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 43);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 44);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 45);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 46);

-- ************************DASHBOARD******************************************



-- ************************DEFAULT ADMIN*******************************************

-- ************************BRANCH MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights values (1,1);
INSERT IGNORE INTO role_permission_rights_api_rights values (1,2);
INSERT IGNORE INTO role_permission_rights_api_rights values (1,3);
INSERT IGNORE INTO role_permission_rights_api_rights values (1,4);
-- ************************BRANCH MAP*******************************************



-- ************************COMPANY MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights values (11,7);
INSERT IGNORE INTO role_permission_rights_api_rights values (11,8);
INSERT IGNORE INTO role_permission_rights_api_rights values (11,9);

-- ************************COMPANY MAP*******************************************

-- ************************NEPSE MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights values (8,10);
INSERT IGNORE INTO role_permission_rights_api_rights values (8,11);

-- ************************NEPSE MAP*******************************************

-- ************************VALUATOR*******************************************

INSERT IGNORE INTO role_permission_rights_api_rights values (4,26);
INSERT IGNORE INTO role_permission_rights_api_rights values (4,27);
INSERT IGNORE INTO role_permission_rights_api_rights values (4,28);
INSERT IGNORE INTO role_permission_rights_api_rights values (4,29);

-- ************************VALUATOR*******************************************


-- ************************COUNTVIEW*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 41);
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 42);
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 43);
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 44);
INSERT IGNORE INTO role_permission_rights_api_rights values (17, 45);

-- ************************COUNTVIEW*******************************************

-- ************************DEFAULT ADMIN*******************************************
















