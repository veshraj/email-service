package com.test.emailservice.modules.auth.repositories;

import com.test.emailservice.modules.auth.entities.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserTokensRepository extends JpaRepository<UserToken, Long> {
    @Query("SELECT t FROM UserToken t WHERE token=?1")
    UserToken getUserTokenByToken(String token);
}
