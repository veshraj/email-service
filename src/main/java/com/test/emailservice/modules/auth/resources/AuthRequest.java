package com.test.emailservice.modules.auth.resources;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotBlank(message = "email must not be empty")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "passweord must not be empty")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

