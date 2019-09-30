BEGIN
    DECLARE
        @count smallint

    SET @count = (Select count(*) from role where role_name = 'CAD')


    BEGIN
        if (@count < 1)
            INSERT INTO role (created_at, last_modified_at, role_name, status, role_type,
                              role_access)
            VALUES ('2019-04-04 12:52:44', '2019-04-04 12:53:13', 'CAD', 1, 1, 2)

    END

END;
