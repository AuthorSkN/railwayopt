package com.railwayopt.entity.keys;

import java.io.Serializable;

public class VSPKey implements Serializable {

    protected Integer parameterId;
    protected Integer stationId;

    public VSPKey() {
    }

    public VSPKey(Integer parameterId, Integer stationId) {
        this.parameterId = parameterId;
        this.stationId = stationId;
    }
}
