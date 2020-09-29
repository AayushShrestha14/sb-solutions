
BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from province)

if(@count < 7)
BEGIN

SET IDENTITY_INSERT province ON
INSERT  INTO province (id, name) VALUES
(1, 'Province 1'),
(2, 'Province 2'),
(3, 'Bagmati Pradesh'),
(4, 'Gandaki Pradesh'),
(5, 'Province 5'),
(6, 'Karnali Pradesh'),
(7, 'Sudurpashchim Pradesh')
SET IDENTITY_INSERT province OFF
END
END ;
