package com.test.emailservice.modules.users.services;


import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.resources.Pagination;
import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.repositories.UserRepository;
import com.test.emailservice.modules.users.resources.UserListRequest;
import com.test.emailservice.modules.users.resources.UserRequest;
import com.test.emailservice.modules.users.resources.UserResource;
import com.test.emailservice.modules.users.resources.UserUpdateRequest;
import com.test.emailservice.modules.users.specifications.UserSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;

    public Page<User> findAll(UserListRequest request, Integer page, Integer pageSize) {
        return repository.findAll(UserSpecification.filter(request), PageRequest.of(Pagination.getPageNumber(page),Pagination.getPageSize(pageSize)));
    }

    public UserResource create(UserRequest request) {
        try{
        return repository.save(request.toUser()).toUserResource(); }
        catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public UserResource getItem(long id) {
        User user = (User) repository.findById(id);
        return user.toUserResource();
    }

    public UserResource update(UserUpdateRequest request) {
        User user = (User) repository.findById(request.getId());
        if(user == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "User Not found", new HashMap());
        }
        user = request.toUser();
        return repository.save(user).toUserResource();
    }

    public void destroy(long id) {
         repository.deleteById(id);
    }
}
