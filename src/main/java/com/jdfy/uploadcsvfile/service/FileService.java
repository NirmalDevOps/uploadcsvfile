package com.jdfy.uploadcsvfile.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    String tableName="applicant-track-record";
    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    public boolean uploadFile(MultipartFile file) {
        logger.info(file+" uploading started");
        try {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
            logger.info(file + " uploaded successfully");
            return true;
        }
        catch (Exception e){
            logger.info(file + " uploading failed");
            return false;
        }
    }
    public boolean deleteFile(String fileName) {
        logger.info(fileName+" is starting to delete");
        try {
            s3Client.deleteObject(bucketName, fileName);
            logger.info(fileName + " is deleted successfully");
            return true;
        }
        catch (Exception e){
            logger.info(fileName + " not deleted, please check with admin");
            return false;
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
