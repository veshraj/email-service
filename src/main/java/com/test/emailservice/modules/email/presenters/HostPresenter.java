package com.test.emailservice.modules.email.presenters;

import com.test.emailservice.modules.email.entities.Smtp;
import com.test.emailservice.modules.email.resources.SmtpResource;

import java.util.ArrayList;
import java.util.List;

public class HostPresenter {
    public List<SmtpResource> map(List<Smtp> hostList) {
        ArrayList<SmtpResource> hostResourceList = new ArrayList<>();
        hostList.forEach(host -> {
            hostResourceList.add(this.map(host));
        });
        return hostResourceList;
    }

    public SmtpResource map(Smtp host) {
        return SmtpResource.builder()
                       .id(host.getId())
                       .name(host.getName())
                       .host(host.getHost())
                       .username(host.getUsername())
                       .password(host.getPassword())
                       .port(host.getPort())
                       .build();
    }
}
