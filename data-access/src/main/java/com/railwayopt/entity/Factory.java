package com.railwayopt.entity;

public class Factory extends Infrastructure{


    private Double weight;

    public Factory() {
    }

    public Factory(int id, String name, Double latitude, Double longitude) {
        super(id, name, latitude, longitude);
    }

    public Factory(int id, String name, Double latitude, Double longitude, Double weight) {
        super(id, name, latitude, longitude);
        this.weight = weight;
    }



    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
