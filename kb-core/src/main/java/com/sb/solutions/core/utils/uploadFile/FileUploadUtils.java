package com.sb.solutions.core.utils.uploadFile;

import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import org.apache.maven.shared.utils.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    private static final int MAX_FILE_SIZE = 2000000;
    private static String url;

    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type) {
        FilePath filePath = new FilePath();
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            if (type.equals("profile")) {
                url = filePath.getOSPath() + UploadDir.userProfile;
            } else if (type.equals("signature")) {
                url = filePath.getOSPath() + UploadDir.userSignature;
            } else {
                return new RestResponseDto().failureModel("wrong file type");
            }
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                new File(url).mkdirs();
            }
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);
            String imagePath = url + strDate + multipartFile.getOriginalFilename();
            path = Paths.get(imagePath);
            Files.write(path, bytes);
            System.out.println(imagePath);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            log.error("Error wile writing file", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }

    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type, int id, String name, String documentName) {
        FilePath filePath = new FilePath();
        String returnImagePath = null;
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("No image is selected");
        } else if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return new RestResponseDto().failureModel("File Size Exceeds the maximum size");
        }

        try {
            byte[] bytes = multipartFile.getBytes();

            url = filePath.getOSPath() + UploadDir.initialDocument + "customer_" + id + "/" + type + "/";
            String returnUrl = UploadDir.initialDocument + "customer_" + id + "/" + type + "/";

            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                new File(url).mkdirs();
            }
            String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename()).toLowerCase();
            String imagePath = url + name + "_" + documentName + "." + fileExtension;
            returnImagePath = returnUrl + name + "_" + documentName + "." + fileExtension;
            path = Paths.get(imagePath);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(returnImagePath);
        } catch (IOException e) {
            log.error("Error while saving file", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }
    public static ResponseEntity<?> uploadAccountOpeningFile(MultipartFile multipartFile, String branch, String type, String name) {
        FilePath filePath = new FilePath();
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        } else if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return new RestResponseDto().failureModel("Image Size more than 400kb");
        }
        if (FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("jpg") || FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("png")) {
            //return new RestResponseDto().failureModel("Invalid file format");
        } else {
            return new RestResponseDto().failureModel("Invalid file format");
        }
        try {
            byte[] bytes = multipartFile.getBytes();
            url = UploadDir.accountRequest + branch + "/";
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                new File(url).mkdirs();
            }
            String imagePath;
            if (type.equals("citizen")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_citizen.jpg";
            } else if (type.equals("passport")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_passport.jpg";
            } else if (type.equals("id")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_id.jpg";
            } else {
                return new RestResponseDto().failureModel("wrong file type");
            }
            path = Paths.get(imagePath);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Fail");
        }
    }
}
