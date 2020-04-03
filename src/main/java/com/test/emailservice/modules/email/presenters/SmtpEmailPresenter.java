package com.test.emailservice.modules.email.presenters;

import com.test.emailservice.modules.email.entities.SmtpEmail;
import com.test.emailservice.modules.email.resources.SmtpEmailResource;
import com.test.emailservice.modules.email.resources.VendorEmailResource;

import java.util.ArrayList;
import java.util.List;

public class SmtpEmailPresenter {

    public List<SmtpEmailResource> map(List<SmtpEmail> smtpEmailList) {
        ArrayList<SmtpEmailResource> smtpEmailResourceArrayList = new ArrayList<>();
        smtpEmailList.forEach(smtpEmail -> {
            smtpEmailResourceArrayList.add(this.map(smtpEmail));
        });
        return smtpEmailResourceArrayList;
    }

    public SmtpEmailResource map(SmtpEmail smtpEmail) {
        HostPresenter hostPresenter = new HostPresenter();

        return SmtpEmailResource.builder()
                       .id(smtpEmail.getId())
                       .from(smtpEmail.getFrom())
                       .to(smtpEmail.getTo())
                       .subject(smtpEmail.getSubject())
                       .message(smtpEmail.getMessage())
                       .vendor(hostPresenter.map(smtpEmail.getHost()))
                       .build();
    }
}
