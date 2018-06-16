package com.railwayopt.gui.custom.shareddata;

import com.railwayopt.entity.Station;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SharedStation extends Station implements Numberable{

    private String sharedIsLC;
    private int number;

    public SharedStation(Station station){
        super(station.getId(), station.getName(), station.getLatitude(), station.getLongitude(), station.getX(), station.getY());
        this.setRegion(station.getRegion());
        this.setExistLogisticCentre(station.isExistLogisticCentre());
    }

    public SharedStation(int number, Station station){
        this(station);
        this.number = number;
    }

    public String getSharedIsLC(){
        return sharedIsLC;
    }

    public void setExistLogisticCentre(boolean existLogisticCentre){
        super.setExistLogisticCentre(existLogisticCentre);
        sharedIsLC = (existLogisticCentre)? "да" : "нет";
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static List<SharedStation> convertToShared(Collection<Station> stations){
        List<SharedStation> sharedStations = new ArrayList<>();
        int number = 1;
        for(Station station:stations){
            sharedStations.add(new SharedStation(number++, station));
        }
        return sharedStations;
    }
}
