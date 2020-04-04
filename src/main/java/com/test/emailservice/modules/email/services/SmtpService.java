package com.test.emailservice.modules.email.services;

import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.modules.email.entities.Smtp;
import com.test.emailservice.modules.email.repositories.SmtpRepository;
import com.test.emailservice.modules.email.resources.SmtpRequest;
import com.test.emailservice.modules.email.resources.SmtpResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SmtpService {
    @Autowired
    SmtpRepository repository;

    public List<Smtp> findAll() {
        try{
//            Page<Host> pageList = repository.findAll(PageRequest.of(Pagination.getPageNumber(page), Pagination.getPageSize(pageSize)));
//            System.out.println(pageList.getTotalPages());
//            return pageList;
            return repository.findAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SmtpResource create(SmtpRequest request) {
        System.out.println(request.toString());
        return repository.save(request.toSmtp()).toHostResource();
    }

    public SmtpResource getItem(int id) {
        return repository.findById(id).toHostResource();
    }

    public SmtpResource update(SmtpRequest request) {
        Smtp host = repository.findById(request.getId());
        if(host == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Host Not found", new HashMap());
        }
        host = request.toSmtp();
        return repository.save(host).toHostResource();
    }


}
