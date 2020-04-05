package com.test.emailservice.modules.auth.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.modules.auth.entities.UserToken;
import com.test.emailservice.modules.auth.repositories.UserTokensRepository;
import com.test.emailservice.modules.auth.services.AuthService;
import com.test.emailservice.modules.auth.threads.AuthUserThread;
import com.test.emailservice.modules.auth.utils.JWTUtil;
import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.repositories.UserRepository;
import com.test.emailservice.modules.users.resources.UserResource;
import com.test.emailservice.modules.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    AuthService authService;

    @Autowired
    UserTokensRepository userTokensRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                jwt = authorizationHeader.substring(7);
                token = jwtUtil.extractUsername(jwt);
            }

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserToken userToken = userTokensRepository.getUserTokenByToken(token);
                if (jwtUtil.validateToken(jwt, userToken)) {
                    User user = userRepository.findById(userToken.getTokenalbeId());
                    /**
                     * Set local thread which will be available over the application
                     * this context must be cleared out on the lifecycle of the request
                     */
                    AuthUserThread.setContext(user);
                    UserDetails userDetails = this.authService.loadUserByUsername(user.getEmail());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (RuntimeException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"code\": "+HttpStatus.FORBIDDEN.value()+",\"message\":\"Invalid token passed\"}");
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
