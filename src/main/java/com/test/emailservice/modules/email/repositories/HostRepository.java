package com.test.emailservice.modules.email.repositories;

import com.test.emailservice.modules.email.entities.Host;
import com.test.emailservice.modules.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HostRepository extends JpaRepository<Host, Integer> {
    @Query("SELECT h FROM Host h WHERE h.id=?1")
    Host findById(int id);
}
