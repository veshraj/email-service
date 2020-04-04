package com.test.emailservice.modules.email.resources;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SmtpEmailResource {
    private long id;
    private String from;
    private String to;
    private String subject;
    private String message;
    private SmtpResource vendor;
}
