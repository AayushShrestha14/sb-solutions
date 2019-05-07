package com.sb.solutions.core.utils.uploadFile;

import com.sb.solutions.core.constant.FilePath;
import com.sb.solutions.core.constant.UploadDir;
import com.sb.solutions.core.dto.RestResponseDto;
import lombok.AllArgsConstructor;
import org.apache.maven.shared.utils.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class UploadFile {
    String url;

    public ResponseEntity<?> uploadAccountOpeningFile(MultipartFile multipartFile,String branch, String type, String name){
        FilePath filePath = new FilePath();
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }
        else if(multipartFile.getSize()>409600){
            return new RestResponseDto().failureModel("Image Size more than 400kb");
        }
        if(!FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("jpg") || !FileUtils.getExtension(multipartFile.getOriginalFilename()).equals("png")){
            return new RestResponseDto().failureModel("Invalid file format");
        }
        try {
            byte[] bytes = multipartFile.getBytes();
            url = filePath.getOSPath() + UploadDir.accountRequest+branch+"/";

            Path path = Paths.get(url);
            if(!Files.exists(path)) {
                new File(url).mkdirs();
            }
            String imagePath;
            if(type.equals("citizen")){
                imagePath = url + name +"_"+System.currentTimeMillis()+"_citizen.jpg";
            }
            else if(type.equals("passport")){
                imagePath = url + name +"_"+System.currentTimeMillis()+"_passport.jpg";
            }
            else if(type.equals("id")){
                imagePath = url + name + "_"+System.currentTimeMillis()+"_id.jpg";
            }
            else{
                return new RestResponseDto().failureModel("wrong file type");
            }
            path = Paths.get(imagePath);
            Files.write(path, bytes);
            System.out.println(imagePath);
            return new RestResponseDto().successModel(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new RestResponseDto().failureModel("Fail");
        }


    }

    public ResponseEntity<?> uploadFile(MultipartFile multipartFile, String type) {
        FilePath filePath = new FilePath();
        if (multipartFile.isEmpty()) {
            return new RestResponseDto().failureModel("Select Signature Image");
        }
        try {
            byte[] bytes = multipartFile.getBytes();
            if(type.equals("profile")){
                url = filePath.getOSPath() + UploadDir.userProfile;
            }
            else if(type.equals("signature")){
                url = filePath.getOSPath() + UploadDir.userSignature;
            }
            else{
                return new RestResponseDto().failureModel("wrong file type");
            }
            Path path = Paths.get(url);
            if(!Files.exists(path)) {
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
}
