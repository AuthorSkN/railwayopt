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

public class RegionGroupForSelectFactory extends TitledPane {


    private static final String FXML_COMPONENT_NAME = "scenes/region_group_for_select_factory.fxml";

    @FXML
    private TableView<FactorySelected> table;
    @FXML
    private TableColumn<FactorySelected, Boolean> tableColumnSelect;
    @FXML
    private TableColumn<FactorySelected, String> tableColumnName;
    @FXML
    private TableColumn<FactorySelected, Double> tableColumnLatitude;
    @FXML
    private TableColumn<FactorySelected, Double> tableColumnLongitude;
    @FXML
    private TableColumn<FactorySelected, Double> tableColumnWeight;
    @FXML
    private CheckBox checkAllSelect;

    private List<FactorySelected> factories;

    private List<BooleanProperty> cells = new ArrayList<>(10);


    public RegionGroupForSelectFactory(String name, List<FactorySelected> factories){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();

            this.setText(name);

            checkAllSelect.selectedProperty().addListener(this::selectAllEvent);

            table.setEditable(true);
            tableColumnSelect.setCellValueFactory(param -> {
                FactorySelected factorySelected = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(factorySelected.isSelected());
                booleanProp.addListener((observable, oldValue, newValue) -> factorySelected.setSelected(newValue));
                cells.add(booleanProp);
                return booleanProp;
            });
            tableColumnSelect.setCellFactory(p -> {
                CheckBoxTableCell<FactorySelected, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            });
            tableColumnSelect.setEditable(true);
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            tableColumnLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
            tableColumnWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
            table.setItems(FXCollections.observableArrayList(factories));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void selectAll(boolean flag){
        if(flag){
            checkAllSelect.setSelected(true);
            selectAllEvent(null, false, true);
        }else{
            checkAllSelect.setSelected(false);
            selectAllEvent(null, true, false);
        }
    }

    private void selectAllEvent(ObservableValue observable, Boolean oldValue, Boolean newValue){
        //флаг установлен
        if (newValue && !oldValue) {
            cells.forEach(cell-> cell.set(true));
        }else if (!newValue && oldValue){
            cells.forEach(cell-> cell.set(false));
        }
    }

    public List<FactorySelected> getSelectedFactories(){
        return table.getItems().stream().filter(FactorySelected::isSelected).collect(Collectors.toList());
    }


}
