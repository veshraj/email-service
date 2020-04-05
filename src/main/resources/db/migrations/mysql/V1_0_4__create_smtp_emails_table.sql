CREATE TABLE smtp_emails (
                       id BIGINT(20) NOT NULL AUTO_INCREMENT,
                       `email_from` VARCHAR (255) NOT NULL,
                       `email_to` TEXT NOT NULL,
                       subject VARCHAR(500) NOT NULL,
                       cc TEXT NULL,
                       bcc TEXT NULL,
                       message TEXT NOT NULL,
                       host_id SMALLINT(4),
                       user_id BIGINT(20) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       deleted_at TIMESTAMP NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (host_id) REFERENCES smtp_hosts(id),
                       FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;