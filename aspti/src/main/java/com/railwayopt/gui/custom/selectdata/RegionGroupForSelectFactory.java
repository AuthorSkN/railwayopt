package com.railwayopt.gui.custom.selectdata;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegionGroupForSelectFactory extends AbstractRegionForSelect<FactorySelected> {


    private static final String FXML_COMPONENT_NAME = "scenes/region_group_for_select_factory.fxml";

    @FXML
    private TableColumn<FactorySelected, Double> tableColumnWeight;

    private List<FactorySelected> factories;

    public RegionGroupForSelectFactory(String name, List<FactorySelected> factories){
        super(FXML_COMPONENT_NAME, name, factories);
        this.factories = factories;
        tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        table.setItems(FXCollections.observableArrayList(factories));
    }


    public List<FactorySelected> getSelectedFactories(){
        List<FactorySelected> selectedList = new ArrayList<>();
        selectedList = factories.stream().filter(FactorySelected::isSelected).collect(Collectors.toList());
        return selectedList;/*table.getItems().stream().filter(FactorySelected::isSelected).collect(Collectors.toList());*/
    }


}
