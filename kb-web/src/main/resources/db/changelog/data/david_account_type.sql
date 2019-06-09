INSERT INTO `account_purpose` (`id`, `name`) VALUES (1, 'Salary'), (2, 'Saving'), (3, 'Investment');

INSERT INTO `account_type` (`id`, `name`, `account_purpose_id`) VALUES (1, 'Saving Account', 2),
(2, 'Current Account', 1), (3, 'Money Market Account', 3), (4, 'Retirement Accounts', 2),
(5, 'Fixed Account', 2), (6, 'Women Special Saving', 2), (7, 'Investor Saving Account', 3),
(8, 'Nagarik Batchat Khata', 1), (9, 'Pension Payment Saving Account', 1), (10, 'Social Security Saving Account', 3);