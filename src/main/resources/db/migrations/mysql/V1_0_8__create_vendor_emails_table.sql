CREATE TABLE vendor_emails (
                       id BIGINT(20) NOT NULL AUTO_INCREMENT,
                       `email_from` VARCHAR (255) NOT NULL,
                       `email_to` VARCHAR(255) NOT NULL,
                       subject VARCHAR(255) NOT NULL,
                       message TEXT NOT NULL,
                       host_id SMALLINT(4),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       deleted_at TIMESTAMP NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (host_id) REFERENCES email_service_providers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;