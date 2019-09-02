BEGIN
DECLARE @count smallint
DECLARE @chckLoanConfig smallint
SET @count = (Select count(*) from permission where id = 4 )
SET @chckLoanConfig = (Select count(*) from permission where id = 2 )
if(@chckLoanConfig = 0)

BEGIN
SET IDENTITY_INSERT permission on
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status)
VALUES (2,  'Loan Configuration', 'fas fa-cogs', '/home/admin/config', 30, 1)
SET IDENTITY_INSERT permission off

SET IDENTITY_INSERT role_permission_rights on
INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (2, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  2, 1)

SET IDENTITY_INSERT role_permission_rights off

END

if(@count = 0)
BEGIN

SET IDENTITY_INSERT permission on


INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (4, 'Valuator', 'fas fa-money-check', '/home/admin/valuator', 25, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (5,  'Sector', 'fas fa-hands-helping', '/home/admin/sector', 50, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (7, 'Approval Limit', 'fas fa-indent', '/home/admin/approvalLimit', 2, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (8, 'Nepse Company', 'fas fa-money-bill-wave', '/home/admin/nepse', 21, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (9,  'Segment', 'fa fa-user', '/home/admin/segment', 22, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (10,  'Sub Segment', 'fa fa-user', '/home/admin/sub-segment', 23, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (11, 'Company', 'fas fa-desktop', '/home/admin/company', 24, 1)
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (12,  'SubSector', 'fa fa-user', '/home/admin/subSector', 51, 1)
SET IDENTITY_INSERT permission off


-- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT******************************
SET IDENTITY_INSERT role_permission_rights on
INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (4, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  4, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (5, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  5, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (7, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  7, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (8, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  8, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (9, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  9, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (10, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  10, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (11, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  11, 1)

INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (12, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  12, 1)


SET IDENTITY_INSERT role_permission_rights off


SET IDENTITY_INSERT url_api on
-- ************************APPROVAL LIMIT*******************************************
INSERT  INTO url_api (id,api_url,type) values (5,'/v1/approvallimit','ADD APPROVAL LIMIT')
INSERT  INTO url_api (id,api_url,type) values (6,'/v1/approvallimit/get','VIEW APPROVAL LIMIT')
INSERT  INTO url_api (id,api_url,type) values (34,'/v1/approvallimit/csv','DOWNLOAD CSV')

INSERT  INTO permission_api_list(permission_id, api_list_id) values (7,5)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (7,6)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (7,34)

-- ************************APPROVAL LIMIT*******************************************


-- ************************COMPANY*******************************************
INSERT  INTO url_api (id,api_url,type) values (7,'/v1/company','ADD COMPANY')
INSERT  INTO url_api (id,api_url,type) values (8,'/v1/company/get','VIEW COMPANY')
INSERT  INTO url_api (id,api_url,type) values (9,'/v1/company/get/statusCount','VIEW STATUS')

INSERT  INTO permission_api_list(permission_id, api_list_id) values (11,7)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (11,8)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (11,9)

-- ************************COMPANY*******************************************

-- ************************NEPSE*******************************************
INSERT  INTO url_api (id,api_url,type) values (10,'/v1/nepseCompany','ADD NEPSE')
INSERT  INTO url_api (id,api_url,type) values (11,'/v1/nepseCompany/get','VIEW NEPSE')

INSERT  INTO permission_api_list(permission_id, api_list_id) values (8,10)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (8,11)

-- ************************NEPSE*******************************************


-- ************************SEGMENT*******************************************
INSERT  INTO url_api (id,api_url,type) values (13,'/v1/segment','ADD SEGMENT')
INSERT  INTO url_api (id,api_url,type) values (14,'/v1/segment/get','VIEW SEGMENT')
INSERT  INTO url_api (id,api_url,type) values (15,'/v1/segment','EDIT SEGMENT')
INSERT  INTO url_api (id,api_url,type) values (31,'/v1/segment/csv','DOWNLOAD CSV')


INSERT  INTO permission_api_list(permission_id, api_list_id) values (9,13)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (9,14)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (9,15)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (9,31)

-- ************************SEGMENT*******************************************


-- ************************SUB-SEGMENT*******************************************
INSERT  INTO url_api (id,api_url,type) values (16,'/v1/subSegment','ADD SUB-SEGMENT')
INSERT  INTO url_api (id,api_url,type) values (17,'/v1/subSegment','EDIT SUB-SEGMENT')
INSERT  INTO url_api (id,api_url,type) values (18,'/v1/subSegment/get','VIEW SUB-SEGMENT')
INSERT  INTO url_api (id,api_url,type) values (32,'/v1/subSegment/csv','DOWNLOAD CSV')


INSERT  INTO permission_api_list(permission_id, api_list_id) values (10,16)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (10,17)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (10,18)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (10,32)

-- ************************SUB-SEGMENT*******************************************

-- ************************SECTOR*******************************************
INSERT  INTO url_api (id,api_url,type) values (19,'/v1/sector','ADD SECTOR')
INSERT  INTO url_api (id,api_url,type) values (20,'/v1/sector','EDIT SECTOR')
INSERT  INTO url_api (id,api_url,type) values (21,'/v1/sector/get','VIEW SECTOR')
INSERT  INTO url_api (id,api_url,type) values (33,'/get','DOWNLOAD CSV')


INSERT  INTO permission_api_list(permission_id, api_list_id) values (5,19)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (5,20)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (5,21)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (5,33)



-- ************************SECTOR*******************************************


-- ************************SUB-SECTOR*******************************************
INSERT  INTO url_api (id,api_url,type) values (22,'/v1/subSector','ADD SUB-SECTOR')
INSERT  INTO url_api (id,api_url,type) values (23,'/v1/subSector','EDIT SUB-SECTOR')
INSERT  INTO url_api (id,api_url,type) values (24,'/v1/subSector/get','VIEW SUB-SECTOR')
INSERT  INTO url_api (id,api_url,type) values (25,'/v1/subSector/csv','DOWNLOAD CSV')


INSERT  INTO permission_api_list(permission_id, api_list_id) values (12,22)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (12,23)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (12,24)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (12,25)


-- ************************SUB-SECTOR*******************************************


-- ************************VALUATOR*******************************************
INSERT  INTO url_api (id,api_url,type) values (26,'/v1/valuator','ADD VALUATOR')
INSERT  INTO url_api (id,api_url,type) values (27,'/v1/valuator','EDIT VALUATOR')
INSERT  INTO url_api (id,api_url,type) values (28,'/v1/valuator/get','VIEW VALUATOR')
INSERT  INTO url_api (id,api_url,type) values (29,'/v1/valuator/csv','DOWNLOAD CSV')


INSERT  INTO permission_api_list(permission_id, api_list_id) values (4,26)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (4,27)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (4,28)
INSERT  INTO permission_api_list(permission_id, api_list_id) values (4,29)

SET IDENTITY_INSERT url_api off

END
END;

-- ************************VALUATOR*******************************************

-- ************************DASHBOARD******************************************
BEGIN
DECLARE @chkLoanurlapi smallint
DECLARE @chkNotificationUrlapi smallint
DECLARE @chkPendingurlapi smallint
set @chkLoanurlapi = (select count (*) from url_api where id = 40)
set @chkNotificationUrlapi = (select count (*) from url_api where id = 45)
set @chkPendingurlapi = (select count (*) from url_api where id = 46)

if(@chkLoanurlapi = 0)
BEGIN
SET IDENTITY_INSERT url_api on
INSERT  INTO url_api (id, api_url, type)
values (40, '/v1/config/getAll', 'LOAN CATEGORY')
INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 40)
SET IDENTITY_INSERT url_api off


END


if(@chkNotificationUrlapi = 0)
BEGIN
SET IDENTITY_INSERT url_api on

-- INSERT  INTO url_api (id, api_url, type)
-- values (43, '/v1/sector/get/statusCount', 'SECTOR COUNT')
-- INSERT  INTO url_api (id, api_url, type)
-- values (44, '/v1/segment/get/statusCount', 'SEGMENT COUNT')
-- INSERT  INTO url_api (id, api_url, type)
-- values (45,'/v1/notification', 'NOTIFICATION')

-- INSERT  INTO permission_api_list(permission_id, api_list_id)
-- values (17, 45)
INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 41)
INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 42)
-- INSERT  INTO permission_api_list(permission_id, api_list_id)
-- values (17, 43)
-- INSERT  INTO permission_api_list(permission_id, api_list_id)
-- values (17, 44)
SET IDENTITY_INSERT url_api off
END




if(@chkPendingurlapi = 0)
BEGIN
SET IDENTITY_INSERT url_api on

INSERT  INTO url_api(id, api_url, type)
values (46,'/v1/pending','PENDING')

INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 46)
SET IDENTITY_INSERT url_api off

END



END ;



-- ************************DASHBOARD******************************************



-- ************************DEFAULT ADMIN*******************************************

BEGIN
DECLARE @ChkAPiMap smallint
SET @ChkAPiMap = (select count(*) from role_permission_rights_api_rights where role_permission_rights_id=7)

if(@ChkAPiMap = 0)
BEGIN
-- ************************APPROVAL LIMIT MAP*******************************************
INSERT  INTO role_permission_rights_api_rights values (7,5)
                                                      INSERT  INTO role_permission_rights_api_rights values (7,6)
INSERT  INTO role_permission_rights_api_rights values (7,34)

                                                       -- ************************APPROVAL LIMIT MAP*******************************************


                                                       -- ************************COMPANY MAP*******************************************
INSERT  INTO role_permission_rights_api_rights values (11,7)
INSERT  INTO role_permission_rights_api_rights values (11,8)
INSERT  INTO role_permission_rights_api_rights values (11,9)

                                                       -- ************************COMPANY MAP*******************************************

                                                       -- ************************NEPSE MAP*******************************************
INSERT  INTO role_permission_rights_api_rights values (8,10)
INSERT  INTO role_permission_rights_api_rights values (8,11)

                                                       -- ************************NEPSE MAP*******************************************


                                                       -- ************************SEGMENT MAP*******************************************
INSERT  INTO role_permission_rights_api_rights values (9,13)
INSERT  INTO role_permission_rights_api_rights values (9,14)
INSERT  INTO role_permission_rights_api_rights values (9,15)
INSERT  INTO role_permission_rights_api_rights values (9,31)

                                                       -- ************************SEGMENT MAP*******************************************

                                                       -- ************************SUB-SEGMENT*******************************************
INSERT  INTO role_permission_rights_api_rights values (10,16)
INSERT  INTO role_permission_rights_api_rights values (10,17)
INSERT  INTO role_permission_rights_api_rights values (10,18)
INSERT  INTO role_permission_rights_api_rights values (10,32)

                                                       -- ************************SUB-SEGMENT*******************************************

                                                       -- ************************SECTOR*******************************************

INSERT  INTO role_permission_rights_api_rights values (5,19)
INSERT  INTO role_permission_rights_api_rights values (5,20)
INSERT  INTO role_permission_rights_api_rights values (5,21)
INSERT  INTO role_permission_rights_api_rights values (5,33)



                                                       -- ************************SECTOR*******************************************

                                                       -- ************************SUB-SECTOR*******************************************

INSERT  INTO role_permission_rights_api_rights values (12,22)
INSERT  INTO role_permission_rights_api_rights values (12,23)
INSERT  INTO role_permission_rights_api_rights values (12,24)
INSERT  INTO role_permission_rights_api_rights values (12,25)


                                                       -- ************************SUB-SECTOR*******************************************


                                                       -- ************************VALUATOR*******************************************

INSERT  INTO role_permission_rights_api_rights values (4,26)
INSERT  INTO role_permission_rights_api_rights values (4,27)
INSERT  INTO role_permission_rights_api_rights values (4,28)
INSERT  INTO role_permission_rights_api_rights values (4,29)

-- ************************VALUATOR*******************************************


-- ************************COUNTVIEW*******************************************


-- ************************COUNTVIEW*******************************************

-- ************************DEFAULT ADMIN*******************************************
END
END ;











