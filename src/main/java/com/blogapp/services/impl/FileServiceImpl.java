package com.blogapp.services.impl;

import com.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Step1:- Getting filename
        String fileName = file.getOriginalFilename();

        //Step2:- Getting fullPath
        String filePath = path+ File.separator+fileName;

        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        //Step3:- Create folder if not created
        File newFile = new File(path);
        if(!newFile.exists()){
            newFile.mkdir();
        }

        //Step4 :- File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
      String fullPath =path+File.separator+fileName;
      InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
