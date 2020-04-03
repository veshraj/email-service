package com.test.emailservice.modules.users.resources;

import com.test.emailservice.core.QueryBuilder.DB;
import com.test.emailservice.modules.users.entities.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserUpdateRequest {
    DB db;

    @NotNull(message = "User id not found")
    private long id;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid email")
    private String email;


    private String mobileNumber;
    private String website;


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

    public User toUser() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                       .id(id)
                       .name(name)
                       .email(email)
                       .website(website)
                       .mobileNumber(mobileNumber)
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
