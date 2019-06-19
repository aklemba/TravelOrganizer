package com.example.ekipaapp.dao;

import com.example.ekipaapp.entity.Location;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ekipaapp.firebase.FirebaseDatabaseConsts.LOCATION_LIST;

public class FirebaseLocationDao {

    private final static FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();

    public DatabaseReference getAllLocations() {
        return FIREBASE_DATABASE.getReference(LOCATION_LIST);
    }

    public void insertLocation(Location location) {
        FIREBASE_DATABASE.getReference(LOCATION_LIST).push().setValue(location);
    }

    public void removeAllLocations() {
        FIREBASE_DATABASE.getReference(LOCATION_LIST).removeValue();
    }

    public DatabaseReference getLocationByKey(String locationKey) {
        return FIREBASE_DATABASE.getReference(LOCATION_LIST).child(locationKey);
    }

    public void updateLocation(String key, Location location) {
        FIREBASE_DATABASE.getReference(LOCATION_LIST).child(key).setValue(location);
    }

    public void removeLocation(String key) {
        FIREBASE_DATABASE.getReference(LOCATION_LIST).child(key).removeValue();
    }
}
