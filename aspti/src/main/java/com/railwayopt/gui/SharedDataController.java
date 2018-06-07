package com.railwayopt.gui;

import com.railwayopt.dbao.DAORailwayImpl;
import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.railwayopt.fao.XlsLoader;
import com.railwayopt.gui.custom.shareddata.RegionGroupForSharedFactory;
import com.railwayopt.gui.custom.shareddata.RegionGroupForSharedStation;
import com.railwayopt.gui.custom.shareddata.SharedFactory;
import com.railwayopt.RegionManager;
import com.railwayopt.gui.custom.shareddata.SharedStation;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedDataController implements Controller{

    @FXML
    private VBox vboxFactories;
    @FXML
    private VBox vboxStations;
    @FXML
    private TabPane tabPane;

    private RegionManager regionManager = new RegionManager();

    @Override
    public void initializeScene() {
        reloadFactory();
        reloadStation();
    }

    @FXML
    public void loadDataFromFile(){
        switch (tabPane.getSelectionModel().getSelectedIndex()){
            case 0: loadFactoryFromFile(); break;
            case 1: loadStationFromFile(); break;
        }
    }

    public void loadFactoryFromFile(){
        File file = SceneManager.showOpenFileDialog("Загрузить файл", "Excel файл", "*");
        XlsLoader loader = null;
        try {
            loader = new XlsLoader(file);
            List<Factory> factories = loader.readFactories();
            new DAORailwayImpl().addAllInfrastructure(factories);
            reloadFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStationFromFile(){
        File file = SceneManager.showOpenFileDialog("Загрузить файл", "Excel файл", "*");
        XlsLoader loader = null;
        try {
            loader = new XlsLoader(file);
            List<Station> stations = loader.readStations();
            new DAORailwayImpl().addAllInfrastructure(stations);
            reloadStation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadFactory(){
        vboxFactories.getChildren().clear();
        List<Factory> factories = new DAORailwayImpl().getAllFactories();
        Map<String, List<Factory>> regionGroups = regionManager.groupingByRegion(factories);
        Set<String> foundRegions = regionGroups.keySet();
        for(String region: foundRegions){
            vboxFactories.getChildren().add(new RegionGroupForSharedFactory(region, SharedFactory.convertToShared(regionGroups.get(region))));
        }
    }

    public void reloadStation(){
        vboxStations.getChildren().clear();
        List<Station> stations = new DAORailwayImpl().getAllStations();
        Map<String, List<Station>> regionGroups = regionManager.groupingByRegion(stations);
        Set<String> foundRegions = regionGroups.keySet();
        for(String region: foundRegions){
            vboxStations.getChildren().add(new RegionGroupForSharedStation(region, SharedStation.convertToShared(regionGroups.get(region))));
        }
    }

    @FXML
    public void deleteAll(){
        new DAORailwayImpl().deleteAllFactory();
    }
}
