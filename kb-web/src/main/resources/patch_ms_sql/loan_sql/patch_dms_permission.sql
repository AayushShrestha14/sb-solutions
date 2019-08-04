
BEGIN
DECLARE @countPermission smallint
SET @countPermission = (Select count(*) from permission where id = 2)
DELETE FROM permission_api_list where permission_id in (4,8,11)
DELETE FROM role_permission_rights WHERE permission_id in (4,8,11)
DELETE FROM permission WHERE id in (4,8,11)
if(@countPermission = 0)
BEGIN
SET IDENTITY_INSERT permission ON
INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES
(2,  'Loan Configuration', 'fas fa-cogs', '/home/admin/config', 30, 1)
SET IDENTITY_INSERT permission OFF

-- **********************MAP PERMISSION TO ADMIN ROLE DEFAULT****************************** --

-- Loan Configuration --
SET IDENTITY_INSERT role_permission_rights ON
 INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (2, '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  2, 1)

 SET IDENTITY_INSERT role_permission_rights OFF
 END
 END;



BEGIN
DECLARE @countLoan smallint
DECLARE @countPending smallint
DECLARE @checkPermPending smallint
DECLARE @checkPermLoan smallint
SET @countloan = (Select count(*) from url_api where id = 40)
SET @checkPermLoan = (Select count(*) from permission_api_list where api_list_id = 40)
SET @checkPermPending = (Select count(*) from permission_api_list where api_list_id = 46)

SET @countPending = (Select count(*) from url_api where id = 46)
if(@countloan = 0)
BEGIN
SET IDENTITY_INSERT url_api ON
 INSERT  INTO url_api (id, api_url, type)
values (40, '/v1/config/getAll', 'LOAN CATEGORY')
SET IDENTITY_INSERT url_api off
END

if(@checkPermLoan = 0)
BEGIN

INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 40)

END

if(@checkPermPending = 0)
BEGIN
SET IDENTITY_INSERT url_api ON
 INSERT  INTO url_api (id, api_url, type)
values (46,'/v1/pending','PENDING')


 SET IDENTITY_INSERT url_api OFF

END
if(@checkPermPending = 0)
BEGIN

INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 46)

END
END;




















