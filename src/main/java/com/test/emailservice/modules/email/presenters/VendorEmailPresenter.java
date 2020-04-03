package com.test.emailservice.modules.email.presenters;

import com.test.emailservice.modules.email.entities.VendorEmail;
import com.test.emailservice.modules.email.resources.VendorEmailResource;
import com.test.emailservice.modules.email.resources.VendorResource;

import java.util.ArrayList;
import java.util.List;

public class VendorEmailPresenter {

    public List<VendorEmailResource> map(List<VendorEmail> vendorEmailList) {
        ArrayList<VendorEmailResource> vendorEmailResourcesList = new ArrayList<>();
        vendorEmailList.forEach(vendorEmail -> {
            vendorEmailResourcesList.add(this.map(vendorEmail));
        });
        return vendorEmailResourcesList;
    }

    public VendorEmailResource map(VendorEmail vendorEmail) {
        return VendorEmailResource.builder()
                       .id(vendorEmail.getId())
                       .from(vendorEmail.getFrom())
                       .to(vendorEmail.getTo())
                       .subject(vendorEmail.getSubject())
                       .message(vendorEmail.getMessage())
                       .vendor(VendorResource.builder()
                                       .id(vendorEmail.getVendor().getId())
                                       .name(vendorEmail.getVendor().getName())
                                       .className(vendorEmail.getVendor().getClassName())
                                       .apiKey(vendorEmail.getVendor().getApiKey())
                                       .build())
                       .build();
    }
}
