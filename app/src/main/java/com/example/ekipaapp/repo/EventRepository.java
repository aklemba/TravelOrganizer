package com.example.ekipaapp.repo;

import android.app.Application;

import com.example.ekipaapp.dao.FirebaseEventDao;
import com.example.ekipaapp.entity.Event;
import com.google.firebase.database.DatabaseReference;

public class EventRepository {

    private final FirebaseEventDao dao;

    public EventRepository(Application application) {
        dao = new FirebaseEventDao();
    }

    public void deleteAllEvents() {
        dao.removeAllEvents();
    }

    public void insertEvent(Event event) {
        dao.insertEvent(event);
    }

    public DatabaseReference getAllEvents() {
        return dao.getAllEvents();
    }

    public DatabaseReference getEventById(String eventKey) {
        return dao.getEventByKey(eventKey);
    }

    public void updateEvent(String key, Event event) {
        dao.updateEvent(key, event);
    }

    public void removeEvent(String key) {
        dao.removeEvent(key);
    }
}