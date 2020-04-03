package com.test.emailservice.modules.email.serviceproviers;


import com.sendgrid.*;
import com.test.emailservice.modules.email.interfaces.EmailVendorContract;

public class SendgridService implements EmailVendorContract {

    @Override
    public void sendMail(String apiKey, String from, String to, String subject, String content) throws Exception {
        Mail mail = new Mail(new Email(from), subject,new Email("veshraj.joshi1@gmail.com"), new Content("text/plain",content));
        SendGrid sg = new SendGrid(apiKey);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        System.out.println(apiKey);
        System.out.println(response.getBody().toString());
    }



}
