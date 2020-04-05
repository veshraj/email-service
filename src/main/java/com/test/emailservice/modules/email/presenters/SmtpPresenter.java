package com.test.emailservice.modules.email.presenters;

import com.test.emailservice.modules.email.entities.Smtp;
import com.test.emailservice.modules.email.resources.SmtpResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SmtpPresenter {
    public List<SmtpResource> map(List<Smtp> hostList) {
        ArrayList<SmtpResource> hostResourceList = new ArrayList<>();
        hostList.forEach(host -> {
            hostResourceList.add(this.map(host));
        });
        return hostResourceList;
    }

    public SmtpResource map(Smtp smtp) {
        if (Objects.nonNull(smtp)) {
            return SmtpResource.builder()
                           .id(smtp.getId())
                           .name(smtp.getName())
                           .host(smtp.getHost())
                           .username(smtp.getUsername())
                           .password(smtp.getPassword())
                           .port(smtp.getPort())
                           .build();
        }
        return  null;
    }
}
