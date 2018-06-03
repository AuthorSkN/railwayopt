package com.railwayopt.entity;

public class Station implements Infrastructable{

    private boolean existLogisticCentre = false;

    public Station(int id, String name, Double latitude, Double longitude) {
        /*super(id, name, latitude, longitude);*/
    }

    public Station(int id, String name, Double latitude, Double longitude, boolean existLogisticCentre) {
        /*super(id, name, latitude, longitude);*/
        this.existLogisticCentre = existLogisticCentre;
    }

    public boolean isExistLogisticCentre() {
        return existLogisticCentre;
    }

    public void setExistLogisticCentre(boolean existLogisticCentre) {
        this.existLogisticCentre = existLogisticCentre;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Double getLatitude() {
        return null;
    }

    @Override
    public void setLatitude(Double latitude) {

    }

    @Override
    public Double getLongitude() {
        return null;
    }

    @Override
    public void setLongitude(Double longitude) {

    }

    @Override
    public Region getRegion() {
        return null;
    }

    @Override
    public void setRegion(Region region) {

    }

    @Override
    public String getDescr() {
        return null;
    }

    @Override
    public void setDescr(String descr) {

    }
}
