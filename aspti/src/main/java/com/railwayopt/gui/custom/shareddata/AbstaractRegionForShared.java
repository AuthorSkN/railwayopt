package com.railwayopt.gui.custom.shareddata;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstaractRegionForShared<T extends Numberable> extends TitledPane {

    @FXML
    protected TableView<T> table;
    @FXML
    protected TableColumn<T, Integer> tableColumnNumber;
    @FXML
    protected TableColumn<T, String> tableColumnName;
    @FXML
    protected TableColumn<T, Double> tableColumnLatitude;
    @FXML
    protected TableColumn<T, Double> tableColumnLongitude;
    @FXML
    protected TableColumn<T, Double> tableColumnX;
    @FXML
    protected TableColumn<T, Double> tableColumnY;


    public AbstaractRegionForShared(String fxmlComponentName, String name){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlComponentName));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();

            this.setText(name);
            tableColumnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            tableColumnLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
            tableColumnX.setCellValueFactory(new PropertyValueFactory<>("x"));
            tableColumnY.setCellValueFactory(new PropertyValueFactory<>("y"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
