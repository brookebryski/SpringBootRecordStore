package com.example.SpringBootRecordStore.Repository;

import com.example.SpringBootRecordStore.Controller.Record;

import java.util.List;

public interface RecordRepositoryCustom {
    List<Record> findAllByArtist(String artistName);
}
