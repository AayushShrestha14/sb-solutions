INSERT IGNORE INTO permission (id,  permission_name, fa_icon, front_url, orders, status) VALUES (18,  'Eligibility', 'fa fa-check-square', '/home/admin/eligibility', 62, 1);
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (4, 'Questions','/home/admin/eligibility/question', 'fa fa-question-circle');
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (5, 'General Questions','/home/admin/eligibility/general-question', 'fa fa-exclamation-circle');
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (6, 'New Requests','/home/admin/eligibility/new-requests', 'fa fa-flag');
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (7, 'Eligible ','/home/admin/eligibility/eligible', 'fa fa-check-circle');
INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (8, 'Non Eligible','/home/admin/eligibility/non-eligible', 'fa fa-exclamation-circle');

INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 4);
INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 5);
INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 6);
INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 7);
INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 8);

 INSERT IGNORE INTO role_permission_rights (id, created_at,  last_modified_at,  permission_id, role_id)
 VALUES (18 , '2019-04-04 13:17:01',  '2019-04-04 13:17:07',  18, 1);