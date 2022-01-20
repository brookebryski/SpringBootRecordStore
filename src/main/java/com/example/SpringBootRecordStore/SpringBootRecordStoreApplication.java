package com.example.SpringBootRecordStore;

import com.example.SpringBootRecordStore.Controller.Record;
import com.example.SpringBootRecordStore.Repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringBootRecordStoreApplication implements CommandLineRunner {

	@Autowired
	RecordRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRecordStoreApplication.class, args);
	}

	@Override
	public void run(String [] args)
	{
		Record record = repository.findById("rumors1").get();
		System.out.println(record.getArtist());
		Record en = new Record();
		en.setRecord_name("The Dark Side of the Moon");
		en.setArtist("Pink Floyd");
		en.setId("moon1");
		//
		List<Record> allrecords = repository.findAll();
		//
		for (Record item : allrecords)
		{
			System.out.println(item.getRecord_name());
		}
		repository.delete(en);
	}

}
