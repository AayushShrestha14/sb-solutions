package com.sb.solutions.core.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.shared.utils.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;

public class FileUploadUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUploadUtils.class);

    private static final int MAX_FILE_SIZE = 10000000;
    private static String url;

    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type) {
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }

        try {
            final byte[] bytes = multipartFile.getBytes();

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

            final String imagePath = url + System.currentTimeMillis() + "." + FileUtils
                .getExtension(multipartFile.getOriginalFilename()).toLowerCase();

            path = Paths.get(FilePath.getOSPath() + imagePath);

            Files.write(path, bytes);

            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            log.error("Error wile writing file", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }

    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type,
        String name, String citizenNumber, String documentName) {
        String url = null;
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("No image is selected");
        } else if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return new RestResponseDto().failureModel("File Size Exceeds the maximum size");
        }

        try {
            final byte[] bytes = multipartFile.getBytes();
            final FilePath filePath = new FilePath();

            url = UploadDir.initialDocument + name + "_" + citizenNumber + "/" + type
                + "/";
            String returnUrl =
                UploadDir.initialDocument + name + "_" + citizenNumber + "/" + type + "/";
            Path path = Paths.get(FilePath.getOSPath() + url);
            if (!Files.exists(path)) {
                new File(FilePath.getOSPath() + url).mkdirs();
            }
            String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename())
                .toLowerCase();
            url = url + name + "_" + documentName + "." + fileExtension;
            returnUrl = returnUrl + name + "_" + documentName + "." + fileExtension;
            path = Paths.get(FilePath.getOSPath() + url);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(returnUrl);
        } catch (IOException e) {
            log.error("Error while saving file", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }

    public static ResponseEntity<?> uploadAccountOpeningFile(MultipartFile multipartFile,
        String branch, String type, String name) {
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        } else if (multipartFile.getSize() > MAX_FILE_SIZE) {
            return new RestResponseDto().failureModel("Image Size more than 400kb");
        } else if (!FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("jpg")
            && !FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("png")) {
            return new RestResponseDto().failureModel("Invalid file format");
        }
        try {
            final byte[] bytes = multipartFile.getBytes();
            url = UploadDir.accountRequest + branch + "/";
            Path path = Paths.get(FilePath.getOSPath() + url);
            if (!Files.exists(path)) {
                new File(FilePath.getOSPath() + url).mkdirs();
            }
            String imagePath;
            if (type.equals("citizen")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_citizen." + FileUtils
                    .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                ;
            } else if (type.equals("passport")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_passport." + FileUtils
                    .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                ;
            } else if (type.equals("id")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_id." + FileUtils
                    .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                ;
            } else if (type.equals("photo")) {
                imagePath = url + name + "_" + System.currentTimeMillis() + "_photo." + FileUtils
                    .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                ;
            } else {
                return new RestResponseDto().failureModel("wrong file type");
            }
            path = Paths.get(FilePath.getOSPath() + imagePath);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Fail");
        }
    }


}

