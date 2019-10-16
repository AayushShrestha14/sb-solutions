BEGIN
DECLARE @count smallint
SET @count = (select count(*) from account_type)

if (@count = 0)
BEGIN
SET IDENTITY_INSERT account_type ON
INSERT INTO account_type (id, name) VALUES (1, 'Current'), (2, 'Saving'), (3, 'Others')
SET IDENTITY_INSERT account_type OFF

SET IDENTITY_INSERT account_purpose ON
INSERT  INTO account_purpose (id, name) VALUES
/*(1, 'Saving Account'),
(2, 'Current Account'),
(3, 'Money Market Account'),
(4, 'Retirement Accounts'),
(5, 'Fixed Account'),
(6, 'Women Special Saving'),
(7, 'Investor Saving Account'),
(8, 'Nagarik Batchat Khata'),
(9, 'Pension Payment Saving Account'),
(10, 'Social Security Saving Account')*/
(1, 'Saving'),
(2, 'Remittance'),
(3, 'Business Transactions'),
(4, 'Investment'),
(5, 'Loan Purpose'),
(6, 'Others')
SET IDENTITY_INSERT account_purpose Off

INSERT  INTO account_type_account_purpose (account_type_id, account_purpose_id) VALUES
/*(1, 2),
(2, 1),
(3, 3),
(4, 2),
(5, 2),
(6, 2),
(7, 3),
(8, 1),
(9, 1),
(10, 3)*/
(1,1), (1,2), (1,3), (1,4), (1,5), (1,6),
(2,1), (2,2), (2,3), (2,4), (2,5), (2,6),
(3,1), (3,2), (3,3), (3,4), (3,5), (3,6)

END

END;
