INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon, permission_id) VALUES (2, 'Questions','/home/eligibility/question', 'fa fa-question-circle',17);
INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon, permission_id) VALUES (3, 'New Requests','/home/eligibility/new-requests', 'fa fa-flag',17);
INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon, permission_id) VALUES (4, 'Eligible ','/home/eligibility/eligible', 'fa fa-check-circle',17);
INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon, permission_id) VALUES (5, 'Non Eligible','/home/eligibility/non-eligible', 'fa fa-exclamation-circle',17);

INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (17, 2);
INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (17, 3);
INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (17, 4);
INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (17, 5);