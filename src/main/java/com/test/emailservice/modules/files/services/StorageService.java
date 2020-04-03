package com.test.emailservice.modules.files.services;

import com.test.emailservice.core.utils.FileUploaderUtil;
import com.test.emailservice.modules.files.resources.ImageResonse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



@Service
public class StorageService {
    ImageResonse imageResonse;
    public ImageResonse store(MultipartFile file) throws IOException {
        if(imageResonse == null) {
            imageResonse = new ImageResonse();
        }
        imageResonse.setUrl(FileUploaderUtil.uploadImage("/", file, "emailservice-"));
        return imageResonse;
    }
}
