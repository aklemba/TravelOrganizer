package com.example.ekipaapp.entity;

public class Event {

    private String name;

    private String url;

    private int rentalCostPerPerson;

    private int rentalCostOverall;

    private int routeLength;

    public Event(){}

    public Event(String name) {
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
}
