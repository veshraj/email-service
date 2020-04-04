package com.test.emailservice.modules.auth.services;


import com.test.emailservice.modules.auth.entities.AuthUser;
import com.test.emailservice.modules.auth.entities.UserToken;
import com.test.emailservice.modules.auth.repositories.UserTokensRepository;
import com.test.emailservice.modules.auth.resources.AuthRequest;
import com.test.emailservice.modules.auth.threads.AuthUserThread;
import com.test.emailservice.modules.auth.utils.JWTUtil;
import com.test.emailservice.modules.users.repositories.UserRepository;
import com.test.emailservice.modules.users.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserTokensRepository tokensRepository;

    @Autowired
    UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.test.emailservice.modules.users.entities.User user = AuthUserThread.getContext();
        if (user == null) {
            user = repository.findByMobileNumberOrEmail(userName);
        }

        if (user != null) {

            return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
        }

        throw new UsernameNotFoundException("Invalid user and name password");
    }


    /**
     * @param token
     * @return
     * @throws Exception
     */
    public int logout(String token) {
        String authToken = jwtUtil.extractAllClaims(token.substring(7)).getSubject();
        UserToken userToken = tokensRepository.getUserTokenByToken(authToken);
        try {
            tokensRepository.deleteById(userToken.getId());
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * @param request
     * @return
     * @Description this function will call while confirming the login crdentials
     */
    public AuthUser loadUserByUsernamePassword(AuthRequest request) {
        com.test.emailservice.modules.users.entities.User user = repository.findByMobileNumberOrEmail(request.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (Objects.nonNull(user) && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthUser(user);
        }
        throw new UsernameNotFoundException("Invalid user name or password");

    }
}
