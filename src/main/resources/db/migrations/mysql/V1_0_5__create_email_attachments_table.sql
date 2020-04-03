CREATE TABLE attachments (
                       id BIGINT(20) NOT NULL AUTO_INCREMENT,
                       url VARCHAR(255) NOT NULL,
                       email_id BIGINT(20),
                       message TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       deleted_at TIMESTAMP NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (email_id) REFERENCES smtp_emails(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;