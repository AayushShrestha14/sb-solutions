
DELETE FROM permission_api_list where permission_id NOT IN (1,2,3,4,6,8,11,13,100,17);
DELETE FROM role_permission_rights WHERE permission_id NOT IN (1,2,3,4,6,8,11,13,100,17);
DELETE FROM permission WHERE id NOT IN (1,2,3,4,6,8,11,13,100,17);
DELETE FROM loan_config_template_list WHERE template_list_id NOT IN (3);
DELETE FROM loan_template WHERE id NOT IN (3);




-- DELETE FROM permission_api_list where permission_id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,100,17);
-- DELETE FROM role_permission_rights WHERE permission_id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,100,17);
-- DELETE FROM permission WHERE id IN (1,2,3,4,5,6,7,8,9,10,11,12,13,100,17);