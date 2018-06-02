package com.railwayopt.gui;

import com.railwayopt.DB;
import com.railwayopt.dataimport.XlsLoader;
import com.railwayopt.entity.*;
import com.railwayopt.gui.custom.selectdata.*;
import com.railwayopt.model.location.RegionManager;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class AddProjectDialogController implements Controller {

    private static String name;
    private static String desc;
    private static List<Factory> factories;
    private static List<Station> stations;
    private static boolean ok;

    @FXML
    private TextField textFieldNameProject;
    @FXML
    private TextArea textAreaProjectDescription;
    @FXML
    private VBox vboxFactories;
    @FXML
    private VBox vboxStations;
    @FXML
    private CheckBox checkSelectAllFactory;
    @FXML
    private CheckBox checkSelectAllStation;

    private List<Factory> factoriesFromDB;
    private List<Station> stationsFromDB;
    private RegionManager regionManager = new RegionManager();

    @Override
    public void initializeScene() {
        ok = false;
        factoriesFromDB = DB.getAllFactories();
        stationsFromDB = DB.getAllStations();
        checkSelectAllFactory.selectedProperty().addListener(this::selectAllFactory);
        checkSelectAllStation.selectedProperty().addListener(this::selectAllStation);
    }

    @FXML
    public void selectData(){
        name = textFieldNameProject.getText();
        desc = textAreaProjectDescription.getText();
        SceneManager.installSelectDataSceneForAddProjectDialog();
    }

    public static Project getNewProject(){
        Project newProject = null;
        if(ok){
            newProject = new Project(0, name, desc);
            newProject.setFactories(new HashSet<>(factories));
            newProject.setStations(new HashSet<>(stations));
        }
        return newProject;
    }

    public void selectAllFactory(ObservableValue observableValue, Boolean oldValue, Boolean newValue){
        if (newValue && !oldValue) {
            for(Node group: vboxFactories.getChildren()){
                ((RegionGroupForSelectFactory)group).selectAll(true);
            }
        }else if (!newValue && oldValue){
            for(Node group: vboxFactories.getChildren()){
                ((RegionGroupForSelectFactory)group).selectAll(false);
            }
        }

    }

    public void selectAllStation(ObservableValue observableValue, Boolean oldValue, Boolean newValue){
        if (newValue && !oldValue) {
            for(Node group: vboxStations.getChildren()){
                ((RegionGroupForSelectStation)group).selectAll(true);
            }
        }else if (!newValue && oldValue){
            for(Node group: vboxStations.getChildren()){
                ((RegionGroupForSelectStation)group).selectAll(false);
            }
        }

    }

    @FXML
    public void loadFactoryFromDB(){
        Map<String, List<FactorySelected>> regionGroups = regionManager.groupingByRegion(FactorySelected.convertToSelected(factoriesFromDB));
        Set<String> foundRegions = regionGroups.keySet();
            for(String region: foundRegions){
            vboxFactories.getChildren().add(new RegionGroupForSelectFactory(region, regionGroups.get(region)));
        }
    }

    @FXML
    public void loadFactoryFromFile(){
        File file = SceneManager.showOpenFileDialog("Загрузить файл", "Excel файл", "*");
        XlsLoader loader = null;
        try {
            loader = new XlsLoader(file);
            List<Factory> factories = loader.readProductions();
            Map<String, List<FactorySelected>> regionGroups = regionManager.groupingByRegion(FactorySelected.convertToSelected(factories));
            Set<String> foundRegions = regionGroups.keySet();
            for(String region: foundRegions){
                vboxFactories.getChildren().add(new RegionGroupForSelectFactory(region, regionGroups.get(region)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadStationFromDB(){
        Map<String, List<StationSelected>> regionGroups = regionManager.groupingByRegion(StationSelected.convertToSelected(stationsFromDB));
        Set<String> foundRegions = regionGroups.keySet();
        for(String region: foundRegions){
            vboxStations.getChildren().add(new RegionGroupForSelectStation(region, regionGroups.get(region)));
        }
    }

    @FXML
    public void close(){
        SceneManager.addProjectDialogClose();
    }

    @FXML void createProject(){
        factories = new ArrayList<>();
        for(Node node: vboxFactories.getChildren()){
            factories.addAll(((RegionGroupForSelectFactory)node).getSelectedFactories());
        }
        stations = new ArrayList<>();
        for(Node node: vboxStations.getChildren()){
            stations.addAll(((RegionGroupForSelectStation)node).getSelectedStations());
        }
        this.ok = true;
        close();
    }

}
