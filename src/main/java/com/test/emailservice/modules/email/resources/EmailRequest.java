package com.test.emailservice.modules.email.resources;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    @NotBlank(message = "Sender email must not be empty")
    @Email(message = "Invalid sender email")
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @NotBlank(message = "Recipient email must not be empty ")
    @Email(message = "Invalid recipient email")
    private String to;

    @Email(message = "Invalid email")
    private String cc;
    @Email(message = "Invalid email")
    private String bcc;
    private String subject;

    @NotBlank(message = "message body must not be empty")
    private String message;



    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
