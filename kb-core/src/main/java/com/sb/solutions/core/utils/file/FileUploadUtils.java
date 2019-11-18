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
import com.sb.solutions.core.utils.PathBuilder;

public class FileUploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

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
            logger.error("Error wile writing file {}", e);
            return new RestResponseDto().failureModel("Fail");
        }

    }

    /**
     * File is uploaded  and renamed that of documenttype
     */
    public static ResponseEntity<?> uploadFile(MultipartFile multipartFile, String url,
        String documentName) {

        try {
            final byte[] bytes = multipartFile.getBytes();

            Path path = Paths.get(FilePath.getOSPath() + url);
            if (!Files.exists(path)) {
                new File(FilePath.getOSPath() + url).mkdirs();
            }
            // check if file under same name exits, if exists delete it
            File dir = path.toFile();
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File f : files) {
                    // remove file if exists
                    if (f.getName().toLowerCase().contains(documentName.toLowerCase())) {
                        try {
                            f.delete();
                        } catch (Exception e) {
                            logger.error("Failed to delete file {} {}", f, e);
                        }
                    }
                }

            }
            String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename())
                .toLowerCase();
            url = url + documentName.toLowerCase() + "." + fileExtension;

            path = Paths.get(FilePath.getOSPath() + url);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(url);
        } catch (IOException e) {
            logger.error("Error while saving file {}", e);
            return new RestResponseDto().failureModel("Fail");
        }
    }

    public static ResponseEntity<?> uploadAccountOpeningFile(MultipartFile multipartFile,
        String branch, String type, String name, String citizenship, String customerName) {
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        } else if (!FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("jpg")
            && !FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("png")) {
            return new RestResponseDto().failureModel("Invalid file format");
        }
        try {
            final byte[] bytes = multipartFile.getBytes();
            url = new PathBuilder(UploadDir.initialDocument)
                .withBranch(branch)
                .withCustomerName(customerName)
                .withCitizenship(citizenship)
                .isJsonPath(false)
                .buildAccountOpening();
            Path path = Paths.get(FilePath.getOSPath() + url);
            if (!Files.exists(path)) {
                new File(FilePath.getOSPath() + url).mkdirs();
            }
            String imagePath;
            switch (type) {
                case "citizen":
                    imagePath =
                        url + name + "_" + System.currentTimeMillis() + "_citizen." + FileUtils
                            .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                    break;
                case "passport":
                    imagePath =
                        url + name + "_" + System.currentTimeMillis() + "_passport." + FileUtils
                            .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                    break;
                case "voter":
                    imagePath =
                        url + name + "_" + System.currentTimeMillis() + "_voter." + FileUtils
                            .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                    break;
                case "license":
                    imagePath =
                        url + name + "_" + System.currentTimeMillis() + "_license." + FileUtils
                            .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                    break;
                case "id":
                    imagePath = url + name + "_" + System.currentTimeMillis() + "_id." + FileUtils
                        .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                    break;
                case "photo":
                    imagePath =
                        url + name + "_" + System.currentTimeMillis() + "_photo." + FileUtils
                            .getExtension(multipartFile.getOriginalFilename()).toLowerCase();
                    break;
                default:
                    return new RestResponseDto().failureModel("wrong file type");
            }
            path = Paths.get(FilePath.getOSPath() + imagePath);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            logger.error("Error uploading account opening file {}", e.getMessage());
            return new RestResponseDto().failureModel("Fail");
        }
    }




}

