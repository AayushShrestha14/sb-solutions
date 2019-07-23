BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from loan_cycle)

if(@count < 2)
BEGIN

SET IDENTITY_INSERT loan_cycle ON
INSERT  INTO loan_cycle (id, cycle, label, created_by_id, modified_by_id, created_at, last_modified_at, version)
 VALUES ('1', 'New', 'Document required during new Loan', null, null, '2019-06-20', '2019-06-26', '0'),
        ('2', 'Re-New', 'Document required during re-new Loan', null, null , '2019-06-20', '2019-06-26', '0'),
        ('3', 'Closure', 'Document required while closing Loan', null, null , '2019-06-20', '2019-06-26', '0');
SET IDENTITY_INSERT loan_cycle OFF
END
END;