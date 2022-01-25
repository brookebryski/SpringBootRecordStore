package com.example.SpringBootRecordStore.Controller;

import com.example.SpringBootRecordStore.Repository.RecordRepository;
import com.example.SpringBootRecordStore.Service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RecordController {

    @Autowired
    RecordRepository repository;

    @Autowired
    RecordService recordService;

    private static final Logger Logger= LoggerFactory.getLogger(RecordController.class);

    @PostMapping("/addRecord")
    public ResponseEntity<AddResponse> addRecordImplementation(@RequestBody Record record)
    {
        String id = recordService.buildId(record.getIsmn(), record.getAisle());
        AddResponse ad = new AddResponse();
        if(!recordService.checkRecordAlreadyExists(id))
        {
            Logger.info("Record does not exist, creating record");
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
            Logger.info("Record exists, skipping creation");
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
    public List<Record> getRecordsByArtistName(@RequestParam(value="artistname")String artistname)
    {
        return repository.findAllByArtist(artistname);
    }

    @PutMapping("/updateRecord/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable(value="id")String id, @RequestBody Record record)
    {
        Record existingRecord = repository.findById(id).get();

        existingRecord.setAisle(record.getAisle());
        existingRecord.setArtist(record.getArtist());
        existingRecord.setRecord_name(record.getRecord_name());
        repository.save(existingRecord);
        return new ResponseEntity<Record>(existingRecord, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRecord")
    public ResponseEntity<String> deleteRecordById(@RequestBody Record record)
    {
        Record recdelete =repository.findById(record.getId()).get();
        repository.delete(recdelete);
        Logger.info("Record is deleted");
        return new ResponseEntity<>("Record is deleted", HttpStatus.CREATED);
    }
}
