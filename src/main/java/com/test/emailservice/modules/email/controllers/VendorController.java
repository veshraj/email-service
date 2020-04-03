package com.test.emailservice.modules.email.controllers;

import com.test.emailservice.core.responses.PaginationResponse;
import com.test.emailservice.modules.email.presenters.VendorPresenter;
import com.test.emailservice.modules.email.resources.VendorResource;
import com.test.emailservice.modules.email.services.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    @Autowired
    VendorService service;

    @Operation(summary = "List of Email Service Provider", tags = {"Emails Service Provider"})
    @GetMapping("")
    public ResponseEntity<PaginationResponse<VendorResource>> list(@RequestParam(required = false) Integer page,
                                                                   @RequestParam(required = false) Integer pageSize) {
        return new PaginationResponse("List of emails", HttpStatus.OK,service.findAll(page, pageSize)).transform(VendorPresenter.class).getResponse();
    }

}
