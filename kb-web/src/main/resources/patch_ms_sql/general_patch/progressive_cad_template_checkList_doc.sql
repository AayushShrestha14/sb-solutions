BEGIN
    DECLARE @assignmentOfReceivable smallint
    SET @assignmentOfReceivable= (SELECT count(*) from document where id = 2000)

    if (@assignmentOfReceivable < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2000, 'Letter of Arrangements',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter of Arrangements',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @deedHypoOfMachinery smallint
    SET @deedHypoOfMachinery= (SELECT count(*) from document where id = 2001)

    if (@deedHypoOfMachinery < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2001, 'Letter of Installment',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter of Installment',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2002)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2002, 'Letter of Lein',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter of Lein',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2003)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2003, 'Promissory Note',null,1,'2021-1-7',1,1,'2021-1-7',0,'Promissory Note',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2004)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2004, 'Loan Deed',null,1,'2021-1-7',1,1,'2021-1-7',0,'Loan Deed',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2005)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2005, 'Promissory Note Guarantor',null,1,'2021-1-7',1,1,'2021-1-7',0,'Promissory Note Guarantor',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2006)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2006, 'Letter of Agreement',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter of Agreement',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2007)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2007, 'Hire Purchase Deed',null,1,'2021-1-7',1,1,'2021-1-7',0,'Hire Purchase Deed',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2008)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2008, 'Letter Of Indemnity',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter Of Indemnity',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2009)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2009, 'Letter Of Disbursement',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter Of Disbursement',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2010)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2010, 'Guarantee Bond Corporate',null,1,'2021-1-7',1,1,'2021-1-7',0,'Guarantee Bond Corporate',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2011)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2011, 'Letter Of Continuity',null,1,'2021-1-7',1,1,'2021-1-7',0,'Letter Of Continuity',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2012)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2012, 'Guarantee Bond Personal',null,1,'2021-1-7',1,1,'2021-1-7',0,'Guarantee Bond Personal',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2013)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2013, 'Mortgage Deed',null,1,'2021-1-7',1,1,'2021-1-7',0,'Mortgage Deed',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2014)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2014, 'Cross Guarantee Bond',null,1,'2021-1-7',1,1,'2021-1-7',0,'Cross Guarantee Bond',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2015)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2015, 'A Hypothecation of Goods and Receivables',null,1,'2021-1-7',1,1,'2021-1-7',0,'A Hypothecation of Goods and Receivables',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;

BEGIN
    DECLARE @count smallint
    SET @count= (SELECT count(*) from document where id = 2016)

    if (@count < 1)
        BEGIN
            SET IDENTITY_INSERT document ON
            INSERT INTO document (id, name,url,status,created_at,created_by_id,modified_by_id,last_modified_at,version,display_name,check_type,contains_template)
            VALUES (2016, 'B Hypothecation of Goods and Receivables',null,1,'2021-1-7',1,1,'2021-1-7',0,'B Hypothecation of Goods and Receivables',null,1)
            SET IDENTITY_INSERT document OFF
        END
END;
