package com.test.emailservice.modules.email.services;

import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.resources.Pagination;
import com.test.emailservice.modules.email.entities.Host;
import com.test.emailservice.modules.email.repositories.HostRepository;
import com.test.emailservice.modules.email.resources.HostRequest;
import com.test.emailservice.modules.email.resources.HostResource;
import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.resources.UserResource;
import com.test.emailservice.modules.users.resources.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class HostService {
    @Autowired
    HostRepository repository;

    public List<Host> findAll() {
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

    public HostResource create(HostRequest request) {
        System.out.println(request.toString());
        return repository.save(request.toHost()).toHostResource();
    }

    public HostResource getItem(int id) {
        return repository.findById(id).toHostResource();
    }

    public HostResource update(HostRequest request) {
        Host host = repository.findById(request.getId());
        if(host == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Host Not found", new HashMap());
        }
        host = request.toHost();
        return repository.save(host).toHostResource();
    }


}
