package com.railwayopt.gui.custom.shareddata;

import com.railwayopt.gui.custom.selectdata.FactorySelected;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RegionGroupForSharedFactory extends AbstaractRegionForShared<SharedFactory>{

    private static final String FXML_COMPONENT_NAME = "scenes/region_group_for_shared_factory.fxml";

    @FXML
    private TableColumn<SharedFactory, Double> tableColumnWeight;
    @FXML
    private TableColumn<SharedFactory, Double> tableColumnFullWeight;
    @FXML
    private TableColumn<SharedFactory, String> tableColumnCargoes;

    private List<FactorySelected> factories;

    public RegionGroupForSharedFactory(String name, List<SharedFactory> factories){
        super(FXML_COMPONENT_NAME, name);
        tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        tableColumnFullWeight.setCellValueFactory(new PropertyValueFactory<>("fullWeight"));
        tableColumnCargoes.setCellValueFactory(new PropertyValueFactory<>("cargoes"));
        table.setItems(FXCollections.observableArrayList(factories));
    }
}
