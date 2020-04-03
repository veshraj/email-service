package com.test.emailservice.modules.auth.controllers;


import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.responses.BaseResponse;
import com.test.emailservice.core.utils.BindingErrorUtil;
import com.test.emailservice.modules.auth.entities.AuthUser;
import com.test.emailservice.modules.auth.entities.UserToken;
import com.test.emailservice.modules.auth.resources.AuthRequest;
import com.test.emailservice.modules.auth.services.AuthService;
import com.test.emailservice.modules.auth.services.UserTokenService;
import com.test.emailservice.modules.auth.utils.JWTUtil;
import com.test.emailservice.modules.users.resources.UserResource;
import com.test.emailservice.modules.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UserService userService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Authentication User using OTP", tags = {"Auth"})
    @PostMapping(value = "authenticate", consumes = {"application/json"})
    public ResponseEntity<BaseResponse<UserResource>> authenticate(@Valid @RequestBody AuthRequest request, BindingResult result) {
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid data provided", BindingErrorUtil.getBindingErrors(result));
        }
        try {
            final AuthUser userDetails = authService.loadUserByUsernamePassword(request);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getEmail(), userDetails.getPassword())
            );
            // create new token
            UserToken userToken = userTokenService.create(userDetails.getId(), "users");

            final String jwt = jwtUtil.generateToken(userToken);
            HttpHeaders headers = new HttpHeaders();
            headers.add("token", jwt);
            // set headers to in the response
            return new BaseResponse("User Logged in successfully", HttpStatus.OK, userService.getItem(userDetails.getId())).setHeaders(headers).getResponse();

        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "OTP not found", null);
        }
    }

    @Operation(summary = "Logout user", tags = {"Auth"})
    @PostMapping(value = "authenticate/logout", consumes = {"application/json"})
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String token) {
        if (authService.logout(token) > 0) {
            return new BaseResponse("Token revoked", HttpStatus.ACCEPTED, null).getResponse();
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Token not found", null);
    }

}
