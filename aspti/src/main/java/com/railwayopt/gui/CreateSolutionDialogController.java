package com.railwayopt.gui;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.railwayopt.mapview.GeoPoint;
import com.railwayopt.mapview.MapView;
import com.railwayopt.mapview.googlemap.GoogleMapAPI;
import com.railwayopt.mapview.graphic.MapPoint;
import com.railwayopt.mapview.graphic.MapPointStyle;
import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.KMeansProClustering;
import com.railwayopt.model.clustering.kmeanspro.KProInitializer;
import com.railwayopt.model.clustering.kmeanspro.ProjectedCluster;
import com.railwayopt.model.clustering.kmeanspro.ProjectionPoint;
import gov.nasa.worldwind.geom.LatLon;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;

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

    private static Map<Integer, Factory> projectFactories = new HashMap<>();
    private static Map<Integer, Station> projectStations = new HashMap<>();
    private static String desc;
    private static int countKP;
    private static int countKNRC;
    private static List<ProjectedCluster> firstLayerClusters;


    public void setObjects(Collection<Factory> factories, Collection<Station> stations){
        factories.forEach(factory -> projectFactories.put(factory.getId(), factory));
        stations.forEach(station -> projectStations.put(station.getId(), station));
    }

    @Override
    public void initializeScene()  {
        mapView.setMapAPI(new GoogleMapAPI());
        mapView.reloadMap();
        mapView.onLoadedMap(()-> {
            String[] colors = MapPointStyle.generateDifferentColors(countKP);
            int colorsIdx = 0;
            for(ProjectedCluster cluster: firstLayerClusters){
                Station station = projectStations.get(cluster.getCentre().getId());
                for(Element element: cluster){
                    Factory factory = projectFactories.get(element.getId());
                    List<GeoPoint> points = new ArrayList<>();
                    points.add(new GeoPoint(factory.getLatitude(), factory.getLongitude()));
                    points.add(new GeoPoint(station.getLatitude(), station.getLongitude()));
                    mapView.createMapPolyline(factory.getId(), 2, colors[colorsIdx], points);
                    mapView.createMapPoint(factory.getId(), new GeoPoint(factory.getLatitude(), factory.getLongitude()),
                            3, new MapPointStyle(MapPointStyle.CIRCLE, "#000", colors[colorsIdx]));
                }

                mapView.createMapPoint(station.getId(), new GeoPoint(station.getLatitude(), station.getLongitude()), 5,
                        new MapPointStyle(MapPointStyle.SQUARE, "#000", colors[colorsIdx]));
                colorsIdx++;
            }
        });
    }


    @FXML
    public void setMCOParameters(){
        desc = textDesc.getText();
        countKP = Integer.parseInt(textCountKP.getText());
        countKNRC = Integer.parseInt(textCountKNRC.getText());
        if (countKNRC == 0){
            createSolution();
        }
    }

    public void createSolution(){
        KProInitializer initializer = new KProInitializer();
        initializer.setK(countKP);
        KMeansProClustering clustering = new KMeansProClustering(countKP);
        clustering.setClusterInitializer(initializer);
        List<ProjectionPoint> projectionPoints = new ArrayList<>(projectStations.size());
        for(Station station: projectStations.values()){
            ProjectionPoint point = new ProjectionPoint(station.getId(), false);
            point.setCoorinatesByLatLon(station.getLatitude(), station.getLongitude());
            projectionPoints.add(point);
        }
        clustering.setProjectionPoints(projectionPoints);
        List<Element> elements = new ArrayList<>();
        for(Factory factory: projectFactories.values()){
            Element element = new Element(factory.getId());
            element.setWeight(factory.getWeight());
            element.setCoorinatesByLatLon(factory.getLatitude(), factory.getLongitude());
            elements.add(element);
        }
        clustering.setElements(elements);
        firstLayerClusters = (List<ProjectedCluster>)clustering.clustering();
        SceneManager.installMapSceneForSolutionDialog();
    }

}
