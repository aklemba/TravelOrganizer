package com.example.ekipaapp.repo;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.ekipaapp.dao.FirebaseEventDao;
import com.example.ekipaapp.dao.FirebaseLocationDao;
import com.example.ekipaapp.entity.Location;
import com.example.ekipaapp.firebase.FirebaseDatabaseConsts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class LocationRepository {

    private final FirebaseLocationDao dao;

    public LocationRepository(Application application) {
        dao = new FirebaseLocationDao();
    }

    public void deleteAllLocations() {
        dao.removeAllLocations();
    }

    public void insertLocationForEvent(Location location, String eventKey) {
        dao.insertLocationForEvent(location, eventKey);
    }

    public DatabaseReference getAllLocations() {
        return dao.getAllLocations();
    }

    public DatabaseReference getLocationByKey(String locationKey, String eventKey) {
        return dao.getLocationByKey(locationKey, eventKey);
    }

    public void updateLocation(String locationKey, Location location, String eventKey) {
        dao.updateLocation(locationKey, location, eventKey);
    }

    public void removeLocation(String locationKey, String eventKey) {
        dao.removeLocation(locationKey, eventKey);
    }

    public DatabaseReference getAllLocationsForEvent(String eventKey) {
        return dao.getAllLocationsForEvent(eventKey);
    }

    public void vote(String locationKey, String eventKey, String email) {
        getLocationByKey(locationKey, eventKey).child("votes").push().setValue(email);
    }

    public void unvote(String locationKey, String eventKey, String emailKey) {
        getLocationByKey(locationKey, eventKey).child("votes").child(emailKey).removeValue();
    }
}