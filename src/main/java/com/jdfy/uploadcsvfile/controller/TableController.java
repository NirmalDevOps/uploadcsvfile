package com.jdfy.uploadcsvfile.controller;

import com.jdfy.uploadcsvfile.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/table")
public class TableController {
    private static final Logger logger = LoggerFactory.getLogger(TableController.class);
    @Autowired
    private TableService service;

    @GetMapping("/create")
    public String createTable()  {
        return service.createTable();
    }

    @DeleteMapping("/delete")
    public String deleteTable() throws InterruptedException {
        return service.deleteTable();
    }
}
