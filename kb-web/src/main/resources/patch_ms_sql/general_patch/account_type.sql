

BEGIN
DECLARE @count smallint
SET @count = (select count(*) from account_purpose)
if(@count = 0)
BEGIN
SET IDENTITY_INSERT account_purpose ON
INSERT  INTO account_purpose (id, name) VALUES (1, 'Salary'), (2, 'Saving'), (3, 'Others')
SET IDENTITY_INSERT account_purpose OFF

SET IDENTITY_INSERT account_type ON
INSERT  INTO account_type (id, name) VALUES (1, 'Saving Account'),
(2, 'Current Account'), (3, 'Money Market Account'), (4, 'Retirement Accounts'),
(5, 'Fixed Account'), (6, 'Women Special Saving'), (7, 'Investor Saving Account'),
(8, 'Nagarik Batchat Khata'), (9, 'Pension Payment Saving Account'), (10, 'Social Security Saving Account')
SET IDENTITY_INSERT account_type Off


INSERT  INTO account_type_account_purpose (account_type_id, account_purpose_id) VALUES
(1, 2),
(2, 1),
(3, 3),
(4, 2),
(5, 2),
(6, 2),
(7, 3),
(8, 1),
(9, 1),
(10, 3)
END

END ;







