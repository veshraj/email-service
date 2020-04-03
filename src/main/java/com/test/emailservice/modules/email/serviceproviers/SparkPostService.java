package com.test.emailservice.modules.email.serviceproviers;

import com.sparkpost.Client;
import com.test.emailservice.modules.email.interfaces.EmailVendorContract;
import lombok.Builder;

@Builder
public class SparkPostService implements EmailVendorContract {
    public SparkPostService(){}

    @Override
    public void sendMail(String apiKey, String from, String to, String subject, String content) throws Exception {
        Client client = new Client(apiKey);
        client.sendMessage(from, to, subject, content, "");
    }
}
