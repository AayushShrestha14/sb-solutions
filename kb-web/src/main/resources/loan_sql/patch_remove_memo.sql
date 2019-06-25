

DELETE FROM role_permission_rights WHERE id =15;
DELETE FROM permission_sub_navs WHERE permission_id =15;
DELETE FROM sub_nav WHERE id in (1,2,3);
DELETE FROM permission WHERE id = 15;