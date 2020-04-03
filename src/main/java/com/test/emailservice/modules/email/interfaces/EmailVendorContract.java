package com.test.emailservice.modules.email.interfaces;

public interface EmailVendorContract {
    public void sendMail(String apiKey, String form, String to,  String subject, String content) throws Exception;
}
