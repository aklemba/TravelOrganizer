package com.example.ekipaapp.dao;

import com.example.ekipaapp.entity.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ekipaapp.firebase.FirebaseDatabaseConsts.EVENT_LIST;

public class FirebaseEventDao {
    private final static FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();

    public DatabaseReference getAllEvents() {
        return FIREBASE_DATABASE.getReference(EVENT_LIST);
    }

    public void addEvent(Event event) {
        FIREBASE_DATABASE.getReference(EVENT_LIST).push().setValue(event);
    }
}
