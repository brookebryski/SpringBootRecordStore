package com.example.SpringBootRecordStore;

import com.example.SpringBootRecordStore.Controller.AddResponse;
import com.example.SpringBootRecordStore.Controller.Record;
import com.example.SpringBootRecordStore.Controller.RecordController;
import com.example.SpringBootRecordStore.Repository.RecordRepository;
import com.example.SpringBootRecordStore.Service.RecordService;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


// use mockito for external dependencies
// mockito is used to mock your methods, but mockmvc is used to mock your service calls
// mockmvc can only be used on controller methods


@SpringBootTest
@AutoConfigureMockMvc

class SpringBootRestServiceApplicationTests {

	@Autowired
	RecordController con;
	@MockBean
	RecordRepository repository;
	@MockBean
	RecordService recordService;
	@Autowired
	private MockMvc mockMvc;



	@Test
	void contextLoads() {

	}

	@Test
	public void checkRecordIDLogic()
	{
		RecordService rec =new RecordService();
		String id = rec.buildId("WALL", 24);
		assertEquals(id,"OLDZWALL24");
		String id1 = rec.buildId("TANG", 24);
		assertEquals(id1,"TANG24");

	}

	@Test
	public void addRecordTest()
	{
		//mock

		Record rec = buildRecord();
		when(recordService.buildId(rec.getIsmn(),rec.getAisle())).thenReturn(rec.getId());
		when(recordService.checkRecordAlreadyExists(rec.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(rec);
		ResponseEntity response	=con.addRecordImplementation(buildRecord());//step
		System.out.println(response.getStatusCode());
		assertEquals(response.getStatusCode(),HttpStatus.CREATED);
		AddResponse ad= (AddResponse) response.getBody();
		ad.getId();
		assertEquals(rec.getId(),ad.getId());
		assertEquals("Success Record is Added",ad.getMsg());

		//call Mock service from code

	}

	@Test
	public void addRecordControllerTest() throws Exception
	{
		Record rec = buildRecord();
		ObjectMapper map =new ObjectMapper();
		String jsonString = map.writeValueAsString(rec);


		when(recordService.buildId(rec.getIsmn(),rec.getAisle())).thenReturn(rec.getId());
		when(recordService.checkRecordAlreadyExists(rec.getId())).thenReturn(false);
		when(repository.save(any())).thenReturn(rec);

		this.mockMvc.perform(post("/addRecord").contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)).andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(rec.getId()));

	}

	@Test
	public void getRecordByArtistTest() throws Exception
	{
		List<Record> li =new ArrayList<Record>();
		li.add(buildRecord());
		li.add(buildRecord());
		when(repository.findAllByArtist(any())).thenReturn(li);
		this.mockMvc.perform(get("/getRecords/artist").param("artistname", "Taylor Swift"))
				.andDo(print()).andExpect(status().isOk()).
				andExpect(jsonPath("$.length()",is(2))).
				andExpect(jsonPath("$.[0].id").value("sfe3b"));

	}

	@Test
	public void updateRecordTest() throws Exception
	{
		Record rec =buildRecord();
		ObjectMapper map =new ObjectMapper();
		String jsonString = map.writeValueAsString(updateRecord());
		when(recordService.getRecordById(any())).thenReturn(buildRecord());
		this.mockMvc.perform(put("/updateRecord/"+rec.getId()).contentType(MediaType.APPLICATION_JSON)
						.content(jsonString)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("{\"record_name\":\"Evermore\",\"id\":\"ever322\",\"ismn\":\"ever\",\"aisle\":322,\"author\":\"Swift\"}"));

	}

	@Test
	public void deleteRecordControllerTest() throws Exception
	{
		when(recordService.getRecordById(any())).thenReturn(buildRecord());
		doNothing().when(repository).delete(buildRecord());
		this.mockMvc.perform(delete("/deleteRecord").contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\" : \"ever322\"}")).andDo(print())
				.andExpect(status().isCreated()).andExpect(content().string("Record is deleted"));

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

	public Record updateRecord()
	{
		Record rec =new Record();
		rec.setAisle(322);
		rec.setRecord_name("Evermore");
		rec.setIsmn("ever");
		rec.setArtist("Taylor Swift");
		rec.setId("ever322");
		return rec;

	}

}
