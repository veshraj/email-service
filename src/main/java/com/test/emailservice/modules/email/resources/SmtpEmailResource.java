package com.test.emailservice.modules.email.resources;

import com.test.emailservice.modules.email.presenters.VendorPresenter;
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
    private HostResource vendor;
}
