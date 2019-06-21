package com.example.ekipaapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.ekipaapp.entity.Location;
import com.example.ekipaapp.repo.LocationRepository;
import com.google.firebase.database.DatabaseReference;


public class LocationViewModel extends AndroidViewModel {

    private LocationRepository repo;

    public LocationViewModel(Application application) {
        super(application);
        this.repo = new LocationRepository(application);
    }

    public DatabaseReference getAllLocations() {
        return repo.getAllLocations();
    }

    public void removeAllLocations() {
        repo.deleteAllLocations();
    }

    public void insertLocationForEvent(Location location, String eventKey) {
        repo.insertLocationForEvent(location, eventKey);
    }

    public DatabaseReference getLocationByKey(String locationKey, String eventKey) {
        return repo.getLocationByKey(locationKey, eventKey);
    }

    public void updateLocation(String locationKey, Location location, String eventKey) {
        repo.updateLocation(locationKey, location, eventKey);
    }

    public void removeLocation(String locationKey, String eventKey) {
        repo.removeLocation(locationKey, eventKey);
    }

    public DatabaseReference getAllLocationsForEvent(String eventKey) {
        return repo.getAllLocationsForEvent(eventKey);
    }

    public void vote(String locationKey, String eventKey, String email) {
        repo.vote(locationKey, eventKey, email);
    }

    public void unvote(String locationKey, String eventKey, String emailKey) {
        repo.unvote(locationKey, eventKey, emailKey);
    }
}
