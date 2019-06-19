package com.example.ekipaapp.repo;

import com.example.ekipaapp.dao.FirebaseEventDao;
import com.example.ekipaapp.entity.Event;
import com.google.firebase.database.DatabaseReference;

public class EventRepository {
    FirebaseEventDao dao = new FirebaseEventDao();

    public DatabaseReference getAllEvents() {
        return dao.getAllEvents();
    }

    public void addEvent(Event event) {
        dao.addEvent(event);
    }
}
