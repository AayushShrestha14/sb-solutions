package com.sb.solutions.core.utils.uploadFile;

import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import org.apache.maven.shared.utils.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.util.Date;

@Component
public class UploadFile {
    String url;


    public ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type) {
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
            e.printStackTrace();
            return new RestResponseDto().failureModel("Fail");
        }
    }

    public ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type, String name, String documentName) {
        FilePath filePath = new FilePath();
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("No image is selected");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            if (type.equals("initial")) {
                if (documentName.equals("Citizenship")) {
                    url = filePath.getOSPath() + UploadDir.initialDocumentCitizenship;
                } else if (documentName.equals("Promissory Note")) {
                    url = filePath.getOSPath() + UploadDir.initialDocumentPromissoryNote;
                } else {
                    url = filePath.getOSPath() + UploadDir.initialDocumentPropertyOwner;
                }
            } else {
                if (documentName.equals("Citizenship")) {
                    url = filePath.getOSPath() + UploadDir.renewalDocumentCitizenship;
                } else if (documentName.equals("Promissory Note")) {
                    url = filePath.getOSPath() + UploadDir.renewalDocumentPromissoryNote;
                } else {
                    url = filePath.getOSPath() + UploadDir.renewalDocumentPropertyOwner;
                }

            }
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                new File(url).mkdirs();
            }

            String fileExtension = FileUtils.getExtension(multipartFile.getOriginalFilename());
            if (fileExtension.equals("jpg")) {
                String imagePath = url + name + "_" + documentName + ".jpg";
                path = Paths.get(imagePath);
                Files.write(path, bytes);
                return new RestResponseDto().successModel(imagePath);
            } else {
                String imagePath = url + name + " " + documentName + ".png";
                path = Paths.get(imagePath);
                Files.write(path, bytes);
                return new RestResponseDto().successModel(imagePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Fail");
        }
    }
}
