package com.test.emailservice.modules.email.controllers;


import java.util.List;
import com.test.emailservice.core.exceptions.CustomException;
import com.test.emailservice.core.responses.BaseResponse;
import com.test.emailservice.core.responses.PaginationResponse;
import com.test.emailservice.core.utils.BindingErrorUtil;
import com.test.emailservice.modules.email.entities.SmtpEmail;
import com.test.emailservice.modules.email.presenters.SmtpEmailPresenter;
import com.test.emailservice.modules.email.presenters.VendorEmailPresenter;
import com.test.emailservice.modules.email.resources.EmailRequest;
import com.test.emailservice.modules.email.resources.SmtpEmailResource;
import com.test.emailservice.modules.email.resources.VendorEmailResource;
import com.test.emailservice.modules.email.services.SmtpEmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/mail/smtps")
public class SmtpEmailController {
    @Autowired
    SmtpEmailService service;


    @Operation(summary = "Email list via Service Provider", tags = {"Emails/SMTP"})
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<VendorEmailResource>>> list(@RequestParam(required = false) Integer page,
                                                                              @RequestParam(required = false) Integer pageSize) {
        return new BaseResponse("List of emails", HttpStatus.OK,service.findAll()).getResponse();
    }


    @Operation(summary = "Email list via SMTP", tags = {"Emails/SMTP"})
    @PostMapping("")
    public ResponseEntity<BaseResponse<SmtpEmailResource>> sendMail(@Valid @RequestBody EmailRequest request, BindingResult result) {
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid data provided", BindingErrorUtil.getBindingErrors(result));
        }
        if(result.hasErrors()) {
            throw new CustomException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Data", BindingErrorUtil.getBindingErrors(result));
        }
        SmtpEmailResource resource = service.sendMail(request);
        if(resource == null) {
            throw  new CustomException(HttpStatus.FAILED_DEPENDENCY, "All server got down. Please try again later.", null);
        }
        return new BaseResponse("Email sent using SMTP", HttpStatus.ACCEPTED, resource).getResponse();

    }
}
