package com.example.SpringBootRecordStore;

import com.example.SpringBootRecordStore.Controller.Record;
import com.example.SpringBootRecordStore.Controller.RecordController;
import com.example.SpringBootRecordStore.Service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringBootRecordStoreApplicationTests {

	@Autowired
	RecordController con;

	@Test
	void contextLoads() {
	}

	@Test
	public void checkBuildIDLogic()
	{
		RecordService rec =new RecordService();
		String id = rec.buildId("ZOLK", 32);
		assertEquals(id, "OLDZOLK32");
	}

	@Test
	public void addRecordTest()
	{
		// mock
		ResponseEntity response =con.addRecordImplementation(buildRecord());
	}

	public Record buildRecord()
	{
		Record rec = new Record();
		rec.setAisle(322);
		rec.setRecord_name("Folklore");
		rec.setIsmn("folk");
		rec.setArtist("Taylor Swift");
		rec.setId("folk322");
		return rec;
	}

}
