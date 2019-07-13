INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status)
 VALUES (19,  'Account Opening', 'fa fa-money-check', null, 56, 1);
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (9, 'Pending Account','/home/admin/openingAccount', 'fa fa-money-check');
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (10, 'Approval Account','/home/admin/approvalOpeningAccount', 'fa fa-money-check');
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (11, 'Rejected Account','/home/admin/rejectedOpeningAccount', 'fa fa-money-check');

INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (19, 9);
INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (19, 10);
INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (19, 11);

 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (19 , '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  19, 1);