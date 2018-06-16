package com.railwayopt.entity;


import javax.persistence.*;

@Entity
@Table(name="factory")
public class Factory implements Infrastructable{

    @Id
    @Column(name="id")
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Infrastructure infrastructure;
    @Column(name="cargoes_types")
    private String cargoes;
    @Column(name="full_weight")
    private Double fullWeight;
    @Column(name="weight")
    private Double weight;

    public Factory() {
        infrastructure = new Infrastructure(false);
    }

    public Factory(Integer id) {
        this.id = id;
        infrastructure = new Infrastructure(false);
        infrastructure.setId(id);
    }

    public Factory(Integer id, String name, Double latitude, Double longitude, Double x, Double y) {
        this.id = id;
        infrastructure = new Infrastructure(id, name, latitude, longitude,x, y, false);
    }

    public Factory(Integer id, String name, Double latitude, Double longitude, Double x, Double y,  Double weight){
        this.id = id;
        this.weight = weight;
        infrastructure = new Infrastructure(id, name, latitude, longitude, x, y, false);
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCargoes() {
        return cargoes;
    }

    public void setCargoes(String cargoes) {
        this.cargoes = cargoes;
    }


    public Double getFullWeight() {
        return fullWeight;
    }

    public void setFullWeight(Double fullWeight) {
        this.fullWeight = fullWeight;
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
        this.infrastructure.setX(x);
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
