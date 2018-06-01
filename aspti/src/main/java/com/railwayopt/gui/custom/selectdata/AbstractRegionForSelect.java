package com.railwayopt.gui.custom.selectdata;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import java.io.IOException;
import java.util.*;

public abstract class AbstractRegionForSelect<T extends Selectable> extends TitledPane
{

    @FXML
    protected TableView<T> table;
    @FXML
    protected TableColumn<T, Boolean> tableColumnSelect;
    @FXML
    protected TableColumn<T, String> tableColumnName;
    @FXML
    protected TableColumn<T, Double> tableColumnLatitude;
    @FXML
    protected TableColumn<T, Double> tableColumnLongitude;

    @FXML
    protected CheckBox checkAllSelect;


    private List<BooleanProperty> cells = new ArrayList<>(10);

    public AbstractRegionForSelect(String fxmlComponentName, String name){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlComponentName));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();

            this.setText(name);

            checkAllSelect.selectedProperty().addListener(this::selectAllEvent);

            table.setEditable(true);
            tableColumnSelect.setCellValueFactory(param -> {
                T selected = param.getValue();
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(selected.isSelected());
                booleanProp.addListener((observable, oldValue, newValue) -> selected.setSelected(newValue));
                cells.add(booleanProp);
                return booleanProp;
            });
            tableColumnSelect.setCellFactory(p -> {
                CheckBoxTableCell<T, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            });
            tableColumnSelect.setEditable(true);
            tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableColumnLatitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            tableColumnLongitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
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
}
