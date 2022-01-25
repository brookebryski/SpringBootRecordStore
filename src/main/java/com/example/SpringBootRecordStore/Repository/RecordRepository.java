package com.example.SpringBootRecordStore.Repository;

import com.example.SpringBootRecordStore.Controller.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, String>, RecordRepositoryCustom {
}
