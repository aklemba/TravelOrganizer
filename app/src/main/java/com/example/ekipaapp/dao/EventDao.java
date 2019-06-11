package com.example.ekipaapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ekipaapp.entity.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Insert
    void insertEvent(Event event);

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();
}
