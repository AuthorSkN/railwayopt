package com.railwayopt.mapview;

import com.railwayopt.mapview.googlemap.GoogleMapAPI;
import com.railwayopt.mapview.graphic.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.*;

public class TestController {

    @FXML
    private MapView mapView;
    @FXML
    private Button buttonShowPoints;
    @FXML
    private Button buttonShowLines;
    @FXML
    private Button buttonChangePoints;
    @FXML
    private Button buttonGroupingPointAndLines;
    @FXML
    private Button buttonHideGroups;

    public void loadMap(){
        mapView.setMapAPI(new GoogleMapAPI());
        mapView.reloadMap();
        mapView.onLoadedMap(()->mapView.createMapPoint(0, new GeoPoint(60, 37), 4));
    }

    public void showPoints(){
        double longitude = 37;
        int n = 10;
        String[] colors = MapPointStyle.generateDifferentColors(n);
        for(int id = 0; id < n; id++){
            longitude += 2;
            MapPoint point = mapView.createMapPoint(id, new GeoPoint(60, longitude), id/2+1);
            point.setStyle(new MapPointStyle(2, colors[id], "#fff"));
            point.updateOnMap();
        }
    }

    public void showLines() {
        double latitude = 60;
        int n = 10;
        String[] colors = MapPointStyle.generateDifferentColors(n);
        for (int id = 0; id < n; id++) {
            List<GeoPoint> points = new ArrayList<>();
            points.add( new GeoPoint(latitude, 60));
            points.add(new GeoPoint(latitude, 65));
            MapPolyline line = mapView.createMapPolyline(id, id / 2 + 1, colors[id], points);
            line.updateOnMap();
            latitude--;
        }
    }

    public void changePoints(){
        int n = 10;
        String[] colors = MapPointStyle.generateDifferentColors(n);
        for(int id = 0; id < n; id++) {
            MapPoint point = mapView.getMapPoint(id);
            if(id % 2 == 0){
                point.setStyle(new MapPointStyle(0, "#0f0", colors[id]));
                GeoPoint oldCoord = point.getCoordinates();
                point.setCoordinates(new GeoPoint(60, oldCoord.getLongitude()));
            }else{
                point.setStyle(new MapPointStyle(1, "#00f", colors[id]));
                GeoPoint oldCoord = point.getCoordinates();
                point.setCoordinates(new GeoPoint(55, oldCoord.getLongitude()));
            }
            point.setWeight(id+1);
            point.updateOnMap();
        }
    }

    public void changeLines(){
        int n = 10;
        String[] colors = MapPointStyle.generateHues("#0000FF", n);
        for(int id = 0; id < n; id++) {
            MapPolyline line = mapView.getMapPolyline(id);
            List<GeoPoint> points = line.getPoints();
            points.add(new GeoPoint(55, 70));
            line.setColor(colors[id]);
            line.updateOnMap();
        }
    }

    public void grouping(){
        List<Integer> ids = Arrays.asList(1,2,3,4);
        MapPointGroup group = mapView.createMapPointGroup(0, ids);
        group.setStyle(new MapPointStyle(MapPointStyle.TRIANGLE, "#ff0000", "#fff"));
        group.updateOnMap();

        MapLineGroup group2 = mapView.createMapLineGroup(0, ids);
        group2.setColor("#f00");
        group2.updateOnMap();
    }

    public void hideGroups(){
        MapPointGroup pointGroup = mapView.getMapPointGroup(0);
        MapLineGroup lineGroup = mapView.getMapLineGroup(0);
        pointGroup.setVisible(false);
        lineGroup.setVisible(false);
    }

    @FXML
    public void deleteAll(){
        mapView.deleteAllPoint();
        mapView.deleteAllLines();
    }

}
