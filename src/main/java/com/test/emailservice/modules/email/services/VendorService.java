package com.test.emailservice.modules.email.services;

import com.test.emailservice.core.resources.Pagination;
import com.test.emailservice.modules.email.entities.Vendor;
import com.test.emailservice.modules.email.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    @Autowired
    VendorRepository repository;

    public Page<Vendor> findAll(Integer page, Integer pageSize) {
        return repository.findAll(PageRequest.of(Pagination.getPageNumber(page),Pagination.getPageSize(pageSize) ));
    }
}
