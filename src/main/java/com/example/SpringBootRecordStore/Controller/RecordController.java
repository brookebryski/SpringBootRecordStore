package com.example.SpringBootRecordStore.Controller;

import com.example.SpringBootRecordStore.Repository.RecordRepository;
import com.example.SpringBootRecordStore.Service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordController {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordService recordService;

    @PostMapping("/addRecord")
    public ResponseEntity<AddResponse> addRecordImplementation(@RequestBody Record record)
}
