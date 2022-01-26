package com.example.SpringBootRecordStore.Service;

import com.example.SpringBootRecordStore.Controller.Record;
import com.example.SpringBootRecordStore.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Service is for business logic
// Do not write all of your code in controller
@Service
public class RecordService {

    @Autowired
    RecordRepository repository;

    public String buildId(String ismn, int aisle)
    {
        if(ismn.startsWith("Z"))
        {
            return "OLD"+ismn+aisle;
        }
        return ismn+aisle;
    }

    public boolean checkRecordAlreadyExists(String id)
    {
        Optional<Record> rec=repository.findById(id);
        if(rec.isPresent())
            return true;
        else
            return false;
    }

    public Record getRecordById(String id)
    {
        return repository.findById(id).get();
    }
}
