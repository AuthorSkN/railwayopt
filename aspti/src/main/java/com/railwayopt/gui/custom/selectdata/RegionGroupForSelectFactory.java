package com.railwayopt.gui.custom.selectdata;

import com.railwayopt.entity.Factory;
import com.sun.prism.impl.FactoryResetException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.util.Callback;


import java.io.IOException;
import java.util.List;

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

    private List<FactorySelected> factories;


    public RegionGroupForSelectFactory(List<FactorySelected> factories){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            Callback<TableColumn<FactorySelected, Boolean>, TableCell<FactorySelected, Boolean>> booleanCellFactory =
                    new Callback<TableColumn<FactorySelected, Boolean>, TableCell<FactorySelected, Boolean>>() {

                        @Override
                        public TableCell<FactorySelected, Boolean> call(TableColumn<FactorySelected, Boolean> p) {
                            return new BooleanCell();
                        }
                    };

            tableColumnSelect.setCellValueFactory(new PropertyValueFactory<FactorySelected, Boolean>("selected"));
            tableColumnSelect.setCellFactory(booleanCellFactory);
            tableColumnName.setCellValueFactory(new PropertyValueFactory<FactorySelected, String>("name"));
            tableColumnLatitude.setCellValueFactory(new PropertyValueFactory<FactorySelected, Double>("latitude"));
            tableColumnLongitude.setCellValueFactory(new PropertyValueFactory<FactorySelected, Double>("longitude"));
            tableColumnWeight.setCellValueFactory(new PropertyValueFactory<FactorySelected, Double>("weight"));
            table.setItems(FXCollections.observableArrayList(factories));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    class BooleanCell extends TableCell<FactorySelected, Boolean> {
        private CheckBox checkBox;
        public BooleanCell() {
            checkBox = new CheckBox();
            //checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing())
                        commitEdit(newValue == null ? false : newValue);
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            checkBox.setDisable(false);
            checkBox.requestFocus();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                checkBox.setSelected(item);
            }
        }
    }
}
