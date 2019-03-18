CREATE TABLE if not exists `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) PRIMARY KEY,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE if not exists `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE if not exists `role_permission_rights` (
 role_id BIGINT(20) DEFAULT null,
  permission_id BIGINT(20) DEFAULT null,
   right_id BIGINT(20) DEFAULT null
   );

 ALTER TABLE `role_permission_rights` add  right_id BIGINT(20) DEFAULT NULL;
