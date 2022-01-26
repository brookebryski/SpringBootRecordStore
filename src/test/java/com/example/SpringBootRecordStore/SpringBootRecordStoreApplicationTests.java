package com.example.SpringBootRecordStore;

import com.example.SpringBootRecordStore.Controller.AddResponse;
import com.example.SpringBootRecordStore.Controller.Record;
import com.example.SpringBootRecordStore.Controller.RecordController;
import com.example.SpringBootRecordStore.Repository.RecordRepository;
import com.example.SpringBootRecordStore.Service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpringBootRecordStoreApplicationTests {

	@Autowired
	RecordController con;
	@MockBean
	RecordRepository repository;
	@MockBean
	RecordService recordService;

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
		Record rec = buildRecord();
		when(recordService.buildId(rec.getIsmn(), rec.getAisle())).thenReturn(rec.getId());
		when(recordService.checkRecordAlreadyExists(rec.getId())).thenReturn(false);

		ResponseEntity response =con.addRecordImplementation(buildRecord());
		System.out.println(response.getStatusCode());
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		AddResponse ad= (AddResponse) response.getBody();
		ad.getId();
		assertEquals(rec.getId(), ad.getId());
		assertEquals("Success Record is Added", ad.getMsg());
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
