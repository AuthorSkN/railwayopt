package com.railwayopt.gui;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;
import com.railwayopt.mapview.GeoPoint;
import com.railwayopt.mapview.MapView;
import com.railwayopt.mapview.googlemap.GoogleMapAPI;
import com.railwayopt.mapview.graphic.MapPoint;
import com.railwayopt.mapview.graphic.MapPointStyle;
import com.railwayopt.mapview.graphic.MapPolyline;
import com.railwayopt.model.Solution;
import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.clustering.ClusteringAnalizer;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.KMeansProClustering;
import com.railwayopt.model.clustering.kmeanspro.KProInitializer;
import com.railwayopt.model.clustering.kmeanspro.ProjectedCluster;
import com.railwayopt.model.clustering.kmeanspro.ProjectionPoint;
import com.railwayopt.model.location.Point;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.*;

public class CreateSolutionDialogController implements Controller {

    @FXML
    private TextArea textDesc;
    @FXML
    private TextField textCountKP;
    @FXML
    private  TextField textCostKP;
    @FXML
    private TextField textCostKNRC;
    @FXML
    private TextField textCountKNRC;
    @FXML
    private TextField textTariff;
    @FXML
    private MapView mapView;
    @FXML
    private ListView<String> listViewSelectingKNRC;
    @FXML
    private VBox vboxMCO;
    @FXML
    private CheckBox checkBoxoptStructure;
    @FXML
    private Button buttonChangeKNRC;
    @FXML
    private Button buttonScalarization;
    @FXML
    private Button buttonRecalculateClustering;
    @FXML
    private Label textEconomCommonCriterion;

    private static Map<Integer, Factory> projectFactories = new HashMap<>();
    private static Map<Integer, Station> projectStations = new HashMap<>();
    private static String desc;
    private static int countKP;
    private static int countKNRC;
    private static double tariff;
    private static double costBuildKP;
    private static double costBuildKNRC;
    private static List<ProjectedCluster> firstLayerClusters;
    private static List<ProjectedCluster> secondLayerClusters;
    private static HashMap<Integer, List<Integer>> mapKNRCToPareto = new HashMap<>();
    private int knrcIdx = 0;
    private ClusteringAnalizer analizer = new ClusteringAnalizer();

    private Integer scalarParetoIdx;


    private String econommicFormat(double criterion){
        return String.format(Locale.FRANCE, "%013f", criterion);
    }

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

        double commonEconomCriterion = analizer.getEconomCriterionByParameters(firstLayerClusters, tariff, costBuildKP)
                                + analizer.getEconomCriterionByParameters(secondLayerClusters, tariff, costBuildKNRC);
        textEconomCommonCriterion.setText(econommicFormat(commonEconomCriterion));
        listViewSelectingKNRC.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectNewKNRC(observable, newValue);
                });
    }

    @FXML
    public void recalculateClustering(){
        mapView.deleteAllPoint();
        mapView.deleteAllLines();
        startClustering();
        double commonEconomCriterion = analizer.getEconomCriterionByParameters(firstLayerClusters, tariff, costBuildKP)
                + analizer.getEconomCriterionByParameters(secondLayerClusters, tariff, costBuildKNRC);
        textEconomCommonCriterion.setText(Double.toString(commonEconomCriterion));
        if (countKNRC == 0) {
            showOneLayer();
        } else {
            showBothLayer();
        }
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
            pointKP.setStyle(new MapPointStyle(MapPointStyle.SQUARE, "#000", color));
            pointKP.setTitle(pointKP.getTitle() + " (" + cluster.getRealCentre().getX() + " " + cluster.getRealCentre().getY() + ")");
            pointKP.updateOnMap();
        }
        colorsIdx = 0;
        for (ProjectedCluster cluster : secondLayerClusters) {
            MapPoint pointKNRC = mapView.getMapPoint(cluster.getCentre().getId());
            pointKNRC.setWeight(6);
            pointKNRC.setStyle(new MapPointStyle(MapPointStyle.TRIANGLE, "#000", colors[colorsIdx]));
            pointKNRC.updateOnMap();
            colorsIdx++;
        }
    }

    @FXML
    public void nextKNRC(){
        listViewSelectingKNRC.getSelectionModel().select(-1);
        int knrcId = secondLayerClusters.get(knrcIdx).getCentre().getId();
        showNextKNRC(knrcId, "#000", "#000");
        knrcIdx++;
        if(knrcIdx >= secondLayerClusters.size()) {
            knrcIdx = 0;
        }

        if (mapKNRCToPareto.get(knrcId).size() == 2){
            Random random = new Random();
            int percent = random.nextInt(100);
            if (percent < 50){
                scalarParetoIdx = 0;
            }else{
                scalarParetoIdx = 1;
            }
        }else{
            scalarParetoIdx = 0;
        }

        knrcId = secondLayerClusters.get(knrcIdx).getCentre().getId();
        mapView.setCentreAndZoomForMap(knrcId, 8);
        showNextKNRC(knrcId, "#1ca027", "#55b2e8");
    }

    @FXML
    public void scalarization(){
        listViewSelectingKNRC.getSelectionModel().select(scalarParetoIdx+1);
       /* String name = listViewSelectingKNRC.getItems().get(scalarParetoIdx + 1);
        selectNewKNRC(null, name);*/
    }

    private void selectNewKNRC(ObservableValue<? extends String> observable, String knrcName){
        if (knrcName == null)
            return;
        Map<Integer, ProjectedCluster> mapSecondLayer = new HashMap<>();
        for(ProjectedCluster cluster: secondLayerClusters){
            mapSecondLayer.put(cluster.getCentre().getId(), cluster);
        }
        Map<Integer, ProjectedCluster> mapFirstLayer = new HashMap<>();
        for(ProjectedCluster cluster: firstLayerClusters){
            mapFirstLayer.put(cluster.getCentre().getId(), cluster);
        }
        Station selectStation = null;
        for(Station station: projectStations.values()){
            if (knrcName.equals(station.getName())) {
                selectStation = station;
                break;
            }
        }
        //Определение элементов, которые нужно перераспределить
        ProjectedCluster thisCluster = secondLayerClusters.get(knrcIdx);
        List<Element> recalcElements = new ArrayList<>();
        List<ProjectedCluster> recalcClusters = new ArrayList<>();
        for(Element element:thisCluster){
            ProjectedCluster recalcCluster = mapFirstLayer.get(element.getId());
            recalcElements.addAll(recalcCluster.getElements());
        }
        //Перераспределение центров на втором уровне
        for(Element element: thisCluster){
            mapView.deleteMapPolyline(element.getId());
        }
        Integer oldKNRCId = thisCluster.getCentre().getId();
        ProjectedCluster newCentreCluster = mapFirstLayer.get(selectStation.getId());
        firstLayerClusters.remove(newCentreCluster);
        List<Integer> thisParetoIds = mapKNRCToPareto.remove(thisCluster.getCentre().getId());
        Element newCentre = thisCluster.removeElementById(selectStation.getId());
        mapKNRCToPareto.put(newCentre.getId(), thisParetoIds);
        thisCluster.addElement(thisCluster.getCentre());
        firstLayerClusters.add(new ProjectedCluster(new ProjectionPoint(
                                                        thisCluster.getCentre().getId(),
                                                        thisCluster.getCentre().getX(),
                                                        thisCluster.getCentre().getY()
        )));
        thisCluster.setCentre(mapFirstLayer.get(newCentre.getId()).getCentre());

        for(ProjectedCluster cluster: secondLayerClusters){
            mapSecondLayer.put(cluster.getCentre().getId(), cluster);
        }
        for(ProjectedCluster cluster: firstLayerClusters){
            mapFirstLayer.put(cluster.getCentre().getId(), cluster);
        }
        //Перераспределение елементов на первом уровне
        List<Integer> kpIds = new ArrayList<>();
        thisCluster.getElements().forEach(element -> kpIds.add(element.getId()));
        for(Element element : thisCluster){
            ProjectedCluster recalcCluster = mapFirstLayer.get(element.getId());
            recalcClusters.add(recalcCluster);
        }
        for(Element element: recalcElements){
            Cluster searchCluster = KMeansProClustering.searchNearestCluster(element, recalcClusters);
            searchCluster.addElement(element);
        }
        //Преобразвание графической части
        for(ProjectedCluster newCluster: recalcClusters){
            ProjectionPoint centre = newCluster.getCentre();
            for(Element element: newCluster){
                MapPolyline line = mapView.getMapPolyline(element.getId());
                List<GeoPoint> points = new ArrayList<>();
                points.add(new GeoPoint(projectFactories.get(element.getId()).getLatitude(), projectFactories.get(element.getId()).getLongitude()));
                points.add(new GeoPoint(projectStations.get(centre.getId()).getLatitude(), projectStations.get(centre.getId()).getLongitude()));

                line.setPoints(points);
                line.updateOnMap();
            }
        }
        MapPoint newKNRCMapPoint = mapView.getMapPoint(newCentre.getId());
        newKNRCMapPoint.getStyle().setColorContour("#1ca027");
        newKNRCMapPoint.getStyle().setShape(MapPointStyle.TRIANGLE);
        newKNRCMapPoint.updateOnMap();
        for(Element element: thisCluster){
            ProjectionPoint centre = thisCluster.getCentre();
            List<GeoPoint> points = new ArrayList<>();
            points.add(new GeoPoint(projectStations.get(element.getId()).getLatitude(), projectStations.get(element.getId()).getLongitude()));
            points.add(new GeoPoint(projectStations.get(centre.getId()).getLatitude(), projectStations.get(centre.getId()).getLongitude()));
            String color = mapView.getMapPoint(centre.getId()).getStyle().getColorFill();
            mapView.createMapPolyline(element.getId(), 2, color, points);
            MapPoint kpMapPoint = mapView.getMapPoint(element.getId());
            kpMapPoint.getStyle().setShape(MapPointStyle.SQUARE);
            if (mapKNRCToPareto.get(newCentre.getId()).contains(kpMapPoint.getId())||
                    (kpMapPoint.getId() == oldKNRCId))
            {
                newKNRCMapPoint.getStyle().setColorContour("#55b2e8");
            }
            kpMapPoint.updateOnMap();
        }
        //Перерасчет общих затрат
        double commonEconomCriterion = analizer.getEconomCriterionByParameters(firstLayerClusters, tariff, costBuildKP)
                + analizer.getEconomCriterionByParameters(secondLayerClusters, tariff, costBuildKNRC);
        textEconomCommonCriterion.setText(econommicFormat(commonEconomCriterion));
    }

    private void showNextKNRC(int knrcId, String colorMain, String colorPareto){
        List<String> variableKNRCNameList = new ArrayList<>();
        MapPoint pointKNRC = mapView.getMapPoint(knrcId);
        pointKNRC.getStyle().setColorContour(colorMain);
        pointKNRC.updateOnMap();
        variableKNRCNameList.add(projectStations.get(pointKNRC.getId()).getName());
        List<Integer> paretoSet = mapKNRCToPareto.get(knrcId);
        if(paretoSet != null) {
            for (Integer kpId : paretoSet) {
                MapPoint pointParetoKP = mapView.getMapPoint(kpId);
                pointParetoKP.getStyle().setColorContour(colorPareto);
                pointParetoKP.updateOnMap();
                variableKNRCNameList.add(projectStations.get(pointParetoKP.getId()).getName());
            }
        }
        listViewSelectingKNRC.setItems(FXCollections.observableArrayList(variableKNRCNameList));
    }


    @FXML
    public void setMCOParameters() {
        desc = textDesc.getText();
        costBuildKP = Double.parseDouble(textCostKP.getText());
        costBuildKNRC = Double.parseDouble(textCostKNRC.getText());
        tariff = Double.parseDouble(textTariff.getText());
        if (checkBoxoptStructure.isSelected()) {
            countKP = 43;/*Integer.parseInt(textCountKP.getText());*/
            countKNRC = 8;/*Integer.parseInt(textCountKNRC.getText());*/
        }else{
            countKP = Integer.parseInt(textCountKP.getText());
            countKNRC = Integer.parseInt(textCountKNRC.getText());
        }
        startClustering();
    }

    @FXML
    public void changeKNRC(){
        vboxMCO.setVisible(true);
        buttonChangeKNRC.setVisible(false);
        buttonRecalculateClustering.setVisible(false);
        nextKNRC();
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
            if (elements.size() > 1) {
                mapKNRCToPareto.put(cluster.getCentre().getId(), list.subList(0, 2));
            }
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
            point.setX(station.getX());
            point.setY(station.getY());
            projectionPoints.add(point);
        }
        clustering.setProjectionPoints(projectionPoints);
        List<Element> elements = new ArrayList<>();
        for (Factory factory : projectFactories.values()) {
            Element element = new Element(factory.getId());
            element.setWeight(factory.getWeight());
            element.setX(factory.getX());
            element.setY(factory.getY());
            elements.add(element);
        }
        clustering.setElements(elements);
        double criterion = Double.MAX_VALUE;
        List<ProjectedCluster> clusters = null;
        for(int i = 0; i < 20; i++) {
           List<ProjectedCluster> atemptClusters = (List<ProjectedCluster>) clustering.clustering();
           double atemptCriterion = analizer.getSumWeightDistanceFotClustering(atemptClusters);
           if (atemptCriterion < criterion){
               clusters = atemptClusters;
               criterion = atemptCriterion;
           }
        }
        return clusters;
    }

    public List<ProjectedCluster> clusteringSecondLayer() {
        KProInitializer initializer = new KProInitializer();
        initializer.setK(countKNRC);
        KMeansProClustering clustering = new KMeansProClustering(countKNRC);
        clustering.setClusterInitializer(initializer);
        List<ProjectionPoint> projectionPoints = new ArrayList<>(projectStations.size());
        for (Station station : projectStations.values()) {
            ProjectionPoint point = new ProjectionPoint(station.getId(), false);
            point.setX(station.getX());
            point.setY(station.getY());
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

    public static Solution getSolution(){
        Solution solution = new Solution(firstLayerClusters, secondLayerClusters);
        solution.setCostBuildKP(costBuildKP);
        solution.setCostBuildKNRC(costBuildKNRC);
        solution.setTariff(tariff);
        return solution;
    }

    @FXML
    public void okClustering(){
        SceneManager.solutionDialogClose();
    }

}
