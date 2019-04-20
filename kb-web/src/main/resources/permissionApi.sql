

-- ************************BRANCH*******************************************
INSERT INTO url_api (id,api_url) values (1,'/v1/branch');
INSERT INTO url_api (id,api_url) values (2,'/v1/branch/get');
INSERT INTO url_api (id,api_url) values (3,'/v1/branch/csv');
INSERT INTO url_api (id,api_url) values (4,'/v1/branch/get/statusCount');
INSERT INTO url_api (id,api_url) values (5,'/v1/branch/getList');

INSERT INTO permission_api_list(permission_id, api_list_id) values (1,1);
INSERT INTO permission_api_list(permission_id, api_list_id) values (1,2);
INSERT INTO permission_api_list(permission_id, api_list_id) values (1,3);
INSERT INTO permission_api_list(permission_id, api_list_id) values (1,4);
INSERT INTO permission_api_list(permission_id, api_list_id) values (1,5);

-- ************************BRANCH*******************************************

-- ************************APPROVAL LIMIT*******************************************
INSERT INTO url_api (id,api_url) values (6,'/v1/approvallimit');
INSERT INTO url_api (id,api_url) values (7,'/v1/approvallimit/get');

INSERT INTO permission_api_list(permission_id, api_list_id) values (7,6);
INSERT INTO permission_api_list(permission_id, api_list_id) values (7,7);

-- ************************APPROVAL LIMIT*******************************************


-- ************************COMPANY*******************************************
INSERT INTO url_api (id,api_url) values (8,'/v1/company');
INSERT INTO url_api (id,api_url) values (9,'/v1/company/get');
INSERT INTO url_api (id,api_url) values (14,'/v1/company/get/statusCount');

INSERT INTO permission_api_list(permission_id, api_list_id) values (11,8);
INSERT INTO permission_api_list(permission_id, api_list_id) values (11,9);
INSERT INTO permission_api_list(permission_id, api_list_id) values (11,14);

-- ************************COMPANY*******************************************

-- ************************NEPSE*******************************************
INSERT INTO url_api (id,api_url) values (10,'/v1/nepseCompany');
INSERT INTO url_api (id,api_url) values (11,'/v1/nepseCompany/get');
INSERT INTO url_api (id,api_url) values (12,'/v1/nepseCompany/bulk');
INSERT INTO url_api (id,api_url) values (13,'/v1/nepseCompany/get/statusCount');

INSERT INTO permission_api_list(permission_id, api_list_id) values (8,10);
INSERT INTO permission_api_list(permission_id, api_list_id) values (8,11);
INSERT INTO permission_api_list(permission_id, api_list_id) values (8,12);
INSERT INTO permission_api_list(permission_id, api_list_id) values (8,13);

-- ************************NEPSE*******************************************


-- ************************USER*******************************************
INSERT INTO url_api (id,api_url) values (15,'/v1/user');
INSERT INTO url_api (id,api_url) values (16,'/v1/user/get');
INSERT INTO url_api (id,api_url) values (17,'v1/user/authenticated');
INSERT INTO url_api (id,api_url) values (18,'/v1/user/csv');
INSERT INTO url_api (id,api_url) values (19,'/v1/user/uploadFile');
INSERT INTO url_api (id,api_url) values (20,'/v1/user/checkFingerPrint');
INSERT INTO url_api (id,api_url) values (21,'/v1/user/get/statusCount');
INSERT INTO url_api (id,api_url) values (22,'/v1/user/listByRole');
INSERT INTO url_api (id,api_url) values (23,'/v1/user/listRole');


INSERT INTO permission_api_list(permission_id, api_list_id) values (6,15);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,16);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,17);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,18);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,19);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,20);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,21);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,22);
INSERT INTO permission_api_list(permission_id, api_list_id) values (6,23);

-- ************************USER*******************************************

-- ************************SEGMENT*******************************************
INSERT INTO url_api (id,api_url) values (24,'/v1/segment');
INSERT INTO url_api (id,api_url) values (25,'/v1/segment/get');
INSERT INTO url_api (id,api_url) values (26,'/v1/segment/getList');
INSERT INTO url_api (id,api_url) values (27,'/v1/segment/get/statusCount');

INSERT INTO permission_api_list(permission_id, api_list_id) values (9,24);
INSERT INTO permission_api_list(permission_id, api_list_id) values (9,25);
INSERT INTO permission_api_list(permission_id, api_list_id) values (9,26);
INSERT INTO permission_api_list(permission_id, api_list_id) values (9,27);

-- ************************SEGMENT*******************************************


-- ************************SUB-SEGMENT*******************************************
INSERT INTO url_api (id,api_url) values (28,'/v1/subSegment');
INSERT INTO url_api (id,api_url) values (29,'v1/subSegment/get');
INSERT INTO url_api (id,api_url) values (30,'/v1/subSegment/get/statusCount');

INSERT INTO permission_api_list(permission_id, api_list_id) values (10,28);
INSERT INTO permission_api_list(permission_id, api_list_id) values (10,29);
INSERT INTO permission_api_list(permission_id, api_list_id) values (10,30);

-- ************************SUB-SEGMENT*******************************************

-- ************************SECTOR*******************************************
INSERT INTO url_api (id,api_url) values (31,'/v1/sector');
INSERT INTO url_api (id,api_url) values (32,'/v1/sector/get');
INSERT INTO url_api (id,api_url) values (33,'/v1/sector/get/statusCount');
INSERT INTO url_api (id,api_url) values (34,'/v1/sector/getList');

INSERT INTO permission_api_list(permission_id, api_list_id) values (5,31);
INSERT INTO permission_api_list(permission_id, api_list_id) values (5,32);
INSERT INTO permission_api_list(permission_id, api_list_id) values (5,33);
INSERT INTO permission_api_list(permission_id, api_list_id) values (5,34);

-- ************************SECTOR*******************************************


-- ************************SUB-SECTOR*******************************************
INSERT INTO url_api (id,api_url) values (35,'/v1/subSector');
INSERT INTO url_api (id,api_url) values (36,'/v1/subSector/get');
INSERT INTO url_api (id,api_url) values (37,'/v1/subSector/get/statusCount');


INSERT INTO permission_api_list(permission_id, api_list_id) values (12,35);
INSERT INTO permission_api_list(permission_id, api_list_id) values (12,36);
INSERT INTO permission_api_list(permission_id, api_list_id) values (12,37);


-- ************************SUB-SECTOR*******************************************


-- ************************VALUATOR*******************************************
INSERT INTO url_api (id,api_url) values (38,'/v1/valuator');
INSERT INTO url_api (id,api_url) values (39,'/v1/valuator/get');
INSERT INTO url_api (id,api_url) values (40,'/v1/valuator/get/statusCount');


INSERT INTO permission_api_list(permission_id, api_list_id) values (4,38);
INSERT INTO permission_api_list(permission_id, api_list_id) values (4,39);
INSERT INTO permission_api_list(permission_id, api_list_id) values (4,40);


-- ************************VALUATOR*******************************************


