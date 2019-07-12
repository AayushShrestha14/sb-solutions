SET IDENTITY_INSERT loan_cycle ON;
INSERT  INTO loan_cycle (id, cycle, label, created_by_id, modified_by_id, created_at, last_modified_at, version)
 VALUES ('1', 'New', 'Document required during new Loan', 1, 1, '2019-06-20', '2019-06-26', '0'),
        ('2', 'Re-New', 'Document required during re-new Loan', 1, 1, '2019-06-20', '2019-06-26', '0');
SET IDENTITY_INSERT loan_cycle OFF;