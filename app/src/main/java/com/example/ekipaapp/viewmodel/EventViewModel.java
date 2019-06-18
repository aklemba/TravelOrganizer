package com.example.ekipaapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.repo.EventRepository;
import com.google.firebase.database.DatabaseReference;

public class EventViewModel extends AndroidViewModel {

    private EventRepository repo;

    public EventViewModel(Application application) {
        super(application);
        this.repo = new EventRepository(application);
    }

    public DatabaseReference getAllEvents() {
        return repo.getAllEvents();
    }

    public void removeAllEvents() {
        repo.deleteAllEvents();
    }

    public void insertEvent(Event event) {
        repo.insertEvent(event);
    }

    public DatabaseReference getEventById(String eventKey) {
        return repo.getEventById(eventKey);
    }

    public void updateEvent(String key, Event event) {
        repo.updateEvent(key, event);
    }

    public void removeEvent(String key) {
        repo.removeEvent(key);
    }
}
