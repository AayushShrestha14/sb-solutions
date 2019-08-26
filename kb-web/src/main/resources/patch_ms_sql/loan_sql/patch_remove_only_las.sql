
DELETE FROM permission_api_list where permission_id  IN (5,7,9,10,12);
DELETE FROM role_permission_rights WHERE permission_id  IN (5,7,9,10,12);
DELETE FROM permission WHERE id  IN (5,7,9,10,12);
DELETE FROM loan_config_template_list WHERE template_list_id NOT IN (3,5);
DELETE FROM loan_template WHERE id NOT IN (3,5);




