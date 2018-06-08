package com.railwayopt.gui.custom.shareddata;

import com.railwayopt.entity.Station;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RegionGroupForSharedStation extends AbstaractRegionForShared<SharedStation> {

    private static final String FXML_COMPONENT_NAME = "scenes/region_group_for_shared_station.fxml";

    @FXML
    private TableColumn<SharedStation, Integer> tableColumnClass;
    @FXML
    private TableColumn<SharedStation, String> tableColumnType;
    @FXML
    private TableColumn<SharedStation, String> tableColumnIsLogCentre;

    public RegionGroupForSharedStation(String name, List<SharedStation> stations) {
        super(FXML_COMPONENT_NAME, name);
        tableColumnClass.setCellValueFactory(new PropertyValueFactory<>("stationClass"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnIsLogCentre.setCellValueFactory(new PropertyValueFactory<>("sharedIsLC"));
        table.setItems(FXCollections.observableArrayList(stations));
    }
}
