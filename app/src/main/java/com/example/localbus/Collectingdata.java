package com.example.localbus;


//data model for collecting the data for the bus

//private @ServerTimestamp Date timestamp;1
// this.timestamp = timestamp;1
//public Date getTimestamp() {
//        return timestamp;
//        }1
//
//public void setTimestamp(Date timestamp) {
//        this.timestamp = timestamp;
//        }

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.util.List;

public class Collectingdata {

    private String documentId;
    private @ServerTimestamp Date timestamp;
    List<String> bi;

    public Collectingdata() {
        //public no-arg constructor needed
    }

    public Collectingdata(List<String> bi) {
        this.timestamp = timestamp;
        this.bi = bi;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    @Exclude
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getBi() {
        return bi;
    }
}