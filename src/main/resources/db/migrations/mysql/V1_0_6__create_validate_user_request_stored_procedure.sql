DROP PROCEDURE IF EXISTS validate_users_request;
DELIMITER $$

CREATE PROCEDURE validate_users_request(IN requestData JSON)
BEGIN
    SET @userId = requestData->>"$.id";
    SET @userEmail = requestData->>"$.email";
    SET @userMobileNumber = requestData->>"$.mobileNumber";
    SET @userWebsite = requestData->>"$.website";

    PREPARE preparedStatement FROM "SET @emailCount = (SELECT count(id) FROM users WHERE email = ? AND id <> ? AND deleted_at IS NULL)";
    EXECUTE preparedStatement USING @userEmail, @userId;
    DEALLOCATE PREPARE preparedStatement;

    PREPARE preparedStatement FROM "SET @mobileNumberCount = (SELECT count(id) FROM users WHERE mobile_number = ? AND id <> ? AND deleted_at IS NULL)";
    EXECUTE preparedStatement USING @userMobileNumber, @userId;
    DEALLOCATE PREPARE preparedStatement;

    PREPARE preparedStatement FROM "SET @websiteCount = (SELECT count(id) FROM users WHERE website = ? AND id <> ? AND deleted_at IS NULL)";
    EXECUTE preparedStatement USING @userWebsite, @userId;
    DEALLOCATE PREPARE preparedStatement;


    SELECT CASE
               WHEN @emailCount > 0
                   THEN true
               ELSE
                   false
            END as email_exists,
            CASE
                WHEN @mobileNumberCount > 0
                    THEN true
                ELSE false
            END as mobile_number_exists,
           CASE
                WHEN @websiteCount >0
                    THEN true
                ELSE false
            END as website_exists;

END $$

DELIMITER ;