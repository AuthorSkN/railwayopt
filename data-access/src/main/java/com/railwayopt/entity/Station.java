package com.railwayopt.entity;

public class Station extends Infrastructure{

    private boolean existLogisticCentre = false;

    public Station(int id, String name, Double latitude, Double longitude) {
        super(id, name, latitude, longitude);
    }

    public Station(int id, String name, Double latitude, Double longitude, boolean existLogisticCentre) {
        super(id, name, latitude, longitude);
        this.existLogisticCentre = existLogisticCentre;
    }

    public boolean isExistLogisticCentre() {
        return existLogisticCentre;
    }

    public void setExistLogisticCentre(boolean existLogisticCentre) {
        this.existLogisticCentre = existLogisticCentre;
    }
}
