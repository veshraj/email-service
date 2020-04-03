package com.test.emailservice.modules.users.controllers;


import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.responses.BaseResponse;
import com.test.emailservice.core.responses.PaginationResponse;
import com.test.emailservice.core.utils.BindingErrorUtil;
import com.test.emailservice.modules.auth.entities.UserToken;
import com.test.emailservice.modules.auth.services.UserTokenService;
import com.test.emailservice.modules.auth.utils.JWTUtil;
import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.presenters.UserPresenter;
import com.test.emailservice.modules.users.resources.UserListRequest;
import com.test.emailservice.modules.users.resources.UserRequest;
import com.test.emailservice.modules.users.resources.UserResource;
import com.test.emailservice.modules.users.resources.UserUpdateRequest;
import com.test.emailservice.modules.users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    private UserRequest request;

    @Autowired
    JWTUtil jwtUtil;


    @Operation(summary = "Get List of users", tags = {"Users"})
    @GetMapping(path = "")
    public ResponseEntity<PaginationResponse<List<User>>> list(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false)String email,
                                                         @RequestParam(required = false) String mobileNumber,
                                                         @RequestParam(required = false) String website,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer pageSize
                                                         ) {
        UserListRequest request = UserListRequest.builder()
                                          .name(name)
                                          .email(email)
                                          .mobileNumber(mobileNumber)
                                          .website(website)
                                          .build();

        return new PaginationResponse("List of users", HttpStatus.OK, service.findAll(request, page, pageSize))
                       .transform(UserPresenter.class)
                       .getResponse();
    }

    @Operation(summary = "Add an user", tags = {"Users"})
    @PostMapping("")
    public ResponseEntity<BaseResponse<UserResource>> create(@Valid @RequestBody UserRequest userRequest, BindingResult result)
    {
        userRequest.validate(result);
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid data provided", BindingErrorUtil.getBindingErrors(result));
        }
        return new BaseResponse("User added sccessfully", HttpStatus.CREATED, service.create(userRequest)).getResponse();
    }

    @Operation(summary = "Register an user", tags = {"Users"})
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResource>> register(@Valid @RequestBody UserRequest userRequest, BindingResult result)
    {
        userRequest.validate(result);
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid data provided", BindingErrorUtil.getBindingErrors(result));
        }
        return new BaseResponse("User added sccessfully", HttpStatus.CREATED, service.create(userRequest)).getResponse();
    }

    @Operation(summary = "Get a user details", tags = {"Users"})
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResource>> get(@PathVariable() long id) {
        return  new BaseResponse("User details", HttpStatus.OK, service.getItem(id)).getResponse();

    }

    @Operation(summary = "Add a user", tags = {"Users"})
    @PutMapping("")
    public ResponseEntity<BaseResponse<UserResource>> update(@Valid @RequestBody UserUpdateRequest userRequest, BindingResult result)
    {
        userRequest.validate(result);
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid data provided", BindingErrorUtil.getBindingErrors(result));
        }
        return new BaseResponse("User updated sccessfully", HttpStatus.ACCEPTED, service.update(userRequest)).getResponse();
    }


}
