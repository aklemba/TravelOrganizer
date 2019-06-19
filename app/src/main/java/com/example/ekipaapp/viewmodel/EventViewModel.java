package com.example.ekipaapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.ekipaapp.entity.Event;
import com.example.ekipaapp.repo.EventRepository;
import com.google.firebase.database.DatabaseReference;

public class EventViewModel extends ViewModel {
    EventRepository eventRepository = new EventRepository();

    public DatabaseReference getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public void addEvent(Event event) {
        eventRepository.addEvent(event);
    }
}
