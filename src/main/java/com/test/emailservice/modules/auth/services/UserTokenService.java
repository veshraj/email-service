package com.test.emailservice.modules.auth.services;

import com.test.emailservice.modules.auth.entities.AuthUser;
import com.test.emailservice.modules.auth.entities.UserToken;
import com.test.emailservice.modules.auth.repositories.UserTokensRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    @Autowired
    UserTokensRepository repository;

    public UserToken create(Long userId, String tokenableType) {
        return repository.save(UserToken.builder()
                    .tokenableType(tokenableType)
                    .tokenalbeId(userId)
                    .token(getRandomToken())
                    .build());
    }

    private String getRandomToken() {
        return RandomStringUtils.randomAlphanumeric(64);
    }
}
