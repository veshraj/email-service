package com.test.emailservice.modules.email.controllers;

import java.util.List;

import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.responses.BaseResponse;
import com.test.emailservice.core.utils.BindingErrorUtil;
import com.test.emailservice.modules.email.resources.HostRequest;
import com.test.emailservice.modules.email.resources.HostResource;
import com.test.emailservice.modules.email.services.HostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/smtp-hosts")
public class SmtpHostController {
    @Autowired
    HostService service;

    @Operation(summary = "List of SMTP host", tags = {"SMTP Host"})
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<HostResource>>> list(@RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer pageSize) {

        return new BaseResponse("List of host", HttpStatus.OK, service.findAll()).getResponse();
//        return  new PaginationResponse("List of smtp hosts", HttpStatus.OK, service.findAll(page, pageSize)).getResponse();
    }

    @Operation(summary = "Add a SMTP host", tags = {"SMTP Host"})
    @PostMapping("")
    public ResponseEntity<BaseResponse<HostResource>> create(@Valid @RequestBody HostRequest request, BindingResult result) {

        request.validate(result);
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid data", BindingErrorUtil.getBindingErrors(result));
        }
        if(service == null) {
            System.out.println("we are upto here");
        }
        return new BaseResponse("Host created", HttpStatus.CREATED, service.create(request)).getResponse();
    }

    @Operation(summary = "Get detials of SMTP host", tags = {"SMTP Host"})
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<HostResource>> get(@PathVariable() Integer id) {
        if(id == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Host not found", null);
        }
        return new BaseResponse("SMTP host Details", HttpStatus.OK, service.getItem(id)).getResponse();
    }

    @Operation(summary = "Update detials of SMTP host", tags = {"SMTP Host"})
    @PutMapping("")
    public ResponseEntity<BaseResponse<HostResource>> update(@Valid @RequestBody HostRequest request, BindingResult result) {
        request.validate(result);
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Host not found", BindingErrorUtil.getBindingErrors(result));
        }
        return new BaseResponse("SMTP host updated", HttpStatus.OK, service.update(request)).getResponse();
    }


}
