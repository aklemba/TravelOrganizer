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

    public void insertEvent(Event event) {
        FIREBASE_DATABASE.getReference(EVENT_LIST).push().setValue(event);
    }

    public void removeAllEvents() {
        FIREBASE_DATABASE.getReference(EVENT_LIST).removeValue();
    }

    public DatabaseReference getEventByKey(String eventKey) {
        return FIREBASE_DATABASE.getReference(EVENT_LIST).child(eventKey);
    }

    public void updateEvent(String key, Event event) {
        FIREBASE_DATABASE.getReference(EVENT_LIST).child(key).setValue(event);
    }

    public void removeEvent(String key) {
        FIREBASE_DATABASE.getReference(EVENT_LIST).child(key).removeValue();
    }
}
