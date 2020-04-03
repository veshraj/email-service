package com.test.emailservice.modules.email.presenters;

import com.test.emailservice.modules.email.entities.Host;
import com.test.emailservice.modules.email.resources.HostResource;

import java.util.ArrayList;
import java.util.List;

public class HostPresenter {
    public List<HostResource> map(List<Host> hostList) {
        ArrayList<HostResource> hostResourceList = new ArrayList<>();
        hostList.forEach(host -> {
            hostResourceList.add(this.map(host));
        });
        return hostResourceList;
    }

    public HostResource map(Host host) {
        return HostResource.builder()
                       .id(host.getId())
                       .name(host.getName())
                       .host(host.getHost())
                       .username(host.getUsername())
                       .password(host.getPassword())
                       .port(host.getPort())
                       .build();
    }
}
