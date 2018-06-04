package com.railwayopt.gui;

import com.railwayopt.DB;
import com.railwayopt.entity.Factory;
import com.railwayopt.gui.custom.selectdata.RegionGroupForSelectStation;
import com.railwayopt.gui.custom.selectdata.StationSelected;
import com.railwayopt.gui.custom.shareddata.RegionGroupForSharedFactory;
import com.railwayopt.gui.custom.shareddata.SharedFactory;
import com.railwayopt.model.location.RegionManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedDataController implements Controller{

    @FXML
    private VBox vboxFactory;

    private RegionManager regionManager = new RegionManager();

    @Override
    public void initializeScene() {
        Map<String, List<Factory>> regionGroups = regionManager.groupingByRegion(DB.getAllFactories());
        Set<String> foundRegions = regionGroups.keySet();
        for(String region: foundRegions){
            vboxFactory.getChildren().add(new RegionGroupForSharedFactory(region, SharedFactory.convertToShared(regionGroups.get(region))));
        }
    }

    public void addData(){

    }
}
