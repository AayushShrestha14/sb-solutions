BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from loan_template)
if(@count < 2)
BEGIN

SET IDENTITY_INSERT loan_template on

 INSERT  INTO loan_template (id, name, template_url, order_url, status, template_view, created_by_id, modified_by_id, created_at, last_modified_at, version) VALUES
            (1, 'Customer Info', '#basicInfo', 1, 1, NULL, NULL, NULL, '2019-04-04', '2019-04-04', 0),
            (2, 'Company Info', '#companyInfo', 2, 1, NULL, NULL, NULL, '2019-05-28', '2019-05-06', 0),
            (4, 'Proposal', '#proposalInfo',4, 1, NULL, NULL, NULL,'2019-05-28', '2019-05-06', 0),
            (5, 'Credit Risk Grading', '#creditGrading', 5, 1, NULL, NULL, NULL, '2019-08-11', '2019-08-18', 0),
            (6, 'Site Visit', '#siteVisit', 6, 1, NULL, NULL, NULL, '2019-08-20', '2019-08-25', 0);

SET IDENTITY_INSERT loan_template off

DELETE FROM loan_template where id = 3

END
END;



