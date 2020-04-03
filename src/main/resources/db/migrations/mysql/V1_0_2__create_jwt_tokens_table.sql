CREATE TABLE user_tokens (
                       id BIGINT(20) NOT NULL AUTO_INCREMENT,
                       tokenable_type VARCHAR(255) NOT NULL,
                       tokenable_id BIGINT NOT NULL,
                       token VARCHAR(64) NOT NULL,
                       last_used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       deleted_at TIMESTAMP NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;