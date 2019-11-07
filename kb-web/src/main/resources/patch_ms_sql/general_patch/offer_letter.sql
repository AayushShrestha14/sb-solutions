BEGIN
    DECLARE
        @count smallint
    DECLARE
        @count_product_mode smallint
    SET @count = (Select count(*) from offer_letter)
    SET @count_product_mode = (SELECT count(*) from product_mode where id = 6 and status = 1)

    if (@count_product_mode > 0)
        BEGIN
            if (@count < 2)
                BEGIN
                    SET IDENTITY_INSERT offer_letter ON
                    INSERT INTO offer_letter (id, name, template_url)
                    VALUES (1, 'Birth Mark Letter', 'home/loan/birth-mark-letter'),
                           (2, 'Success Letter', 'home/loan/success-offer-letter')
                    SET IDENTITY_INSERT offer_letter OFF
                END
        END
END;
