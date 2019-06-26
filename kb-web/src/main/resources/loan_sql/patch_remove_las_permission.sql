
DELETE FROM permission_api_list where permission_id  IN (2,4,5,7,8,9,10,11,12);
DELETE FROM role_permission_rights WHERE permission_id  IN (2,4,5,7,8,9,10,11,12);
DELETE FROM permission WHERE id  IN (2,4,5,7,8,9,10,11,12);
DELETE FROM loan_config_template_list WHERE template_list_id NOT IN (3);
DELETE FROM loan_template WHERE id NOT IN (3);




