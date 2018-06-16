package com.railwayopt.entity;


import javax.persistence.*;

@Entity
@Table(name="geo_object")
public class Infrastructure {

    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name="longitude")
    private Double longitude;
    @Column(name="is_station")
    private Boolean isStation;
    @Column(name="x_coord")
    private Double x;
    @Column(name="y_coord")
    private Double y;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    public Infrastructure() {
    }

    public Infrastructure(boolean isStation){
        this.isStation = isStation;
    }

    public Infrastructure(int id, String name, Double latitude, Double longitude, Double x, Double y, boolean isStation) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isStation = isStation;
        this.x = x;
        this.y = y;
    }

    public Infrastructure(Integer id, String name, Double latitude, Double longitude, Double x, Double y, Region region, boolean isStation) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.region = region;
        this.isStation = isStation;
        this.x = x;
        this.y = y;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }


    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }



}
