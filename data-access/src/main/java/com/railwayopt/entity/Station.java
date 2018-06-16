package com.railwayopt.entity;

import javax.persistence.*;

@Entity
@Table(name="station")
public class Station implements Infrastructable{

    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="type")
    private String type;
    @Column(name="class")
    private Integer stationClass;
    @Column(name="is_logistic_centre")
    private Boolean existLogisticCentre;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private RailwayBranch branch;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Infrastructure infrastructure;

    public Station(){}

    public Station(int id, String name, Double latitude, Double longitude, Double x, Double y) {
        this.id = id;
        infrastructure = new Infrastructure(id, name, latitude, longitude, x, y, true);
    }

    public Station(int id, String name, Double latitude, Double longitude, Double x, Double y, boolean existLogisticCentre) {
        this.id  = id;
        this.existLogisticCentre = existLogisticCentre;
        infrastructure = new Infrastructure(id, name, latitude, longitude,x, y, true);
    }

    public Boolean isExistLogisticCentre() {
        return existLogisticCentre;
    }

    public void setExistLogisticCentre(Boolean existLogisticCentre) {
        this.existLogisticCentre = existLogisticCentre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStationClass() {
        return stationClass;
    }

    public void setStationClass(Integer stationClass) {
        this.stationClass = stationClass;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
        infrastructure.setId(id);
    }

    @Override
    public String getName() {
        return infrastructure.getName();
    }

    @Override
    public void setName(String name) {
        infrastructure.setName(name);
    }

    @Override
    public Double getLatitude() {
        return infrastructure.getLatitude();
    }

    @Override
    public void setLatitude(Double latitude) {
        infrastructure.setLatitude(latitude);
    }

    @Override
    public Double getLongitude() {
        return infrastructure.getLongitude();
    }

    @Override
    public void setLongitude(Double longitude) {
        infrastructure.setLongitude(longitude);
    }

    @Override
    public Region getRegion() {
        return infrastructure.getRegion();
    }

    @Override
    public void setRegion(Region region) {
        infrastructure.setRegion(region);
    }

    @Override
    public Double getX() {
        return infrastructure.getX();
    }

    @Override
    public void setX(Double x) {
        infrastructure.setX(x);
    }

    @Override
    public Double getY() {
        return infrastructure.getY();
    }

    @Override
    public void setY(Double y) {
        infrastructure.setY(y);
    }


    public Infrastructure getAndDeleteInfrastructure(){
        Infrastructure result = this.infrastructure;
        this.infrastructure = null;
        return result;
    }
}
