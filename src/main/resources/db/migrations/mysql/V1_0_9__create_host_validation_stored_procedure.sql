DROP PROCEDURE IF EXISTS validate_host_request;
DELIMITER $$

CREATE PROCEDURE validate_host_request(IN requestData JSON)
BEGIN
    SET @host = requestData->>"$.host";
    SET @id = requestData->>"$.id";

    PREPARE preparedStatement FROM "SET @hostCount = (SELECT count(id) FROM smtp_hosts WHERE host = ? AND id <> ? AND deleted_at IS NULL)";
    EXECUTE preparedStatement USING @host, @id;
    DEALLOCATE PREPARE preparedStatement;

    SELECT CASE
               WHEN @hostCount > 0
                   THEN true
               ELSE
                   false
            END as host_exists;

END $$

DELIMITER ;