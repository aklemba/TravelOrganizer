package com.example.ekipaapp.repo;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.ekipaapp.EventDatabase;
import com.example.ekipaapp.dao.EventDao;
import com.example.ekipaapp.entity.Event;

import java.util.List;

public class EventRepository {
    private EventDao eventDao;

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDao = db.eventDao();
    }

    public LiveData<List<Event>> getAllEvents() {
        return eventDao.getAllEvents();
    }
}
