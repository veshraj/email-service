package com.test.emailservice.modules.users.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResource {
    @JsonProperty("id")
    private Long id;

    private String name;

    private String email;

    private String mobileNumber;

    private String website;


}
