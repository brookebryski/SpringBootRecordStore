package com.example.SpringBootRecordStore.Controller;

import com.example.SpringBootRecordStore.Repository.RecordRepository;
import com.example.SpringBootRecordStore.Service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordController {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordService recordService;
}
