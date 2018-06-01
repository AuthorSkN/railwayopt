package com.railwayopt.gui.custom.selectdata;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class RegionGroupForSelectStation extends AbstractRegionForSelect<StationSelected>{

    private static final String FXML_COMPONENT_NAME = "scenes/region_group_for_select_station.fxml";

    private List<StationSelected> stations;

    public RegionGroupForSelectStation(String name, List<StationSelected> stations){
        super(FXML_COMPONENT_NAME, name);
        table.setItems(FXCollections.observableArrayList(stations));
    }


    public List<StationSelected> getSelectedStations(){
        List<StationSelected> l = table.getItems().stream().filter(StationSelected::isSelected).collect(Collectors.toList());
        return l;
    }

}
