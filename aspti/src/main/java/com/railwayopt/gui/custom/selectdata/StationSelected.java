package com.railwayopt.gui.custom.selectdata;

import com.railwayopt.entity.*;

import java.util.*;

public class StationSelected extends Station implements Selectable{

    private boolean selected = false;

    public StationSelected(Station station){
        super(station.getId(), station.getName(), station.getLatitude(), station.getLongitude(), station.isExistLogisticCentre());
        this.setRegion(station.getRegion());
}

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static List<StationSelected> convertToSelected(List<Station> stations){
        List<StationSelected> result = new ArrayList<>();
        for(Station station: stations){
            result.add(new StationSelected(station));
        }
        return result;
    }
}
