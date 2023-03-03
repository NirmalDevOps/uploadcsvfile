package com.jdfy.uploadcsvfile.controller;

import com.jdfy.uploadcsvfile.service.CsvFileSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/splittor")
public class SplitCsvFileController {

    private static final Logger logger = LoggerFactory.getLogger(SplitCsvFileController.class);

    @Autowired
    CsvFileSplit csvFileSplit;
    @GetMapping("/splitcsvfile")
    public ResponseEntity<String> splitFile() throws IOException {
        if(csvFileSplit.splitFile())
            return new ResponseEntity<>("Successfully splited", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to split,Please check with admin", HttpStatus.BAD_REQUEST);
    }
}
