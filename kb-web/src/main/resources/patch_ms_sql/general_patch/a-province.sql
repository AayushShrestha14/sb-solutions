
BEGIN
DECLARE @count smallint
SET @count = (Select count(*) from province)

if(@count < 7)
BEGIN

SET IDENTITY_INSERT province ON
INSERT  INTO province (id, name) VALUES
(1, 'Province 1'),
(2, 'Province 2'),
(3, 'Province 3'),
(4, 'Province 4'),
(5, 'Province 5'),
(6, 'Province 6'),
(7, 'Province 7')
SET IDENTITY_INSERT province OFF
END
END ;
