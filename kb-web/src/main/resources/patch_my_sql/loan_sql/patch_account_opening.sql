INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status)
VALUES (19, 'Account Opening', 'fa fa-money-check', '/home/admin/openingAccount', 56, 1);

INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
VALUES (19, '2019-04-04 13:17:01', '2019-04-04 13:17:07', 19, 1);