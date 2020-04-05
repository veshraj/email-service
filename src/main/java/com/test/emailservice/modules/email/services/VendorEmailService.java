package com.test.emailservice.modules.email.services;

import java.lang.reflect.Method;
import java.util.List;

import com.test.emailservice.core.resources.Pagination;
import com.test.emailservice.modules.auth.entities.AuthUser;
import com.test.emailservice.modules.auth.threads.AuthUserThread;
import com.test.emailservice.modules.email.entities.Vendor;
import com.test.emailservice.modules.email.entities.VendorEmail;
import com.test.emailservice.modules.email.repositories.VendorEmailRepository;
import com.test.emailservice.modules.email.repositories.VendorRepository;
import com.test.emailservice.modules.email.resources.EmailRequest;
import com.test.emailservice.modules.email.resources.VendorEmailResource;
import com.test.emailservice.modules.email.sepecifications.VendorEmailSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VendorEmailService {
    @Autowired
    VendorEmailRepository repository;

    @Autowired
    VendorRepository vendorRepository;

    public Page<VendorEmail> findAll(String search, Integer page, Integer pageSize) {
        return repository.findAll(VendorEmailSpecification.filter(search), PageRequest.of(Pagination.getPageNumber(page),Pagination.getPageSize(pageSize)));
    }

    public VendorEmailResource sendMail(EmailRequest request) {
        List<Vendor> vendorList = vendorRepository.findAll();
        Vendor vendor;
        int index = 0;
        while (true) {
            try {
                vendor = vendorList.get(index);
                Object object =  Class.forName(vendor.getClassName()).getDeclaredConstructor().newInstance();
                Method method =  object.getClass().getDeclaredMethod("sendMail", String.class, String.class, String.class, String.class, String.class);
                method.invoke(object, vendor.getApiKey(), request.getFrom(), request.getTo(), request.getSubject(), request.getMessage());
                return repository.save(VendorEmail.builder()
                                               .from(request.getFrom())
                                               .to(request.getTo())
                                               .vendor(vendor)
                                               .user(AuthUserThread.getContext())
                                               .message(request.getMessage())
                                               .subject(request.getSubject())
                                               .build()).toVendorEmailResource();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (index == vendorList.size()) {
                break;
            }
            index++;
        }
        
        return null;
    }
}
