package com.test.emailservice.modules.email.repositories;

import com.test.emailservice.modules.email.entities.Smtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SmtpRepository extends JpaRepository<Smtp, Integer> {
    @Query("SELECT s FROM Smtp s WHERE s.id=?1")
    Smtp findById(int id);
}
