package com.railwayopt.entity;


import com.railwayopt.entity.keys.VSPKey;

import javax.persistence.*;

@Entity
@Table(name="value_station_parameter")
@IdClass(VSPKey.class)
public class ValueStationParameter {

    @Id
    @Column(name = "parameter_id")
    private Integer parameterId;
    @Id
    @Column(name = "station_id")
    private Integer stationId;
    @Column(name="value")
    private Double value;


    public ValueStationParameter() {
    }

    public ValueStationParameter(Integer parameterId, Integer stationId) {
        this.parameterId = parameterId;
        this.stationId = stationId;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }


}
