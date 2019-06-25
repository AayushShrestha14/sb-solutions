
DELETE FROM permission_api_list where permission_id in (1,2,3,4,6,8,11,13,100,17);
DELETE FROM role_permission_rights WHERE permission_id in (1,2,3,4,6,8,11,13,100,17);
DELETE FROM permission WHERE id in (1,2,3,4,6,8,11,13,100,17);