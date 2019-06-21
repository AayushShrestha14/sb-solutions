package com.sb.solutions.core.utils.uploadFile;

import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import org.apache.maven.shared.utils.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    private static final int MAX_FILE_SIZE = 2000000;
    private static String url;

    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type) {
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            if (type.equals("profile")) {
                url = UploadDir.userProfile;
            } else if (type.equals("signature")) {
                url = UploadDir.userSignature;
            } else {
                return new RestResponseDto().failureModel("wrong file type");
            }
            Path path = Paths.get(FilePath.getOSPath() + url);
            if (!Files.exists(path)) {
                new File(FilePath.getOSPath() + url).mkdirs();
            }
            String imagePath = url + System.currentTimeMillis() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
            path = Paths.get(FilePath.getOSPath() + imagePath);
            Files.write(path, bytes);
            System.out.println(imagePath);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            log.error("Error wile writing file", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }


    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type, int id, String name, String documentName) {
        String url = null;
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("No image is selected");
        } else if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return new RestResponseDto().failureModel("File Size Exceeds the maximum size");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            FilePath filePath = new FilePath();
            url = FilePath.getOSPath() + UploadDir.initialDocument + "customer_" + id + "/" + type + "/";
            String returnUrl = UploadDir.initialDocument + "customer_" + id + "/" + type + "/";
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                new File(url).mkdirs();
            }
            String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
            url = url + name + "_" + documentName + "." + fileExtension;
            returnUrl = returnUrl+ name + "_" + documentName + "." + fileExtension;
            path = Paths.get(url);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(returnUrl);
        } catch (IOException e) {
            log.error("Error while saving file", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }
}
