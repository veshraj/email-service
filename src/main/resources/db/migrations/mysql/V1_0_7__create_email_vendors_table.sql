CREATE TABLE email_service_providers (
                       id SMALLINT (4) NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       `class_name` VARCHAR(500) NOT NULL,
                       api_key VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       deleted_at TIMESTAMP NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;

INSERT INTO email_service_providers (`name`, `class_name`, api_key)
            VALUES ("Send Grid", "com.test.emailservice.modules.email.serviceproviers.SendgridService", "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"),
            ("Sparkpost", "com.test.emailservice.modules.email.serviceproviers.SparkPostService", "497a49ac6237917f6100e915cca77c1c071fdf99");