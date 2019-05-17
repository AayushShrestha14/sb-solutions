INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (4, 'Questions','/home/eligibility/question', 'fa fa-question-circle');
INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (5, 'New Requests','/home/eligibility/new-requests', 'fa fa-flag');
INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (6, 'Eligible ','/home/eligibility/eligible', 'fa fa-check-circle');
INSERT INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (7, 'Non Eligible','/home/eligibility/non-eligible', 'fa fa-exclamation-circle');

INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 4);
INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 5);
INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 6);
INSERT INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (18, 7);