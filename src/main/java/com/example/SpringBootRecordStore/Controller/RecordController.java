package com.example.SpringBootRecordStore.Controller;

import com.example.SpringBootRecordStore.Repository.RecordRepository;
import com.example.SpringBootRecordStore.Service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RecordController {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordService recordService;

    @PostMapping("/addRecord")
    public ResponseEntity<AddResponse> addRecordImplementation(@RequestBody Record record)
    {
        String id = recordService.buildId(record.getIsmn(), record.getAisle());
        AddResponse ad = new AddResponse();
        if(!recordService.checkRecordAlreadyExists(id))
        {
            record.setId(id);
            repository.save(record);
            HttpHeaders headers = new HttpHeaders();
            headers.add("unique", id);
            ad.setMsg("Success record is added");
            ad.setId(id);
            // return ad
            return new ResponseEntity<AddResponse>(ad, headers, HttpStatus.CREATED);
        }
        // add record details into database
        else
        {
            ad.setMsg("Record already exists.");
            ad.setId(id);
            return new ResponseEntity<AddResponse>(ad, HttpStatus.ACCEPTED);
        }
        // write the code to tell record already exists
    }

    @GetMapping("/getRecords/{id}")
    public Record getRecordById(@PathVariable(value="id")String id)
    {
        try {
            Record rec = repository.findById(id).get();
            return rec;
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getRecords/artist")
    public void getRecordsByArtistName(@RequestParam(value="artistname")String artistname)
    {
        repository.
    }
}
