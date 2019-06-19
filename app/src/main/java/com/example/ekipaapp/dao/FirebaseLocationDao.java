package com.example.ekipaapp.dao;

import com.example.ekipaapp.entity.Location;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ekipaapp.firebase.FirebaseDatabaseConsts.EVENT_LIST;
import static com.example.ekipaapp.firebase.FirebaseDatabaseConsts.LOCATION_LIST;

public class FirebaseLocationDao {

    private final static FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();

    public DatabaseReference getAllLocations() {
        return FIREBASE_DATABASE.getReference(LOCATION_LIST);
    }

    public void insertLocationForEvent(Location location, String eventKey) {
        FIREBASE_DATABASE.getReference(EVENT_LIST)
                .child(eventKey)
                .child(LOCATION_LIST)
                .push()
                .setValue(location);
    }

    public void removeAllLocations() {
        FIREBASE_DATABASE.getReference(LOCATION_LIST).removeValue();
    }

    public DatabaseReference getLocationByKey(String locationKey, String eventKey) {
        return FIREBASE_DATABASE.getReference(EVENT_LIST)
                .child(eventKey)
                .child(LOCATION_LIST)
                .child(locationKey);
    }

    public void updateLocation(String locationKey, Location location, String eventKey) {
        FIREBASE_DATABASE.getReference(EVENT_LIST)
                .child(eventKey)
                .child(LOCATION_LIST)
                .child(locationKey).setValue(location);
    }

    public void removeLocation(String locationKey, String eventKey) {
        FIREBASE_DATABASE.getReference(EVENT_LIST)
                .child(eventKey)
                .child(LOCATION_LIST)
                .child(locationKey)
                .removeValue();
    }

    public DatabaseReference getAllLocationsForEvent(String eventKey) {
        return FIREBASE_DATABASE.getReference(EVENT_LIST).child(eventKey).child(LOCATION_LIST);
    }
}
