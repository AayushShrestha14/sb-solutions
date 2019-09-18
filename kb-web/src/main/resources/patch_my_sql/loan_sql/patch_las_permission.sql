INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (1, 'Branch', 'camera-outline', '/home/admin/branch', 10, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (2, 'Loan Configuration', 'smartphone-outline', '/home/admin/config', 30, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (3, 'Role and Permission', 'shield-outline', '/home/admin/role', 19, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (4, 'Valuator', 'plus-circle-outline', '/home/admin/valuator', 25, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (5, 'Sector', 'plus-circle-outline', '/home/admin/sector', 50, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (6, 'Users', 'person-add-outline', '/home/admin/user', 1, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (7, 'Approval Limit', 'plus-circle-outline', '/home/admin/approvalLimit', 2, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (8, 'Nepse Company', 'plus-circle-outline', '/home/admin/nepse', 21, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (9, 'Segment', 'plus-circle-outline', '/home/admin/segment', 22, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (10, 'Sub Segment', 'plus-circle-outline', '/home/admin/sub-segment', 23, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (11, 'Company', 'map-outline', '/home/admin/company', 24, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (12, 'SubSector', 'plus-circle-outline', '/home/admin/subSector', 51, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (13, 'Document', 'book-outline', '/home/admin/document', 55, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (100, 'Role Hierarchy', 'shield-outline', '/home/admin/roleHierarchy', 20, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (17, 'Dashboard', 'home-outline', '/home/admin/dashboard', 1, 1);
INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (102, 'Catalogue', 'layers-outline', '/home/admin/catalogue', 60, 1);


-- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT******************************
INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (1, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 1, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (2, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 2, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (3, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 3, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (4, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 4, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (5, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 5, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (6, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 6, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (7, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 7, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (8, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 8, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (9, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 9, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (10, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 10, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (11, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 11, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (12, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 12, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (13, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 13, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (100, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 100, 1);


INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (17, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 17, 1);

-- Catalogue --
INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (102, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 102, 1);

-- ************************BRANCH*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (1, '/v1/branch', 'ADD BRANCH');
INSERT IGNORE INTO url_api (id, api_url, type)
values (2, '/v1/branch', 'EDIT BRANCH');
INSERT IGNORE INTO url_api (id, api_url, type)
values (3, '/v1/branch/csv', 'DOWNLOAD CSV');
INSERT IGNORE INTO url_api (id, api_url, type)
values (4, '/v1/branch/get', 'VIEW BRANCH');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (1, 1);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (1, 2);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (1, 3);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (1, 4);


-- ************************BRANCH*******************************************

-- ************************APPROVAL LIMIT*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (5, '/v1/approvallimit', 'ADD APPROVAL LIMIT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (6, '/v1/approvallimit/get', 'VIEW APPROVAL LIMIT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (34, '/v1/approvallimit/csv', 'DOWNLOAD CSV');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (7, 5);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (7, 6);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (7, 34);

-- ************************APPROVAL LIMIT*******************************************


-- ************************COMPANY*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (7, '/v1/company', 'ADD COMPANY');
INSERT IGNORE INTO url_api (id, api_url, type)
values (8, '/v1/company/get', 'VIEW COMPANY');
INSERT IGNORE INTO url_api (id, api_url, type)
values (9, '/v1/company/get/statusCount', 'VIEW STATUS');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (11, 7);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (11, 8);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (11, 9);

-- ************************COMPANY*******************************************

-- ************************NEPSE*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (10, '/v1/nepseCompany', 'ADD NEPSE');
INSERT IGNORE INTO url_api (id, api_url, type)
values (11, '/v1/nepseCompany/get', 'VIEW NEPSE');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (8, 10);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (8, 11);

-- ************************NEPSE*******************************************


-- ************************USER*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (12, '/v1/user', 'ADD USER');
INSERT IGNORE INTO url_api (id, api_url, type)
values (30, '/v1/user/csv', 'DOWNLOAD CSV');

INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (6, 12);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (6, 30);


-- ************************USER*******************************************

-- ************************SEGMENT*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (13, '/v1/segment', 'ADD SEGMENT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (14, '/v1/segment/get', 'VIEW SEGMENT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (15, '/v1/segment', 'EDIT SEGMENT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (31, '/v1/segment/csv', 'DOWNLOAD CSV');


INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (9, 13);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (9, 14);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (9, 15);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (9, 31);

-- ************************SEGMENT*******************************************


-- ************************SUB-SEGMENT*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (16, '/v1/subSegment', 'ADD SUB-SEGMENT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (17, '/v1/subSegment', 'EDIT SUB-SEGMENT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (18, '/v1/subSegment/get', 'VIEW SUB-SEGMENT');
INSERT IGNORE INTO url_api (id, api_url, type)
values (32, '/v1/subSegment/csv', 'DOWNLOAD CSV');


INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (10, 16);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (10, 17);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (10, 18);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (10, 32);

-- ************************SUB-SEGMENT*******************************************

-- ************************SECTOR*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (19, '/v1/sector', 'ADD SECTOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (20, '/v1/sector', 'EDIT SECTOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (21, '/v1/sector/get', 'VIEW SECTOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (33, '/get', 'DOWNLOAD CSV');


INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (5, 19);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (5, 20);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (5, 21);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (5, 33);



-- ************************SECTOR*******************************************


-- ************************SUB-SECTOR*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (22, '/v1/subSector', 'ADD SUB-SECTOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (23, '/v1/subSector', 'EDIT SUB-SECTOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (24, '/v1/subSector/get', 'VIEW SUB-SECTOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (25, '/v1/subSector/csv', 'DOWNLOAD CSV');


INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (12, 22);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (12, 23);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (12, 24);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (12, 25);


-- ************************SUB-SECTOR*******************************************


-- ************************VALUATOR*******************************************
INSERT IGNORE INTO url_api (id, api_url, type)
values (26, '/v1/valuator', 'ADD VALUATOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (27, '/v1/valuator', 'EDIT VALUATOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (28, '/v1/valuator/get', 'VIEW VALUATOR');
INSERT IGNORE INTO url_api (id, api_url, type)
values (29, '/v1/valuator/csv', 'DOWNLOAD CSV');


INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (4, 26);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (4, 27);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (4, 28);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (4, 29);



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
values (45, '/v1/notification', 'NOTIFICATION');
INSERT IGNORE INTO url_api(id, api_url, type)
values (46, '/v1/pending', 'PENDING');



INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 40);
-- INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
-- values (17, 41);
-- INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
-- values (17, 42);
-- INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
-- values (17, 43);
-- INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
-- values (17, 44);
-- INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
-- values (17, 45);
INSERT IGNORE INTO permission_api_list(permission_id, api_list_id)
values (17, 46);

-- ************************DASHBOARD******************************************


-- ************************DEFAULT ADMIN*******************************************

-- ************************BRANCH MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (1, 1);
INSERT IGNORE INTO role_permission_rights_api_rights
values (1, 2);
INSERT IGNORE INTO role_permission_rights_api_rights
values (1, 3);
INSERT IGNORE INTO role_permission_rights_api_rights
values (1, 4);
-- ************************BRANCH MAP*******************************************

-- ************************APPROVAL LIMIT MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (7, 5);
INSERT IGNORE INTO role_permission_rights_api_rights
values (7, 6);
INSERT IGNORE INTO role_permission_rights_api_rights
values (7, 34);

-- ************************APPROVAL LIMIT MAP*******************************************


-- ************************COMPANY MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (11, 7);
INSERT IGNORE INTO role_permission_rights_api_rights
values (11, 8);
INSERT IGNORE INTO role_permission_rights_api_rights
values (11, 9);

-- ************************COMPANY MAP*******************************************

-- ************************NEPSE MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (8, 10);
INSERT IGNORE INTO role_permission_rights_api_rights
values (8, 11);

-- ************************NEPSE MAP*******************************************


-- ************************SEGMENT MAP*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (9, 13);
INSERT IGNORE INTO role_permission_rights_api_rights
values (9, 14);
INSERT IGNORE INTO role_permission_rights_api_rights
values (9, 15);
INSERT IGNORE INTO role_permission_rights_api_rights
values (9, 31);

-- ************************SEGMENT MAP*******************************************

-- ************************SUB-SEGMENT*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (10, 16);
INSERT IGNORE INTO role_permission_rights_api_rights
values (10, 17);
INSERT IGNORE INTO role_permission_rights_api_rights
values (10, 18);
INSERT IGNORE INTO role_permission_rights_api_rights
values (10, 32);

-- ************************SUB-SEGMENT*******************************************

-- ************************SECTOR*******************************************

INSERT IGNORE INTO role_permission_rights_api_rights
values (5, 19);
INSERT IGNORE INTO role_permission_rights_api_rights
values (5, 20);
INSERT IGNORE INTO role_permission_rights_api_rights
values (5, 21);
INSERT IGNORE INTO role_permission_rights_api_rights
values (5, 33);



-- ************************SECTOR*******************************************

-- ************************SUB-SECTOR*******************************************

INSERT IGNORE INTO role_permission_rights_api_rights
values (12, 22);
INSERT IGNORE INTO role_permission_rights_api_rights
values (12, 23);
INSERT IGNORE INTO role_permission_rights_api_rights
values (12, 24);
INSERT IGNORE INTO role_permission_rights_api_rights
values (12, 25);


-- ************************SUB-SECTOR*******************************************


-- ************************VALUATOR*******************************************

INSERT IGNORE INTO role_permission_rights_api_rights
values (4, 26);
INSERT IGNORE INTO role_permission_rights_api_rights
values (4, 27);
INSERT IGNORE INTO role_permission_rights_api_rights
values (4, 28);
INSERT IGNORE INTO role_permission_rights_api_rights
values (4, 29);

-- ************************VALUATOR*******************************************


-- ************************COUNTVIEW*******************************************
INSERT IGNORE INTO role_permission_rights_api_rights
values (17, 41);
INSERT IGNORE INTO role_permission_rights_api_rights
values (17, 42);
INSERT IGNORE INTO role_permission_rights_api_rights
values (17, 43);
INSERT IGNORE INTO role_permission_rights_api_rights
values (17, 44);
INSERT IGNORE INTO role_permission_rights_api_rights
values (17, 45);

-- ************************COUNTVIEW*******************************************

-- ************************DEFAULT ADMIN*******************************************












