package com.test.emailservice.modules.email.presenters;

import com.test.emailservice.modules.email.entities.Vendor;
import com.test.emailservice.modules.email.resources.VendorResource;

import java.util.ArrayList;
import java.util.List;

public class VendorPresenter {
    public List<VendorResource> map(List<Vendor> vendorList) {
        ArrayList<VendorResource> vendorResourceList = new ArrayList<>();
        vendorList.forEach(vendor -> {
            vendorResourceList.add(this.map(vendor));
        });
        return vendorResourceList;
    }

    public VendorResource map(Vendor vendor) {
        return VendorResource.builder()
                                       .id(vendor.getId())
                                       .name(vendor.getName())
                                       .className(vendor.getClassName())
                                       .apiKey(vendor.getApiKey())
                                       .build();
    }
}
