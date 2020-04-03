package com.test.emailservice.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploaderUtil {

    private static final String ROOT_PATH = "src/main/resources/uploads/";

    /**
     * This method is used to upload file to the defined path
     *
     * @param uploadPath    is used to define the path where the file is to be uploaded.
     * @param multipartFile is the file sent from the view
     * @param fileName      Filename that is to be saved as with current timestamp
     * @return the file name.
     */
    public static String uploadImage(String uploadPath, MultipartFile multipartFile, String fileName) throws IOException {

        String name = "";
        try {
            name = fileName.toLowerCase() + System.nanoTime() + "."
                           + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

            // Check if the file's name contains invalid characters

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get(ROOT_PATH + uploadPath)
                                          .toAbsolutePath().normalize().resolve(name);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }

        return name;
    }
}
