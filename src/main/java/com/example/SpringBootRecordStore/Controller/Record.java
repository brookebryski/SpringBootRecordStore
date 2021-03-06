package com.example.SpringBootRecordStore.Controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Data3")
public class Record {
    @Column(name="record_name")
    private String record_name;
    @Id
    @Column(name="id")
    private String id;
    @Column(name="ismn")
    private String ismn;
    @Column(name="aisle")
    private int aisle;
    @Column(name="artist")
    private String artist;

    public String getRecord_name() {
        return record_name;
    }

    public void setRecord_name(String record_name) {
        this.record_name = record_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsmn() {
        return ismn;
    }

    public void setIsmn(String ismn) { this.ismn = ismn;}

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
