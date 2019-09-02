DELETE
FROM permission_sub_navs
WHERE permission_id = 19;
DELETE
FROM sub_nav
WHERE id in (9, 10, 11);
DELETE
FROM role_permission_rights
WHERE permission_id = 19;

DELETE
FROM permission
WHERE id = 19;