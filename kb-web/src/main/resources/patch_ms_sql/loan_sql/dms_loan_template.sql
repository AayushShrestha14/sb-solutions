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

