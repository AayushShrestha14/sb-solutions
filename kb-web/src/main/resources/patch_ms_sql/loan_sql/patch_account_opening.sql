BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from permission where id = 19)
if(@count = 0)
BEGIN
SET IDENTITY_INSERT permission on

INSERT  INTO permission (id,  permission_name, fa_icon, front_url, orders, status)
VALUES (19,  'Account Opening', 'fa fa-money-check', '/home/admin/openingAccount', 56, 1)
SET IDENTITY_INSERT permission off

SET IDENTITY_INSERT role_permission_rights on
INSERT  INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
VALUES (19 , '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  19, 1)
SET IDENTITY_INSERT role_permission_rights off

END
END;