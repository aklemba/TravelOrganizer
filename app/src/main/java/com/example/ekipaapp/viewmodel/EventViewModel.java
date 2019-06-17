package com.example.ekipaapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.repo.EventRepository;
import com.google.firebase.database.DatabaseReference;

public class EventViewModel extends AndroidViewModel {

    private static int counter = 0;

    //private final LiveData<List<Event>> allEvents;
    private EventRepository repo;

    public EventViewModel(Application application) {
        super(application);
        this.repo = new EventRepository(getApplication());
        //allEvents = repo.getAllEvents();
        counter++;
        Log.d("PARAPET", "EventViewModel: " + counter);
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
}
