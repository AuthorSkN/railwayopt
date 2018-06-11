package com.railwayopt.gui;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.railwayopt.gui.custom.selectdata.RegionGroupForSelectStation;
import com.railwayopt.mapview.GeoPoint;
import com.railwayopt.mapview.MapView;
import com.railwayopt.mapview.googlemap.GoogleMapAPI;
import com.railwayopt.mapview.graphic.MapPoint;
import com.railwayopt.mapview.graphic.MapPointStyle;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.KMeansProClustering;
import com.railwayopt.model.clustering.kmeanspro.KProInitializer;
import com.railwayopt.model.clustering.kmeanspro.ProjectedCluster;
import com.railwayopt.model.clustering.kmeanspro.ProjectionPoint;
import com.railwayopt.model.location.Point;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;

public class CreateSolutionDialogController implements Controller {

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
    @FXML
    private ListView<String> listViewSelectingKNRC;
    @FXML
    private CheckBox checkBoxoptStructure;

    private static Map<Integer, Factory> projectFactories = new HashMap<>();
    private static Map<Integer, Station> projectStations = new HashMap<>();
    private static String desc;
    private static int countKP;
    private static int countKNRC;
    private static List<ProjectedCluster> firstLayerClusters;
    private static List<ProjectedCluster> secondLayerClusters;
    private static HashMap<Integer, List<Integer>> mapKNRCToPareto = new HashMap<>();


    public void setObjects(Collection<Factory> factories, Collection<Station> stations) {
        factories.forEach(factory -> projectFactories.put(factory.getId(), factory));
        stations.forEach(station -> projectStations.put(station.getId(), station));
    }

    public void setInitialListenrsForDialog(){
        checkBoxoptStructure.selectedProperty().addListener(this::selectOptStucture);
    }


    public void selectOptStucture(ObservableValue observableValue, Boolean oldValue, Boolean newValue){
        if (newValue && !oldValue) {
            this.textCountKP.setDisable(true);
            this.textCountKNRC.setDisable(true);
        }else if (!newValue && oldValue){
            this.textCountKP.setDisable(false);
            this.textCountKNRC.setDisable(false);
        }
    }

    @Override
    public void initializeScene() {
        mapView.setMapAPI(new GoogleMapAPI());
        mapView.reloadMap();
        mapView.onLoadedMap(() -> {
            if (countKNRC == 0) {
                showOneLayer();
            } else {
                showBothLayer();
            }
        });
        listViewSelectingKNRC.setItems(FXCollections.observableArrayList("Востание", "Круторожино", "Зелецино"));
    }

    public void showOneLayer() {
        String[] colors = MapPointStyle.generateDifferentColors(countKNRC);
        int colorsIdx = 0;
        for (ProjectedCluster cluster : firstLayerClusters) {
            Station station = projectStations.get(cluster.getCentre().getId());
            for (Element element : cluster) {
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
    }

    public void showBothLayer() {
        for (Station station : projectStations.values()) {
            String title = station.getName() + " " + station.getLatitude() + " " + station.getLongitude();
            mapView.createMapPoint(station.getId(), title,
                    new GeoPoint(station.getLatitude(), station.getLongitude()), 3,
                    new MapPointStyle(MapPointStyle.SQUARE, "#000", "fff"));
        }
        String[] colors = MapPointStyle.generateDifferentColors(countKNRC);
        int colorsIdx = 0;
        Map<Integer, String> baseColorMap = new HashMap<>();
        for (ProjectedCluster cluster : secondLayerClusters) {
            Station knrc = projectStations.get(cluster.getCentre().getId());
            String[] hues = MapPointStyle.generateHues(colors[colorsIdx], cluster.getSize());
            int huesIdx = 0;
            for (Element elementKP : cluster) {
                Station kp = projectStations.get(elementKP.getId());
                List<GeoPoint> points = new ArrayList<>();
                points.add(new GeoPoint(kp.getLatitude(), kp.getLongitude()));
                points.add(new GeoPoint(knrc.getLatitude(), knrc.getLongitude()));
                mapView.createMapPolyline(kp.getId(), 2, colors[colorsIdx], points);
                baseColorMap.put(kp.getId(), hues[huesIdx++]);
            }
            colorsIdx++;
        }
        for (ProjectedCluster cluster : firstLayerClusters) {
            Station station = projectStations.get(cluster.getCentre().getId());
            String color = baseColorMap.get(cluster.getCentre().getId());
            for (Element element : cluster) {
                Factory factory = projectFactories.get(element.getId());
                List<GeoPoint> points = new ArrayList<>();
                points.add(new GeoPoint(factory.getLatitude(), factory.getLongitude()));
                points.add(new GeoPoint(station.getLatitude(), station.getLongitude()));
                mapView.createMapPolyline(factory.getId(), 2, color, points);
                mapView.createMapPoint(factory.getId(), factory.getName(), new GeoPoint(factory.getLatitude(), factory.getLongitude()),
                        3, new MapPointStyle(MapPointStyle.CIRCLE, "#000", color));
            }

            MapPoint pointKP = mapView.getMapPoint(station.getId());
            pointKP.setWeight(5);
            if (mapKNRCToPareto.containsValue(pointKP.getId()) ){
                pointKP.setStyle(new MapPointStyle(MapPointStyle.SQUARE, "#000", color));
            }else{
                pointKP.setStyle(new MapPointStyle(MapPointStyle.SQUARE, "#3399ff", color));
            }
            pointKP.setTitle(pointKP.getTitle() + " (" + cluster.getRealCentre().getX() + " " + cluster.getRealCentre().getY() + ")");
            pointKP.updateOnMap();
        }
        colorsIdx = 0;
        for (ProjectedCluster cluster : secondLayerClusters) {
            MapPoint pointKNRC = mapView.getMapPoint(cluster.getCentre().getId());
            pointKNRC.setWeight(6);
            pointKNRC.setStyle(new MapPointStyle(MapPointStyle.TRIANGLE, "#000", colors[colorsIdx]));
            pointKNRC.updateOnMap();
            /*if(mapKNRCToPareto.containsKey(pointKNRC.getId())){
                List<Integer> paretoIds = mapKNRCToPareto.get(pointKNRC.getId());
                for(Integer id:paretoIds){
                    MapPoint point = mapView.getMapPoint(id);
                }
            }*/
            colorsIdx++;
        }
    }


    @FXML
    public void setMCOParameters() {
        desc = textDesc.getText();
        if (checkBoxoptStructure.isSelected()) {
            countKP = 43;/*Integer.parseInt(textCountKP.getText());*/
            countKNRC = 8;/*Integer.parseInt(textCountKNRC.getText());*/
        }else{
            countKP = Integer.parseInt(textCountKP.getText());
            countKNRC = Integer.parseInt(textCountKNRC.getText());
        }
        startClustering();
    }

    public void getParetoForNKRC(){
        for(ProjectedCluster cluster:secondLayerClusters){
            List<Element> elements = cluster.getElements();
            Collections.sort(elements, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    Element knrc = cluster.getCentre();
                    if(knrc.distanceTo((Point) o1) > knrc.distanceTo((Point)o2)) {
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });
            List list = new ArrayList();
            elements.forEach((x) -> list.add(x.getId()));
            mapKNRCToPareto.put(cluster.getCentre().getId(),  list.subList(0, 2));
        }
    }

    public void startClustering() {
        firstLayerClusters = clusteringFirstLayer();
        if (countKNRC > 0) {
            secondLayerClusters = clusteringSecondLayer();
            getParetoForNKRC();
        }
        SceneManager.installMapSceneForSolutionDialog();
    }

    public List<ProjectedCluster> clusteringFirstLayer() {
        KProInitializer initializer = new KProInitializer();
        initializer.setK(countKP);
        KMeansProClustering clustering = new KMeansProClustering(countKP);
        clustering.setClusterInitializer(initializer);
        List<ProjectionPoint> projectionPoints = new ArrayList<>(projectStations.size());
        for (Station station : projectStations.values()) {
            ProjectionPoint point = new ProjectionPoint(station.getId(), false);
            point.setCoorinatesByLatLon(station.getLatitude(), station.getLongitude());
            projectionPoints.add(point);
        }
        clustering.setProjectionPoints(projectionPoints);
        List<Element> elements = new ArrayList<>();
        for (Factory factory : projectFactories.values()) {
            Element element = new Element(factory.getId());
            element.setWeight(factory.getWeight());
            element.setCoorinatesByLatLon(factory.getLatitude(), factory.getLongitude());
            elements.add(element);
        }
        clustering.setElements(elements);
        return (List<ProjectedCluster>) clustering.clustering();
    }

    public List<ProjectedCluster> clusteringSecondLayer() {
        KProInitializer initializer = new KProInitializer();
        initializer.setK(countKNRC);
        KMeansProClustering clustering = new KMeansProClustering(countKNRC);
        clustering.setClusterInitializer(initializer);
        List<ProjectionPoint> projectionPoints = new ArrayList<>(projectStations.size());
        for (Station station : projectStations.values()) {
            ProjectionPoint point = new ProjectionPoint(station.getId(), false);
            point.setCoorinatesByLatLon(station.getLatitude(), station.getLongitude());
            projectionPoints.add(point);
        }
        clustering.setProjectionPoints(projectionPoints);
        List<Element> elements = new ArrayList<>();
        for (ProjectedCluster cluster : firstLayerClusters) {
            Element element = new Element(cluster.getCentre().getId(), cluster.getCentre().getX(), cluster.getCentre().getY());
            element.setWeight(cluster.getClusterWeight());
            elements.add(element);
        }
        clustering.setElements(elements);
        return (List<ProjectedCluster>) clustering.clustering();
    }

}
