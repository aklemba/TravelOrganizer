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

    public void insertLocation(Location location) {
        dao.insertLocation(location);
    }

    public DatabaseReference getAllLocations() {
        return dao.getAllLocations();
    }

    public DatabaseReference getLocationById(String locationKey) {
        return dao.getLocationByKey(locationKey);
    }

    public void updateLocation(String key, Location location) {
        dao.updateLocation(key, location);
    }

    public void removeLocation(String key) {
        dao.removeLocation(key);
    }
}