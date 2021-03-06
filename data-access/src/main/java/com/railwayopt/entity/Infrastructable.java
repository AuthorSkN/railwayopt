package com.railwayopt.entity;

import javax.persistence.*;

public interface Infrastructable {

    Integer getId();

    void setId(Integer id);

    String getName();

    void setName(String name);

    Double getLatitude();

    void setLatitude(Double latitude);

    Double getLongitude();

    void setLongitude(Double longitude);

    Region getRegion();

    void setRegion(Region region);

    Double getX();

    void setX(Double x);

    Double getY();

    void setY(Double y);

    Infrastructure getAndDeleteInfrastructure();
}
