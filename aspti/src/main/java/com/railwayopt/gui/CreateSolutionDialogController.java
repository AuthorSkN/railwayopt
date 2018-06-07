package com.railwayopt.gui;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.railwayopt.mapview.GeoPoint;
import com.railwayopt.mapview.MapView;
import com.railwayopt.mapview.googlemap.GoogleMapAPI;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.KProInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.Collection;

public class CreateSolutionDialogController implements Controller{

    @FXML
    private TextField textName;
    @FXML
    private TextArea textDesc;
    @FXML
    private TextField textCountKP;
    @FXML
    private TextField textCountKNRC;
    @FXML
    private MapView mapView;

    private static Collection<Factory> projectFactories;
    private static Collection<Station> projectStations;
    private static String name;
    private static String desc;
    private static int countKP;
    private static int countKNRC;


    public void setObjects(Collection<Factory> factories, Collection<Station> stations){
        projectFactories = factories;
        projectStations = stations;
    }

    @Override
    public void initializeScene()  {
        mapView.setMapAPI(new GoogleMapAPI());
        mapView.reloadMap();
        mapView.onLoadedMap(()-> {
            for (Factory factory : projectFactories) {
                mapView.createMapPoint(factory.getId(), new GeoPoint(factory.getLatitude(), factory.getLongitude()), 3);
            }
        });
    }

    @FXML
    public void cliced(){
        mapView.createMapPoint(4, new GeoPoint(65.7, 37.3), 3);
    }

    @FXML
    public void setMCOParameters(){
        name = textName.getText();
        desc = textDesc.getText();
        countKP = Integer.parseInt(textCountKP.getText());
        countKNRC = Integer.parseInt(textCountKNRC.getText());
        if (countKNRC == 0){
            createSolution();
        }
    }

    public void createSolution(){
        SceneManager.installMapSceneForSolutionDialog();
    }

}
