package com.sb.solutions.core.utils.uploadFile;

import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
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

    public ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type, String cycle) {
        FilePath filePath = new FilePath();
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }

        try {
            byte[] bytes = multipartFile.getBytes();
            if (cycle.equals("initial")) {
                if (type.equals("Cititzenship")) {
                    url = filePath.getOSPath() + UploadDir.initialDocumentCitizenship;
                } else if (type.equals("Promissory Note")) {
                    url = filePath.getOSPath() + UploadDir.initialDocumentPromissoryNote;
                } else {
                    url = filePath.getOSPath() + UploadDir.initialDocumentPropertyOwner;
                }
            } else {
                if (type.equals("Cititzenship")) {
                    url = filePath.getOSPath() + UploadDir.renewalDocumentCitizenship;
                } else if (type.equals("Promissory Note")) {
                    url = filePath.getOSPath() + UploadDir.renewalDocumentPromissoryNote;
                } else {
                    url = filePath.getOSPath() + UploadDir.renewalDocumentPropertyOwner;
                }

            }
            Path path = Paths.get(url);
            if (!Files.exists(path)) {
                new File(url).mkdirs();
            }
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            String imagePath = url + strDate + type;
            path = Paths.get(imagePath);
            Files.write(path, bytes);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Fail");
        }
    }
}
