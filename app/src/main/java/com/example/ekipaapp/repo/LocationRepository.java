package com.example.ekipaapp.repo;

import android.app.Application;

import com.example.ekipaapp.dao.FirebaseLocationDao;
import com.example.ekipaapp.entity.Location;
import com.google.firebase.database.DatabaseReference;


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