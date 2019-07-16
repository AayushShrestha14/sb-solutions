 INSERT IGNORE INTO permission (id, permission_name, fa_icon, front_url, orders, status) VALUES (15,
            'Memo', 'fa fa-envelope', null, 62, 1);

            INSERT IGNORE INTO role_permission_rights (id, created_at, last_modified_at, permission_id, role_id)
            VALUES (15 , '2019-04-04 13:17:01', '2019-04-04 13:17:07', 15, 1);

   INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (1,
            'Under Review','/home/memo/underReview', 'fa fa-envelope-open');
            INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (2,
            'Compose','/home/memo/compose', 'fa fa-edit');
            INSERT IGNORE INTO sub_nav (id, sub_nav_name, front_url,fa_icon) VALUES (3,
            'Memo Type','/home/memo/type', 'fa fa-bookmark');

     INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (15, 1);
            INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (15, 2);
            INSERT IGNORE INTO permission_sub_navs (permission_id, sub_navs_id) VALUES (15, 3);