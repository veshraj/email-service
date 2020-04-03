package com.test.emailservice.modules.users.resources;


import com.test.emailservice.core.QueryBuilder.DB;
import com.test.emailservice.modules.users.dao.UserRequestValidationResult;
import com.test.emailservice.modules.users.entities.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.test.emailservice.modules.users.repositories.UserRepository;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Service
public class UserRequest {
    DB db;

    private long id;

    @NotBlank(message = "Name field must not be empty")
    private String name;

    @NotBlank(message = "Email field must not be empty")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, message = "Password must have 8 characters")
    @Size(max = 20, message = "Password must not exceed 20 characters")
    private String password;

    private String confirmPassword;
    private String mobileNumber;
    private String website;

    public UserRequest(long id, String name, String email, String mobileNumber, String password, String confirmPassword, String website){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.confirmPassword = confirmPassword;
        this.website = website;
    }

    public UserRequest(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @AssertTrue(message = "Confirm password did not match")
    public boolean isConfirmPassword() {
        return this.password.equals(this.confirmPassword);
    }

    public User toUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                       .id(id)
                       .name(name)
                       .email(email)
                       .website(website)
                       .mobileNumber(mobileNumber)
                       .password(passwordEncoder.encode(password))
                       .build();
    }

    public void validate(BindingResult result) {
        if(db == null) {
            db = new DB();
        }
        ArrayList params = new ArrayList<Object>();
        params.add(this.toJosonString());
        ResultSet rs = db.callProc("validate_users_request",params).getProcResult();
        try{
            if(rs.next()) {
                if(rs.getBoolean("email_exists")) {
                    FieldError error = new FieldError(
                            "UserResource",
                            "email",
                            "",
                            false,
                            new String[]{"errorCode"},
                            new Object[]{},
                            "Email already taken"
                    );
                    result.addError(error);
                }

                if(rs.getBoolean("mobile_number_exists")) {
                    FieldError error = new FieldError(
                            "UserResource",
                            "mobileNumber",
                            "",
                            false,
                            new String[]{"errorCode"},
                            new Object[]{},
                            "Mobible number already taken"
                    );
                    result.addError(error);
                }

                if(rs.getBoolean("website_exists")) {
                    FieldError error = new FieldError(
                            "UserResource",
                            "website",
                            "",
                            false,
                            new String[]{"errorCode"},
                            new Object[]{},
                            "Website already taken"
                    );
                    result.addError(error);
                }
            }
        }
        catch (SQLException sqlie) {

        }

    }

    public String toJosonString() {
        return "{" +
                  "\"id\":" + id + "," +
                  "\"name\":\"" + name + "\"," +
                  "\"email\":\"" + email + "\"," +
                  "\"mobileNumber\":\"" + mobileNumber + "\"," +
                  "\"website\":\"" + website + "\"" +
               "}";
    }
}
