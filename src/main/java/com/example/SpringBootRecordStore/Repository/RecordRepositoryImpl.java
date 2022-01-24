package com.example.SpringBootRecordStore.Repository;

import com.example.SpringBootRecordStore.Controller.Record;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RecordRepositoryImpl implements RecordRepositoryCustom {

    @Autowired
    RecordRepository repository;

    @Override
    public List<Record> findAllByArtist(String artistName) {
        List<Record> recordswithArtist = new ArrayList<>();
        List<Record> records = repository.findAll();
        for (Record item : records)
            if (item.getArtist().equalsIgnoreCase("artistName")) {
                recordswithArtist.add(item);
            }
        return recordswithArtist;
    }
}
