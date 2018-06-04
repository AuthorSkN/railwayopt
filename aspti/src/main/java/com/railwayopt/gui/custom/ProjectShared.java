package com.railwayopt.gui.custom;

import com.railwayopt.entity.*;
import com.railwayopt.gui.SceneManager;
import com.railwayopt.gui.custom.shareddata.SharedFactory;
import com.railwayopt.gui.custom.shareddata.SharedStation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProjectShared extends VBox{

    private static final String FXML_COMPONENT_NAME = "scenes/project_shared.fxml";

    private Project project;

    @FXML
    private Label projectName;
    @FXML
    private TextArea projectDesc;
    @FXML
    private TableView<SharedFactory> tableFactory;
    @FXML
    private TableColumn<SharedFactory, Integer> columnNumberFactory;
    @FXML
    private TableColumn<SharedFactory, String> columnNameFactory;
    @FXML
    private TableColumn<SharedFactory, Double> columnLatFactory;
    @FXML
    private TableColumn<SharedFactory, Double> columnLonFactory;
    @FXML
    private TableColumn<SharedFactory, Double> columnWeightFactory;

    @FXML
    private TableView<SharedStation> tableStation;
    @FXML
    private TableColumn<SharedStation, Integer> columnNumberStation;
    @FXML
    private TableColumn<SharedStation, String> columnNameStation;
    @FXML
    private TableColumn<SharedStation, Double> columnLatStation;
    @FXML
    private TableColumn<SharedStation, Double> columnLonStation;
    @FXML
    private TableColumn<SharedStation, String> columnIsLogCentre;



    public ProjectShared(Project project){
        super();
        this.project = project;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            projectName.setText(project.getName());
            projectDesc.setText(project.getDescription());
            initializeTableStation();
            tableStation.setItems(FXCollections.observableArrayList(SharedStation.convertToShared( project.getStations() )));
            initializeTableFactory();
            tableFactory.setItems(FXCollections.observableArrayList(SharedFactory.convertToShared( project.getFactories() )));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initializeTableStation(){
        columnNumberStation.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnNameStation.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLatStation.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        columnLonStation.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        columnIsLogCentre.setCellValueFactory(new PropertyValueFactory<>("sharedIsLC"));
    }

    private void initializeTableFactory(){
        columnNumberFactory.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnNameFactory.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnLatFactory.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        columnLonFactory.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        columnWeightFactory.setCellValueFactory(new PropertyValueFactory<>("weight"));
    }

    @FXML
    public void openSolutions(){
        SceneManager.installSolutionsScene(project);
    }
}
