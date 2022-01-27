package com.example.SpringBootRecordStore;

import com.example.SpringBootRecordStore.Controller.Record;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.Assert;

    @SpringBootTest
    public class TestsIT {
        //mvn test
        //TestRestTemplate Rest Assured
        @Test
        public void getArtistNameRecordsTest() throws JSONException
        {
            String expected= "[\r\n" +
                    "    {\r\n" +
                    "        \"record_name\": \"Evermore\",\r\n" +
                    "        \"id\": \"ever4\",\r\n" +
                    "        \"isbn\": \"ever\",\r\n" +
                    "        \"aisle\": 4,\r\n" +
                    "        \"author\": \"Taylor\"\r\n" +
                    "    },\r\n" +
                    "    {\r\n" +
                    "        \"record_name\": \"Red\",\r\n" +
                    "        \"id\": \"red22\",\r\n" +
                    "        \"isbn\": \"red\",\r\n" +
                    "        \"aisle\": 22,\r\n" +
                    "        \"author\": \"Taylor\"\r\n" +
                    "    }\r\n" +
                    "]";
            TestRestTemplate restTemplate =new TestRestTemplate();
            ResponseEntity<String> response =restTemplate.getForEntity("http://localhost:8080/getRecords/artist?artistname=Taylor", String.class);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            JSONAssert.assertEquals(expected, response.getBody(), false);


        }

        @Test
        public void addRecordIntegrationTest()
        {
            TestRestTemplate restTemplate =new TestRestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Record> request = new HttpEntity<Record>(buildRecord(),headers);
            ResponseEntity<String>	response =	restTemplate.postForEntity("http://localhost:8080/addRecord", request, String.class);
            Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
            Assert.assertEquals(buildRecord().getId(),response.getHeaders().get("unique").get(0));

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

