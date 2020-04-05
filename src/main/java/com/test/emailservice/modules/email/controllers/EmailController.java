package com.test.emailservice.modules.email.controllers;

import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.responses.BaseResponse;
import com.test.emailservice.core.responses.PaginationResponse;
import com.test.emailservice.core.utils.BindingErrorUtil;
import com.test.emailservice.modules.email.presenters.VendorEmailPresenter;
import com.test.emailservice.modules.email.resources.EmailRequest;
import com.test.emailservice.modules.email.resources.VendorEmailResource;
import com.test.emailservice.modules.email.services.VendorEmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/vendor/emails")
public class EmailController {
    @Autowired
    VendorEmailService service;


    @Operation(summary = "Email list via Service Provider", tags = {"Emails/Service Provider"})
    @GetMapping("")
    public ResponseEntity<PaginationResponse<VendorEmailResource>> list(@RequestParam(required = false) String search,
                                                                        @RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer pageSize) {
        return new PaginationResponse("List of emails", HttpStatus.OK,service.findAll(search, page, pageSize)).transform(VendorEmailPresenter.class).getResponse();
    }


    @Operation(summary = "Send Email via Service Provider", tags = {"Emails/Service Provider"})
    @PostMapping("")
    public ResponseEntity<BaseResponse<VendorEmailResource>> sendEmail(@Valid @RequestBody EmailRequest request, BindingResult result) {
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Data", BindingErrorUtil.getBindingErrors(result));
        }
        VendorEmailResource resource = service.sendMail(request);
        if(resource == null) {
            throw  new CustomException(HttpStatus.FAILED_DEPENDENCY, "All server got down. Please try again later.", null);
        }
        return new BaseResponse("Email sent", HttpStatus.ACCEPTED, resource).getResponse();
    }
}
