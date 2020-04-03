package com.test.emailservice.modules.email.resources;

import com.test.emailservice.core.QueryBuilder.DB;
import com.test.emailservice.modules.email.entities.Host;
import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotBlank;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HostRequest {
    private int id;
    @NotBlank(message = "Name field must not be emplty")
    private String name;

    @NotBlank(message = "Host field must not be empty")
    private String host;

    @NotBlank(message = "username field must not be empty")
    private String username;

    @NotBlank(message = "password field must not be empty")
    private String password;

    private int port;

    public void validate(BindingResult result) {
        DB db = new DB();
        ArrayList params = new ArrayList<Object>();
        params.add("{\"host\":\""+host+"\", \"id\":"+id+"}");
        ResultSet rs = db.callProc("validate_host_request",params).getProcResult();
        try {
            if(rs.next()) {
                if(rs.getBoolean("host_exists")) {
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
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Host toHost() {
        return Host.builder()
                       .id(id)
                       .name(name)
                       .host(host)
                       .username(username)
                       .password(password)
                       .port(port)
                       .build();
    }


}