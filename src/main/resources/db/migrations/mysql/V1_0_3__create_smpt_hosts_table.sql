CREATE TABLE smtp_hosts (
                       id SMALLINT (4) NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       host VARCHAR(255) NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(30) NOT NULL,
                       port VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                       deleted_at TIMESTAMP NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf32;

INSERT INTO smtp_hosts (name, host, username, password, port)
            VALUES ("Mail Trap", "smtp.mailtrap.io", "f1d942159eb38d", "7d77bf966fe637", 465),
            ("Google Mail", "smtp.gmail.com", "fmcrms@gmail.com", "fmcrms123", 465);
            ;
