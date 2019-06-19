package com.example.ekipaapp.viewmodel;

import android.app.Application;
import android.util.Log;

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

    public void insertLocation(Location location) {
        repo.insertLocation(location);
    }

    public DatabaseReference getLocationById(String locationKey) {
        return repo.getLocationById(locationKey);
    }

    public void updateLocation(String key, Location location) {
        repo.updateLocation(key, location);
    }

    public void removeLocation(String key) {
        repo.removeLocation(key);
    }
}
