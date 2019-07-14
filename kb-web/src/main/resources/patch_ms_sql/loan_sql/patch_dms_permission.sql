BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from permission where id = 2)
DELETE FROM permission_api_list where permission_id in (4,8,11)
DELETE FROM role_permission_rights WHERE permission_id in (4,8,11)
DELETE FROM permission WHERE id in (4,8,11)
if(@count = 0)
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
DECLARE @count smallint
SET @count = (Select count(*) from url_api where id = 40)
if(@count = 0)
BEGIN
SET IDENTITY_INSERT url_api ON
 INSERT  INTO url_api (id, api_url, type)
values (40, '/v1/config/getAll', 'LOAN CATEGORY'),

 (46,'/v1/pending','PENDING')

INSERT  INTO permission_api_list(permission_id, api_list_id)
values (17, 40),
 (17, 46)

 SET IDENTITY_INSERT url_api OFF

END
END;

















