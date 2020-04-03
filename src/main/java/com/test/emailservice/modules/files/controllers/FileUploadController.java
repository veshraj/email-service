package com.test.emailservice.modules.files.controllers;

import com.test.emailservice.core.responses.BaseResponse;
import com.test.emailservice.modules.files.resources.ImageResonse;
import com.test.emailservice.modules.files.services.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileUploadController {
    @Autowired
    StorageService storageService;

    @Operation(summary = "Upload file", tags = {"Files"})
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<ImageResonse>> handleImageUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        return new BaseResponse<ImageResonse>("Image uploaded successfully!!", HttpStatus.ACCEPTED, storageService.store(file)).getResponse();
    }

}
