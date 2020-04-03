package com.test.emailservice.modules.email.repositories;

import com.test.emailservice.modules.email.entities.SmtpEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<SmtpEmail, Long> {

}
