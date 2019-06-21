package com.example.ekipaapp.entity;

import java.util.HashMap;

public class Location {

    private String name;

    private String url;

    private int rentalCostPerPerson;

    private int rentalCostOverall;

    private int routeLength;

    private HashMap<String, String> votes;

    public Location(){}

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRentalCostPerPerson() {
        return rentalCostPerPerson;
    }

    public void setRentalCostPerPerson(int rentalCostPerPerson) {
        this.rentalCostPerPerson = rentalCostPerPerson;
    }

    public int getRentalCostOverall() {
        return rentalCostOverall;
    }

    public void setRentalCostOverall(int rentalCostOverall) {
        this.rentalCostOverall = rentalCostOverall;
    }

    public int getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(int routeLength) {
        this.routeLength = routeLength;
    }

    public HashMap<String, String> getVotes() {
        return votes;
    }

    public void setVotes(HashMap<String, String> votes) {
        this.votes = votes;
    }
}
