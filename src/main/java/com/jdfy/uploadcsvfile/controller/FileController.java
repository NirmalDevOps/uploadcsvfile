package com.jdfy.uploadcsvfile.controller;

import com.jdfy.uploadcsvfile.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {

        if(service.uploadFile(file))
            return new ResponseEntity<>("File successfully uploaded!!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to upload", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        if(service.deleteFile(fileName))
            return new ResponseEntity<>("File successfully Deleted", HttpStatus.OK);
        else
            return new ResponseEntity<>("File Not Deleted, Please connect wtih admin", HttpStatus.BAD_REQUEST);
    }
}
