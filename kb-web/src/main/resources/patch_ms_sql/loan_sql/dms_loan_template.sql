BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from loan_template where id = 3)
if(@count = 0)
BEGIN

SET IDENTITY_INSERT loan_template on

 INSERT  INTO loan_template (id, name, template_url, order_url, status, template_view, created_by_id, modified_by_id, created_at, last_modified_at, version) VALUES
           (3, 'General', '#loanType',3, 1, NULL, NULL, NULL,'2019-05-28', '2019-05-06', 0)

SET IDENTITY_INSERT loan_template off

END
END;


BEGIN
DECLARE @count_financial smallint
SET @count_financial = (Select count(*) from loan_template where id = 5)
if(@count_financial = 0)
BEGIN

SET IDENTITY_INSERT loan_template on

 INSERT  INTO loan_template (id, name, template_url, order_url, status, template_view, created_by_id, modified_by_id, created_at, last_modified_at, version) VALUES
           (5, 'Financial', '#financialInfo',5, 1, NULL, NULL, NULL,'2019-08-23', '2019-08-23', 0)

SET IDENTITY_INSERT loan_template off

END
END;

