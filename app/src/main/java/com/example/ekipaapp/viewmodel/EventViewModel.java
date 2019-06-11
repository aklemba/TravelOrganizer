package com.example.ekipaapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.repo.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private final LiveData<List<Event>> allEvents;
    private EventRepository repo;

    public EventViewModel(Application application) {
        super(application);
        this.repo = new EventRepository(getApplication());
        allEvents = repo.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
}
