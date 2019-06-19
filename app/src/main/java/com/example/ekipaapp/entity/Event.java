package com.example.ekipaapp.entity;

import java.util.HashMap;

public class Event {
    String name;
    HashMap<String, Location> locationMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Location> getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(HashMap<String, Location> locationMap) {
        this.locationMap = locationMap;
    }
}
